package uk.dangrew.nuts.graphics.selection.view;

public interface UiFoodSelector< FoodTypeT > {

   public boolean isSelected( FoodTypeT food );
   
   public void deselect( FoodTypeT food );

   public void select( FoodTypeT food );

}//End Interface

