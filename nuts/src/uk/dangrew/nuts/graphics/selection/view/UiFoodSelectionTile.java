package uk.dangrew.nuts.graphics.selection.view;

import javafx.scene.layout.GridPane;
import uk.dangrew.nuts.food.FoodPortion;

//awful lack of use of interfaces forces an abstract extension - thanks jfx!
public abstract class UiFoodSelectionTile extends GridPane {

   private boolean isSelected;
   
   public void setSelected( boolean selected ) {
      updateSelection( selected );
      this.isSelected = selected;
   }//End Method
   
   protected abstract void updateSelection( boolean selected );

   public abstract FoodPortion food();

   public boolean isSelected(){
      return isSelected;
   }//End Method
   
}//End Interface

