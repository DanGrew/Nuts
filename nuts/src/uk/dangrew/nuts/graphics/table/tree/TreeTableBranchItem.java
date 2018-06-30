package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.control.TreeItem;
import uk.dangrew.nuts.food.FoodPortion;

public class TreeTableBranchItem extends TreeItem< TreeTableBranchController >{

   public TreeTableBranchItem( FoodPortion concept ) {
      setValue( new TreeTableBranchController( concept, this ) );
      setExpanded( true );
   }//End Constructor
   
}//End Class
