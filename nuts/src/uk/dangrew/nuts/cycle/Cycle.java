package uk.dangrew.nuts.cycle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.Properties;

public class Cycle implements Concept {

   private final Properties properties;
   private final ObservableList< Goal > goals;
   
   private Goal baseGoal;
   
   public Cycle( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   public Cycle( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   Cycle( Properties properties ) {
      this.properties = properties;
      this.goals = FXCollections.observableArrayList();
   }//End Constructor
   
   @Override public Properties properties() {
      return properties;
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
   
   public ObservableList< Goal > goals() {
      return goals;
   }//End Method

   @Override public Concept duplicate( String referenceId ) {
      return this;
   }//End Method

}//End Class
