package uk.dangrew.nuts.cycle;


import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.goal.Goal;

public class AlternatingCycle {

   static final int DEFAULT_NUMBER_OF_DEFICITS = 1;
   
   private final AlternatingCycleGoalCalculator calculator;
   private final Goal baseGoal;
   private final List< Goal > goals;
   
   private final ObjectProperty< Integer > numberOfDeficits;

   public AlternatingCycle( Goal baseGoal ) {
      this( new AlternatingCycleGoalCalculator(), baseGoal );
   }//End Constructor
   
   AlternatingCycle( AlternatingCycleGoalCalculator calculator, Goal baseGoal ) {
      this.goals = new ArrayList<>();
      this.baseGoal = baseGoal;
      this.numberOfDeficits = new SimpleObjectProperty<>( DEFAULT_NUMBER_OF_DEFICITS );
      this.calculator = calculator;
      this.calculator.initialiseGoals( this );
   }//End Constructor
   
   Object calculator(){
      return calculator;
   }//End Method
   
   public Goal baseGoal() {
      return baseGoal;
   }//End Method
   
   public List< Goal > goals() {
      return goals;
   }//End Method
   
   public ObjectProperty< Integer > numberOfDeficits() {
      return numberOfDeficits;
   }//End Method

}//End Class
