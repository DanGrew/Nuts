/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * {@link MacroGoalRatioCalculator} calculates the ratio of {@link MacroNutrient}s against the 
 * {@link MacroNutrient}s provided in a {@link Goal}.
 */
class MacroGoalRatioCalculator {

   private Goal goal;
   private FoodProperties properties;
   private FoodAnalytics analytics;
   
   /**
    * Associate with the given.
    * @param properties the {@link FoodProperties}.
    * @param analytics the {@link FoodAnalytics}.
    * @param goal the {@link Goal}.
    */
   void associate( FoodProperties properties, FoodAnalytics analytics, Goal goal ) {
      if ( this.properties != null || this.analytics != null || this.goal != null ) {
         throw new IllegalStateException( "Allredy associated." );
      }
      
      this.properties = properties;
      this.analytics = analytics;
      this.goal = goal;
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         properties.nutritionFor( macro ).gramsProperty().addListener( ( s, o, n ) -> updateRatios() );
         goal.properties().nutritionFor( macro ).gramsProperty().addListener( ( s, o, n ) -> updateRatios() );
      }
      updateRatios();
   }//End Method
   
   /**
    * Method to update the ratios of {@link Goal}s {@link MacroNutrient}s.
    */
   private void updateRatios(){
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         double macroGoal = goal.properties().nutritionFor( macro ).inGrams();
         if ( macroGoal == 0 ) {
            analytics.nutrientRatioFor( macro ).set( 0.0 );   
            continue;
         }
         double macroUsage = properties.nutritionFor( macro ).inGrams();
         double proportion = macroUsage * 100 / macroGoal;
         analytics.nutrientRatioFor( macro ).set( proportion );
      }
   }//End Method

}//End Class
