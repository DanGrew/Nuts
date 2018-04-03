package uk.dangrew.nuts.goal;

import uk.dangrew.nuts.goal.proportion.ProportionGoalCalculator;

public enum GoalTypes {

   Calorie( new MacroGoalRatioCalculator() ),
   Proportion( new ProportionGoalCalculator() );
   
   private final GoalCalculator calculator;
   
   private GoalTypes( GoalCalculator calculator ) {
      this.calculator = calculator;
   }//End Constructor
   
   public GoalCalculator calculator(){
      return calculator;
   }//End Method
   
}//End Enum
