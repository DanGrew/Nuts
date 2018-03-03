package uk.dangrew.nuts.apis.tesco.graphics.selection;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.UiFoodSelector;

public class UiTescoFoodSelectionController implements UiFoodSelector {

   @Override public boolean isSelected( FoodPortion food ) {
      return false;
   }//End Method

   @Override public void deselect( FoodPortion food ) {
      System.out.println( "deselected " + food.food().get().properties().nameProperty().get() );
   }//End Method

   @Override public void select( FoodPortion food ) {
      System.out.println( "selected " + food.food().get().properties().nameProperty().get() );
   }//End Method

}//End Class
