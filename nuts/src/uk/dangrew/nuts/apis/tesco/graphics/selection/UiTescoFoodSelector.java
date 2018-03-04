package uk.dangrew.nuts.apis.tesco.graphics.selection;

import uk.dangrew.nuts.apis.tesco.item.TescoFoodReference;

public interface UiTescoFoodSelector {

   public boolean isSelected( TescoFoodReference food );
   
//   public boolean isSelected( Food food );

   public void deselect( TescoFoodReference food );

   public void select( TescoFoodReference food );

}//End Interface

