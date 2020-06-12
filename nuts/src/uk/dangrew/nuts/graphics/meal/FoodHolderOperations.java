package uk.dangrew.nuts.graphics.meal;

import uk.dangrew.kode.javafx.table.controller.ConceptTableController;
import uk.dangrew.nuts.food.FoodPortion;

public interface FoodHolderOperations extends ConceptTableController< FoodPortion >{

   public void moveUp();

   public void moveDown();
   
}//End Interface

