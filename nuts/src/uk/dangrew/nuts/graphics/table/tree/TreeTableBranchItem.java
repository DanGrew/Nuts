package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.control.TreeItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.FoodHolder;

public class TreeTableBranchItem extends TreeItem< TreeTableController<FoodPortion> >{

   public TreeTableBranchItem( 
            FoodPortion concept, 
            FoodHolder holder, 
            TreeTableHolderControls parent
   ) {
      setValue( 
               new TreeTableBranchController( 
                  concept,
                  holder,
                  this, 
                  parent
               ) 
      );
      setExpanded( true );
   }//End Constructor
   
}//End Class
