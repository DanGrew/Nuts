package uk.dangrew.nuts.graphics.cycle;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.util.Callback;
import uk.dangrew.nuts.goal.GoalImpl;

public class UiCycleCreationDialogResultConverter implements Callback< ButtonType, CycleCreationResult >{

   private final ButtonType acceptingButtonType;
//   private final ComboBox< CycleType > types; 
   private final ComboBox< GoalImpl > goalImpls; 
   
   public UiCycleCreationDialogResultConverter(
            ButtonType acceptingButtonType,
//            ComboBox< CycleType > types, 
            ComboBox< GoalImpl > goalImpls 
   ) {
      this.acceptingButtonType = acceptingButtonType;
//      this.types = types;
      this.goalImpls = goalImpls;
   }//End Constructor
   
   @Override public CycleCreationResult call( ButtonType buttonType ) {
      if ( buttonType != acceptingButtonType ) {
         return null;
      }
      
//      CycleType type = types.getSelectionModel().getSelectedItem();
//      if ( type == null ) {
//         return null;
//      }
      
      GoalImpl baseGoal = goalImpls.getSelectionModel().getSelectedItem();
      if ( baseGoal == null ) {
         return null;
      }
      
//      return new CycleCreationResult( type, baseGoal );
      return null;
   }//End Method

}//End Class
