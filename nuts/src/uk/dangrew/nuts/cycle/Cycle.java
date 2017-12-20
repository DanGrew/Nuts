package uk.dangrew.nuts.cycle;

import javafx.collections.ObservableList;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.system.Concept;

public interface Cycle extends Concept {

   public CycleType type();
   
   public void setBaseGoal( Goal baseGoal );
   
   public Goal baseGoal();
   
   public ObservableList< Goal > goals();
   
   @Override public Cycle duplicate( String referenceId );
   
}//End Interface

