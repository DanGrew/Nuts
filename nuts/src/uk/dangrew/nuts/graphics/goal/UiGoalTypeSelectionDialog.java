package uk.dangrew.nuts.graphics.goal;

import uk.dangrew.nuts.goal.GoalTypes;
import uk.dangrew.nuts.graphics.common.UiEnumTypeSelectionDialog;

public class UiGoalTypeSelectionDialog extends UiEnumTypeSelectionDialog< GoalTypes > {

   public UiGoalTypeSelectionDialog() {
      super( GoalTypes.class, GoalTypes.Calorie );
      setTitle( "Goal Type Selection" );
      setContentText( "Choose your Goal Type:" );
   }//End Constructor
   
}//End Class
