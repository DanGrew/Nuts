/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.goal;

import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.nutrients.OptionalNutritionalUnit;

/**
 * {@link MacroGoalRatioCalculator} calculates the ratio of {@link MacroNutrient}s against the 
 * {@link MacroNutrient}s provided in a {@link Goal}.
 */
public class MacroGoalRatioCalculator implements GoalCalculator {

   private Nutrition nutrition;
   private GoalAnalytics analytics;
   private Goal calorieGoal;
   
   @Override public void calculate( Nutrition nutrition, GoalAnalytics analytics, Goal goal ) {
      this.nutrition = nutrition;
      this.analytics = analytics;
      this.calorieGoal = goal;
      calculate();
   }//End Method
   
   private void calculate(){
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         OptionalNutritionalUnit unitGoal = unit.of( calorieGoal );
         if ( !unitGoal.isPresent() || unitGoal.get() == 0 ) {
            analytics.of( unit ).set( 0.0 );
            continue;
         }
         
         OptionalNutritionalUnit unitUsage = unit.of( nutrition );
         double proportion = unitUsage.get() * 100 / unitGoal.get();
         analytics.of( unit ).set( proportion );
      }
   }//End Method

}//End Class
