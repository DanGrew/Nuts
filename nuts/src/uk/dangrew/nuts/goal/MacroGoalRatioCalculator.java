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

/**
 * {@link MacroGoalRatioCalculator} calculates the ratio of {@link MacroNutrient}s against the 
 * {@link MacroNutrient}s provided in a {@link Goal}.
 */
public class MacroGoalRatioCalculator implements GoalCalculator {

   private SimpleGoalRatioCalculator simpleGoalCalculator;
   
   @Override public void calculate( Nutrition nutrition, GoalAnalytics analytics, Goal goal ) {
      this.simpleGoalCalculator = new SimpleGoalRatioCalculator( goal, analytics, nutrition );
      calculate();
   }//End Method
   
   private void calculate(){
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         simpleGoalCalculator.calculate( unit );
      }
   }//End Method

}//End Class
