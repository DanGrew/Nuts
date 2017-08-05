/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import uk.dangrew.kode.javafx.registrations.RegistrationManager;
import uk.dangrew.nuts.goal.MacroGoalRatioCalculator;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * The {@link FoodPortion} wraps a {@link Food} item to portion it linearly, as a percentage.
 */
public class FoodPortion implements Food {
   
   private final ChangeListener< Object > macroUpdater;
   private final RegistrationManager registrations;
   
   private final FoodProperties properties;
   private final FoodAnalytics analytics;
   private final GoalAnalytics goalAnalytics;
   
   private final ObjectProperty< Food > food;
   private final ObjectProperty< Double > portion;
   
   /**
    * Constructs a new {@link FoodPortion}.
    * @param food the {@link Food} immediately applied.
    * @param portion the portion immediately applied.
    */
   public FoodPortion( Food food, double portion ) {
      this();
      setFood( food );
      setPortion( portion );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodPortion}.
    */
   public FoodPortion() {
      this( 
               new FoodProperties( "Portion" ), 
               new FoodAnalytics(),
               new GoalAnalytics(),
               new MacroRatioCalculator(),
               new MacroGoalRatioCalculator()
      );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodPortion}.
    * @param properties the {@link FoodProperties}.
    * @param foodAnalytics the {@link FoodAnalytics}.
    * @param goalAnalytics the {@link GoalAnalytics}.
    * @param ratioCalculator the {@link MacroRatioCalculator}.
    * @param goalRatioCalculator the {@link MacroGoalRatioCalculator}.
    */
   FoodPortion( 
            FoodProperties properties, 
            FoodAnalytics foodAnalytics, 
            GoalAnalytics goalAnalytics,
            MacroRatioCalculator ratioCalculator,
            MacroGoalRatioCalculator goalRatioCalculator
   ) {
      this.food = new SimpleObjectProperty<>();
      this.portion = new SimpleObjectProperty<>( 100.0 );
      this.properties = properties;
      this.analytics = foodAnalytics;
      this.goalAnalytics = goalAnalytics;
      
      //TODO think these two calculators should be removed and the logic in this class relocated
      ratioCalculator.associate( properties, foodAnalytics );
      goalRatioCalculator.associate( properties, goalAnalytics );
      
      //need to bind macros
      this.macroUpdater = ( s, o, n ) -> updateMacros();
      this.portion.addListener( macroUpdater );
      this.registrations = new RegistrationManager();
   }//End Constructor
   
   /**
    * Method to update the {@link MacroNutrient} values according to the portion.
    */
   private void updateMacros(){
      if ( food.get() == null ) {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            properties.nutritionFor( macro ).set( 0.0 );
         }
         return;
      }
      
      goalAnalytics.goal().set( food.get().goalAnalytics().goal().get() );
      double proportion = portion.get() / 100.0;
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         properties.nutritionFor( macro ).set( food.get().properties().nutritionFor( macro ).get() * proportion );
         goalAnalytics.nutrientRatioFor( macro ).set( food.get().goalAnalytics().nutrientRatioFor( macro ).get() * proportion );
      }
      properties.calories().set( food.get().properties().calories().get() * proportion );
      goalAnalytics.caloriesRatioProperty().set( food.get().goalAnalytics().caloriesRatio() * proportion );
   }//End Method
   
   /**
    * Setter for the {@link Food} in the {@link FoodPortion}.
    * @param food the {@link Food} to portion.
    */
   public void setFood( Food food ) {
      detachFromFood();
      attachToFood( food );
      updateMacros();
   }//End Method

   /**
    * Access to the {@link Food} property
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Food > food() {
      return food;
   }//End Method

   /**
    * Access to the portion property
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Double > portion() {
      return portion;
   }//End Method

   /**
    * Setter for the portion, lower bound of 0.
    * @param portion the portion.
    */
   public void setPortion( double portion ) {
      if ( portion < 0 ) {
         portion = 0;
      }
      
      this.portion.set( portion );
   }//End Method

   /**
    * Access to the portioned {@link MacroNutrient} property.
    * @param macro the {@link MacroNutrient} in question.
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Double > nutritionFor( MacroNutrient macro ) {
      return properties.nutritionFor( macro );
   }//End Method
   
   /**
    * Access to the ratio of the given {@link MacroNutrient}.
    * @param macro the {@link MacroNutrient} in question.
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Double > nutritionRatioFor( MacroNutrient macro ) {
      return analytics.nutrientRatioFor( macro );
   }//End Method
   
   /**
    * Method to detach listeners from the current {@link Food}.
    */
   private void detachFromFood(){
      if ( food.get() == null ) {
         return;
      }
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         this.food.get().properties().nutritionFor( macro ).removeListener( macroUpdater );
         this.food.get().goalAnalytics().nutrientRatioFor( macro ).removeListener( macroUpdater );
      }
      this.food.get().properties().calories().removeListener( macroUpdater );
      this.food.get().goalAnalytics().goal().removeListener( macroUpdater );
      this.food.get().goalAnalytics().caloriesRatioProperty().removeListener( macroUpdater );
      registrations.shutdown();
   }//End Method
   
   /**
    * Method to attach listeners to the given {@link Food}.
    * @param food the {@link Food} to attach to.
    */
   private void attachToFood( Food food ){
      this.food.set( food );
      
      if ( food == null ) {
         return;
      }
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         food.properties().nutritionFor( macro ).addListener( macroUpdater );
         food.goalAnalytics().nutrientRatioFor( macro ).addListener( macroUpdater );
      }
      food.properties().calories().addListener( macroUpdater );
      food.goalAnalytics().goal().addListener( macroUpdater );
      food.goalAnalytics().caloriesRatioProperty().addListener( macroUpdater );
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public FoodProperties properties() {
      return properties;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public FoodAnalytics foodAnalytics() {
      return analytics;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public GoalAnalytics goalAnalytics() {
      return goalAnalytics;
   }//End Method

}//End Class
