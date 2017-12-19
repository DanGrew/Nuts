package uk.dangrew.nuts.graphics.cycle;

import uk.dangrew.nuts.cycle.CycleType;
import uk.dangrew.nuts.goal.Goal;

public class CycleCreationResult {

   private final CycleType type;
   private final Goal baseGoal;
   
   public CycleCreationResult( CycleType type, Goal goal ) {
      this.type = type;
      this.baseGoal = goal;
   }//End Constructor

   public CycleType type() {
      return type;
   }//End Method

   public Goal baseGoal() {
      return baseGoal;
   }//End Method

}//End Class
