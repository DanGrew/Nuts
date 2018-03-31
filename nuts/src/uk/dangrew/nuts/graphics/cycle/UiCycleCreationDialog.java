package uk.dangrew.nuts.graphics.cycle;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.TextFlowBuilder;
import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.goal.GoalStore;

public class UiCycleCreationDialog extends Dialog< CycleCreationResult > {

   static final String CREATE = "Create";
   static final TextFlow BASE_GOAL_DESCRIPTION = new TextFlowBuilder()
            .normal( "Select the base goal to cycle for. This goal is used to balance the calories consumed over the cycle "
            + "period so that the overall calories consumed results in the same as if the goal were followed "
            + "each day of the cycle." )
            .build();
   
   private final ButtonType createButtonType;
   
   public UiCycleCreationDialog( GoalStore goalStore ) {
      setTitle( "Cycle Creation" );
      setHeaderText( "Please choose the type of cycle and the base goal." );
      setResizable( true );

      createButtonType = new ButtonType( CREATE, ButtonData.OK_DONE );
      getDialogPane().getButtonTypes().addAll( createButtonType, ButtonType.CANCEL );

      GridPane content = new GridPane();
      content.setPrefWidth( 400 );
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForRowPercentages( content, 30, 10, 10 );
      styling.configureConstraintsForEvenColumns( content, 1 );
      content.setPadding( new Insets( 10 ) );
      
      content.add( BASE_GOAL_DESCRIPTION, 0, 1 );
      
      ComboBox< CalorieGoal > calorieGoals = new ComboBox<>( goalStore.objectList() );
      calorieGoals.setMaxWidth( Double.MAX_VALUE );
      content.add( calorieGoals, 0, 2 );
      
      getDialogPane().setContent( content );
      
      setResultConverter( new UiCycleCreationDialogResultConverter( createButtonType, calorieGoals ) );
   }// End Constructor
   
   public Optional< CycleCreationResult > friendly_showAndWait(){
      //not tested - friendly to workaround final - do not change
      return showAndWait();
   }//End Method
   
}//End Class
