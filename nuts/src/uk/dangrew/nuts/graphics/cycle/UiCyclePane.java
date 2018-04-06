package uk.dangrew.nuts.graphics.cycle;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.cycle.CycleStore;
import uk.dangrew.nuts.goal.Gender;
import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.goal.CalorieGoalImpl;
import uk.dangrew.nuts.goal.CalorieGoalStore;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;

public class UiCyclePane extends GridPane {

   private final UiCycleTable uiCycleTable;
   private final UiCycleGoalsTable uiCycleGoalsTable;
   
   public UiCyclePane() {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForColumnPercentages( this, 30, 70 );
      styling.configureConstraintsForEvenRows( this, 1 );
      
      CycleStore cycles = new CycleStore();
      CalorieGoal baseGoal = new CalorieGoalImpl( "Base" );
      baseGoal.gender().set( Gender.Male );
      baseGoal.age().set( 28.0 );
      baseGoal.weight().set( 197.0 );
      baseGoal.height().set( 1.87 );
      baseGoal.calorieDeficit().set( 300.0 );
      
      CalorieGoalStore goals = new CalorieGoalStore();
      goals.createConcept( "Goal1" );
      goals.createConcept( "Goal2" );
      goals.createConcept( "Goal3" );
      
      add( new ConceptTableWithControls<>( "Cycles", uiCycleTable = new UiCycleTable( cycles, goals ) ), 0, 0 );
      add( new ConceptTableWithControls<>( "Goals", uiCycleGoalsTable = new UiCycleGoalsTable() ), 1, 0 );
      
      uiCycleTable.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
         uiCycleGoalsTable.controller().focusOn( n.concept() );
      } );
   }//End Constructor
   
}//End Class
