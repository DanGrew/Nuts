package uk.dangrew.nuts.graphics.cycle;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.cycle.CycleStore;
import uk.dangrew.nuts.goal.Gender;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.GoalImpl;
import uk.dangrew.nuts.goal.GoalStore;

public class UiCyclePane extends GridPane {

//   private final UiCycleTable uiCycleTable;
//   private final UiCycleConfigurationPane configurationPane; 
//   private final ReadOnlyGoalTable goalTable;
   
   public UiCyclePane() {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForColumnPercentages( this, 30, 70 );
      styling.configureConstraintsForEvenRows( this, 2 );
      
      CycleStore cycles = new CycleStore();
      Goal baseGoal = new GoalImpl( "Base" );
      baseGoal.gender().set( Gender.Male );
      baseGoal.age().set( 28.0 );
      baseGoal.weight().set( 197.0 );
      baseGoal.height().set( 1.87 );
      baseGoal.calorieDeficit().set( 300.0 );
      
      GoalStore goals = new GoalStore();
      goals.createConcept( "Goal1" );
      goals.createConcept( "Goal2" );
      goals.createConcept( "Goal3" );
      
//      cycles.alternatingCycleStore().createConcept( "First" ).setBaseGoal( baseGoal );
//      AlternatingCycle second = cycles.alternatingCycleStore().createConcept( "Second" );
//      second.setBaseGoal( baseGoal );
//      second.numberOfDeficits().set( 2.0 );
//      cycles.alternatingCycleStore().createConcept( "Third" ).setBaseGoal( baseGoal );
//      
//      add( new ConceptTableWithControls<>( "Cycles", uiCycleTable = new UiCycleTable( cycles, goals ) ), 0, 0 );
//      add( configurationPane = new UiCycleConfigurationPane(), 0, 1 );
//      add( goalTable = new ReadOnlyGoalTable(), 1, 0 );
//      GridPane.setRowSpan( goalTable, 2 );
//      
//      uiCycleTable.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> {
//         goalTable.setGoals( n.concept().goalImpls() );
//         configurationPane.setCycle( n.concept() );
//      } );
   }//End Constructor
   
}//End Class
