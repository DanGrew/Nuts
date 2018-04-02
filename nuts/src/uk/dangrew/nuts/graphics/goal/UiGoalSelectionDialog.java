package uk.dangrew.nuts.graphics.goal;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceDialog;
import uk.dangrew.nuts.goal.GoalTypes;

public class UiGoalSelectionDialog extends ChoiceDialog< GoalTypes > {

   public UiGoalSelectionDialog() {
      super( GoalTypes.Calorie, FXCollections.observableArrayList( GoalTypes.values() ) );
      setTitle( "Template Selection" );
      setContentText( "Choose your Template:" );
   }//End Constructor
   
   public Optional< GoalTypes > friendly_showAndWait(){
      return showAndWait();
   }//End Method
   
   public void friendly_setHeaderText( String headerText ) {
      setHeaderText( headerText );
   }//End Method
   
}//End Class
