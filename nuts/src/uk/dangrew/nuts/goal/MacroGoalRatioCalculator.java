/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal;

import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * {@link MacroGoalRatioCalculator} calculates the ratio of {@link MacroNutrient}s against the 
 * {@link MacroNutrient}s provided in a {@link Goal}.
 */
public class MacroGoalRatioCalculator implements GoalCalculator {

   private FoodProperties properties;
   private GoalAnalytics analytics;
   private CalorieGoal calorieGoal;
   
   @Override public void calculate( FoodProperties properties, GoalAnalytics analytics, Goal goal ) {
      if ( goal.type() != GoalTypes.Calorie ) {
         return;
      }
      this.properties = properties;
      this.analytics = analytics;
      this.calorieGoal = ( CalorieGoal ) goal;
      calculate();
   }//End Method
   
   private void calculate(){
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
