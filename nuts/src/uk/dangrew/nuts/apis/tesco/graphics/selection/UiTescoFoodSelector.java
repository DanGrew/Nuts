package uk.dangrew.nuts.apis.tesco.graphics.selection;

import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;

public interface UiTescoFoodSelector {

   public boolean isSelected( TescoFoodDescription food );
   
//   public boolean isSelected( Food food );

   public void deselect( TescoFoodDescription food );

   public void select( TescoFoodDescription food );

}//End Interface

