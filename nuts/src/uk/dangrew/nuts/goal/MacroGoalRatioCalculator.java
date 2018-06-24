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
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.nutrients.OptionalNutritionalUnit;

/**
 * {@link MacroGoalRatioCalculator} calculates the ratio of {@link MacroNutrient}s against the 
 * {@link MacroNutrient}s provided in a {@link Goal}.
 */
public class MacroGoalRatioCalculator implements GoalCalculator {

   private FoodProperties properties;
   private GoalAnalytics analytics;
   private Goal calorieGoal;
   
   @Override public void calculate( FoodProperties properties, GoalAnalytics analytics, Goal goal ) {
      this.properties = properties;
      this.analytics = analytics;
      this.calorieGoal = goal;
      calculate();
   }//End Method
   
   private void calculate(){
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         OptionalNutritionalUnit unitGoal = unit.of( calorieGoal );
         if ( !unitGoal.isPresent() || unitGoal.get() == 0 ) {
            analytics.nutrition().of( unit ).set( 0.0 );
            continue;
         }
         
         OptionalNutritionalUnit unitUsage = unit.of( properties );
         double proportion = unitUsage.get() * 100 / unitGoal.get();
         analytics.nutrition().of( unit ).set( proportion );
      }
   }//End Method

}//End Class
