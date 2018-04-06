package uk.dangrew.nuts.graphics.cycle;

import uk.dangrew.nuts.goal.calorie.CalorieGoal;

public class CycleCreationResult {
   
   private final CalorieGoal baseGoal;
   
   public CycleCreationResult( CalorieGoal baseGoal ) {
      this.baseGoal = baseGoal;
   }//End Constructor
   
   public CalorieGoal baseGoal(){
      return baseGoal;
   }//End Method

}//End Class
