package uk.dangrew.nuts.graphics.goal;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceDialog;
import uk.dangrew.nuts.goal.GoalTypes;

public class UiGoalTypeSelectionDialog extends ChoiceDialog< GoalTypes > {

   public UiGoalTypeSelectionDialog() {
      super( GoalTypes.Calorie, FXCollections.observableArrayList( GoalTypes.values() ) );
      setTitle( "Goal Type Selection" );
      setContentText( "Choose your Goal Type:" );
   }//End Constructor
   
   public Optional< GoalTypes > friendly_showAndWait(){
      return showAndWait();
   }//End Method
   
   public void friendly_setHeaderText( String headerText ) {
      setHeaderText( headerText );
   }//End Method
   
}//End Class
