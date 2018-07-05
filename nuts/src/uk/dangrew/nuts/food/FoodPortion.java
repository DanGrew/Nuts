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
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.nutrients.OptionalNutritionalUnit;
import uk.dangrew.nuts.system.Properties;

/**
 * The {@link FoodPortion} wraps a {@link Food} item to portion it linearly, as a percentage.
 */
public class FoodPortion implements Food {
   
   private final ChangeListener< Object > macroUpdater;
   private final RegistrationManager registrations;
   
   private final Properties properties;
   private final Nutrition nutrition;
   private final FoodAnalytics analytics;
   
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
               new Properties( "Portion" ), 
               new Nutrition(),
               new FoodAnalytics(),
               new MacroRatioCalculator()
      );
   }//End Constructor
   
   FoodPortion( 
            Properties properties, 
            Nutrition nutrition,
            FoodAnalytics foodAnalytics, 
            MacroRatioCalculator ratioCalculator
   ) {
      this.food = new SimpleObjectProperty<>();
      this.portion = new SimpleObjectProperty<>( 100.0 );
      this.properties = properties;
      this.nutrition = nutrition;
      this.analytics = foodAnalytics;
      
      //TODO think these two calculators should be removed and the logic in this class relocated
      ratioCalculator.associate( nutrition, foodAnalytics );
      
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
         for ( NutritionalUnit unit : NutritionalUnit.values() ) {
            unit.of( nutrition ).set( 0.0 );
         }
         return;
      }
      
      double proportion = portion.get() / 100.0;
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         OptionalNutritionalUnit unitValue = unit.of( this.food.get() );
         unit.of( nutrition ).set( unitValue.get() * proportion );
      }
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
    * Method to detach listeners from the current {@link Food}.
    */
   private void detachFromFood(){
      if ( food.get() == null ) {
         return;
      }
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         unit.of( this.food.get() ).property().removeListener( macroUpdater );
      }
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
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         unit.of( this.food.get() ).property().addListener( macroUpdater );
      }
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public Properties properties() {
      return properties;
   }//End Method
   
   @Override public Nutrition nutrition() {
      return nutrition;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public FoodAnalytics foodAnalytics() {
      return analytics;
   }//End Method
   
   @Override public FoodPortion duplicate( String referenceId ) {
      if ( food().get() == null ) {
         return new FoodPortion( null, portion().get() );
      } else {
         return new FoodPortion( food().get(), portion().get() );
      }
   }//End Method
   
   @Override public String toString() {
      if ( food.get() == null ) {
         return "No Food";
      }
      
      return food.get().properties().nameProperty().get() + ": " + portion.get();
   }//End Method
   
}//End Class
