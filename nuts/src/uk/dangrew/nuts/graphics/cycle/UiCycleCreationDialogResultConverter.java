package uk.dangrew.nuts.graphics.cycle;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.util.Callback;
import uk.dangrew.nuts.goal.CalorieGoal;

public class UiCycleCreationDialogResultConverter implements Callback< ButtonType, CycleCreationResult >{

   private final ButtonType acceptingButtonType;
   private final ComboBox< CalorieGoal > calorieGoals; 
   
   public UiCycleCreationDialogResultConverter(
            ButtonType acceptingButtonType,
            ComboBox< CalorieGoal > calorieGoals 
   ) {
      this.acceptingButtonType = acceptingButtonType;
      this.calorieGoals = calorieGoals;
   }//End Constructor
   
   @Override public CycleCreationResult call( ButtonType buttonType ) {
      if ( buttonType != acceptingButtonType ) {
         return null;
      }
      
      CalorieGoal baseGoal = calorieGoals.getSelectionModel().getSelectedItem();
      if ( baseGoal == null ) {
         return null;
      }
      
      return new CycleCreationResult( baseGoal );
   }//End Method

}//End Class
