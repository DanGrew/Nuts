package uk.dangrew.nuts.graphics.table.tree;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;

public interface TreeTableController extends ConceptTableRow< FoodPortion >{
   
   public void add();

   public void copy();

   public void remove();

   public void moveUp();
   
   public void moveDown();
   
}//End Interface
