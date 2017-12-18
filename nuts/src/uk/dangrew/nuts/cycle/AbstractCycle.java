package uk.dangrew.nuts.cycle;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.Properties;

public abstract class AbstractCycle implements Concept {

   private final Properties properties;
   private final List< Goal > goals;
   
   private Goal baseGoal;
   
   protected AbstractCycle( Properties properties ) {
      this.properties = properties;
      this.goals = new ArrayList<>();
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

}//End Class
