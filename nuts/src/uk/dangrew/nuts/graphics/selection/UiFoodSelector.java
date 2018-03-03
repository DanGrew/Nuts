package uk.dangrew.nuts.graphics.selection;

import uk.dangrew.nuts.food.FoodPortion;

public interface UiFoodSelector {

   public boolean isSelected( FoodPortion food );
   
//   public boolean isSelected( Food food );

   public void deselect( FoodPortion food );

   public void select( FoodPortion food );

}//End Interface

