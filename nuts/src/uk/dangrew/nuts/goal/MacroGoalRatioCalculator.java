/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal;

import javafx.beans.value.ChangeListener;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * {@link MacroGoalRatioCalculator} calculates the ratio of {@link MacroNutrient}s against the 
 * {@link MacroNutrient}s provided in a {@link Goal}.
 */
public class MacroGoalRatioCalculator {

   private final ChangeListener< Double > updater;
   
   private FoodProperties properties;
   private GoalAnalytics analytics;
   
   private CalorieGoal calorieGoal;
   
   /**
    * Constructs a new {@link MacroGoalRatioCalculator}.
    */
   public MacroGoalRatioCalculator() {
      this.updater = ( s, o, n ) -> updateRatios();
   }//End Constructor
   
   /**
    * Associate with the given.
    * @param properties the {@link FoodProperties}.
    * @param analytics the {@link FoodAnalytics}.
    * @param goal the {@link Goal}.
    */
   public void associate( FoodProperties properties, GoalAnalytics analytics ) {
      if ( this.properties != null || this.analytics != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.properties = properties;
      this.properties.carbohydrates().addListener( updater );
      this.properties.fats().addListener( updater );
      this.properties.protein().addListener( updater );
      this.properties.calories().addListener( updater );
      this.analytics = analytics;
      this.analytics.calorieGoal().addListener( ( s, o, n ) -> setGoal( n ) );
   }//End Method
   
   /**
    * Method to apply the {@link Goal} to the calculator.
    * @param goal the {@link Goal}, can be null.
    */
   private void setGoal( CalorieGoal calorieGoal ) {
      if ( this.calorieGoal != null ) {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            this.calorieGoal.properties().nutritionFor( macro ).removeListener( updater );
         }
         this.calorieGoal.properties().calories().removeListener( updater );
      }
      
      this.calorieGoal = calorieGoal;
      if ( this.calorieGoal == null ) {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            analytics.nutrientRatioFor( macro ).set( 0.0 );
         }
         return;
      } else {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            properties.nutritionFor( macro ).addListener( updater );
            this.calorieGoal.properties().nutritionFor( macro ).addListener( updater );
         }
         this.calorieGoal.properties().calories().addListener( updater );
         updateRatios();
      }
   }//End Method
   
   /**
    * Method to update the ratios of {@link Goal}s {@link MacroNutrient}s.
    */
   private void updateRatios(){
      if ( this.calorieGoal == null ) {
         return;
      }
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         double macroGoal = calorieGoal.properties().nutritionFor( macro ).get();
         if ( macroGoal == 0 ) {
            analytics.nutrientRatioFor( macro ).set( 0.0 );   
            continue;
         }
         double macroUsage = properties.nutritionFor( macro ).get();
         double proportion = macroUsage * 100 / macroGoal;
         analytics.nutrientRatioFor( macro ).set( proportion );
      }
      
      if ( calorieGoal.properties().calories().get() == 0.0 ) {
         analytics.caloriesRatioProperty().set( 0.0 );   
      } else {
         double calorieProprtion = properties.calories().get() * 100 / calorieGoal.properties().calories().get();
         analytics.caloriesRatioProperty().set( calorieProprtion );
      }
   }//End Method

}//End Class
