package uk.dangrew.nuts.graphics.cycle;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.util.Callback;
import uk.dangrew.nuts.cycle.CycleType;
import uk.dangrew.nuts.goal.Goal;

public class UiCycleCreationDialogResultConverter implements Callback< ButtonType, CycleCreationResult >{

   private final ButtonType acceptingButtonType;
   private final ComboBox< CycleType > types; 
   private final ComboBox< Goal > goals; 
   
   public UiCycleCreationDialogResultConverter(
            ButtonType acceptingButtonType,
            ComboBox< CycleType > types, 
            ComboBox< Goal > goals 
   ) {
      this.acceptingButtonType = acceptingButtonType;
      this.types = types;
      this.goals = goals;
   }//End Constructor
   
   @Override public CycleCreationResult call( ButtonType buttonType ) {
      if ( buttonType != acceptingButtonType ) {
         return null;
      }
      
      CycleType type = types.getSelectionModel().getSelectedItem();
      if ( type == null ) {
         return null;
      }
      
      Goal baseGoal = goals.getSelectionModel().getSelectedItem();
      if ( baseGoal == null ) {
         return null;
      }
      
      return new CycleCreationResult( type, baseGoal );
   }//End Method

}//End Class
