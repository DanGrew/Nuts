package uk.dangrew.nuts.apis.tesco.graphics.selection;

import uk.dangrew.nuts.apis.tesco.item.TescoFoodItem;

public interface UiTescoFoodSelector {

   public boolean isSelected( TescoFoodItem food );
   
//   public boolean isSelected( Food food );

   public void deselect( TescoFoodItem food );

   public void select( TescoFoodItem food );

}//End Interface

