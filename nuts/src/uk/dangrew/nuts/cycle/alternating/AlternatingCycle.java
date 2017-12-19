package uk.dangrew.nuts.cycle.alternating;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.cycle.AbstractCycle;
import uk.dangrew.nuts.cycle.Cycle;
import uk.dangrew.nuts.cycle.CycleType;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.system.Properties;

public class AlternatingCycle extends AbstractCycle implements Cycle {

   static final int DEFAULT_NUMBER_OF_DEFICITS = 1;
   
   private final AlternatingCycleGoalCalculator calculator;
   
   private final ObjectProperty< Integer > numberOfDeficits;

   public AlternatingCycle( String name ) {
      this( new Properties( name ), new AlternatingCycleGoalCalculator() );
   }//End Constructor
   
   public AlternatingCycle( String id, String name ) {
      this( new Properties( id, name ), new AlternatingCycleGoalCalculator() );
   }//End Constructor
   
   AlternatingCycle( Properties properties, AlternatingCycleGoalCalculator calculator ) {
      super( properties );
      this.numberOfDeficits = new SimpleObjectProperty<>( DEFAULT_NUMBER_OF_DEFICITS );
      this.calculator = calculator;
   }//End Constructor
   
   @Override public CycleType type() {
      return CycleType.Alternating;
   }//End Method
   
   @Override public void setBaseGoal( Goal baseGoal ) {
      super.setBaseGoal( baseGoal );
      if ( baseGoal() != null ) {
         this.calculator.initialiseGoals( this );
      }
   }//End Method
   
   public ObjectProperty< Integer > numberOfDeficits() {
      return numberOfDeficits;
   }//End Method
   
   @Override public AlternatingCycle duplicate( String referenceId ) {
      AlternatingCycle duplicate = new AlternatingCycle( properties().nameProperty().get() + referenceId );
      super.duplicate( duplicate );
      duplicate.numberOfDeficits.set( numberOfDeficits.get() );
      return duplicate;
   }//End Method

}//End Class
