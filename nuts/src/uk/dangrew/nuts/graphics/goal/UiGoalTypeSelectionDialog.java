package uk.dangrew.nuts.graphics.goal;

import uk.dangrew.kode.javafx.dialog.UiEnumTypeSelectionDialog;
import uk.dangrew.nuts.goal.GoalTypes;

public class UiGoalTypeSelectionDialog extends UiEnumTypeSelectionDialog< GoalTypes > {

   public UiGoalTypeSelectionDialog() {
      super( GoalTypes.class, GoalTypes.Calorie );
      setTitle( "Goal Type Selection" );
      setContentText( "Choose your Goal Type:" );
   }//End Constructor
   
}//End Class
