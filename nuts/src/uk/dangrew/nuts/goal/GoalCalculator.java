package uk.dangrew.nuts.goal;

import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;

public interface GoalCalculator {

   public void calculate(
            FoodProperties properties,
            GoalAnalytics analytics,
            Goal goal
   );
}//End Interface
