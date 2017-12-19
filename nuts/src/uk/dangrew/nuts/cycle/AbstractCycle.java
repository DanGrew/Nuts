package uk.dangrew.nuts.cycle;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.system.Properties;

public abstract class AbstractCycle implements Cycle {

   static final CycleApproach DEFAULT_APPROACH = CycleApproach.LowThenHigh;
   
   private final Properties properties;
   private final ObjectProperty< CycleApproach > approach;
   private final List< Goal > goals;
   
   private Goal baseGoal;
   
   protected AbstractCycle( Properties properties ) {
      this.properties = properties;
      this.goals = new ArrayList<>();
      this.approach = new SimpleObjectProperty<>( DEFAULT_APPROACH );
   }//End Constructor
   
   @Override public Properties properties() {
      return properties;
   }//End Method
   
   @Override public abstract AbstractCycle duplicate( String referenceId );
   
   protected void duplicate( AbstractCycle duplicate ){
      duplicate.setBaseGoal( baseGoal );
   }//End Method
   
   public void setBaseGoal( Goal baseGoal ) {
      if ( this.baseGoal != null ) {
         throw new IllegalStateException( "Base Goal Already Set." );
      }
      this.baseGoal = baseGoal;
   }//End Method
   
   public Goal baseGoal() {
      return baseGoal;
   }//End Method
   
   public List< Goal > goals() {
      return goals;
   }//End Method

   public ObjectProperty< CycleApproach > approach() {
      return approach;
   }//End Method

}//End Class
