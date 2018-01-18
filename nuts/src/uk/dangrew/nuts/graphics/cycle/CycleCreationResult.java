package uk.dangrew.nuts.graphics.cycle;

import uk.dangrew.nuts.goal.Goal;

public class CycleCreationResult {
   
   private final Goal baseGoal;
   
   public CycleCreationResult( Goal baseGoal ) {
      this.baseGoal = baseGoal;
   }//End Constructor
   
   public Goal baseGoal(){
      return baseGoal;
   }//End Method

}//End Class
