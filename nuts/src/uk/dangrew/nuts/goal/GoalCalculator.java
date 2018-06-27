package uk.dangrew.nuts.goal;

import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.nutrients.Nutrition;

public interface GoalCalculator {

   public void calculate(
            Nutrition nutrition,
            GoalAnalytics analytics,
            Goal goal
   );
   
}//End Interface
