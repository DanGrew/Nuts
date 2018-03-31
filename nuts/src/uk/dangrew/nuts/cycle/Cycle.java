package uk.dangrew.nuts.cycle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.goal.DerivedCalorieGoal;
import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.Properties;

public class Cycle implements Concept {

   private final Properties properties;
   private final ObservableList< DerivedCalorieGoal > goals;
   
   private CalorieGoal baseGoal;
   
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
   
   public void setBaseGoal( CalorieGoal baseGoal ) {
      if ( this.baseGoal != null ) {
         throw new IllegalStateException( "Base Goal Already Set." );
      }
      this.baseGoal = baseGoal;
   }//End Method
   
   public CalorieGoal baseGoal() {
      return baseGoal;
   }//End Method
   
   public ObservableList< DerivedCalorieGoal > goals() {
      return goals;
   }//End Method

   @Override public Cycle duplicate( String referenceId ) {
      return this;
   }//End Method

}//End Class
