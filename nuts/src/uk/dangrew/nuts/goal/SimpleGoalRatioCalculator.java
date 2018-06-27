package uk.dangrew.nuts.goal;

import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.nutrients.OptionalNutritionalUnit;

public class SimpleGoalRatioCalculator {

   private final Nutrition nutrition;
   private final GoalAnalytics analytics;
   private final Goal goal;
   
   public SimpleGoalRatioCalculator(
            Goal goal, 
            GoalAnalytics analytics, 
            Nutrition nutrition 
   ) {
      this.nutrition = nutrition;
      this.analytics = analytics;
      this.goal = goal;
   }//End Constructor
   
   public  void calculate( NutritionalUnit unit ) {
      OptionalNutritionalUnit unitGoal = unit.of( goal );
      if ( !unitGoal.isPresent() || unitGoal.get() == 0 ) {
         analytics.of( unit ).set( 0.0 );
         return;
      }
      
      OptionalNutritionalUnit unitUsage = unit.of( nutrition );
      double proportion = unitUsage.get() * 100 / unitGoal.get();
      analytics.of( unit ).set( proportion );
   }//End Method
   
}//End Class
