package uk.dangrew.nuts.graphics.meal;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptTableController;

public interface FoodHolderOperations extends ConceptTableController< FoodPortion >{

   public void moveUp();

   public void moveDown();
   
}//End Interface

