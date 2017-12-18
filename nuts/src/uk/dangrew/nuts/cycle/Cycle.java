package uk.dangrew.nuts.cycle;

import java.util.List;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.system.Concept;

public interface Cycle extends Concept {

   public Goal baseGoal();
   
   public List< Goal > goals();
   
}//End Interface

