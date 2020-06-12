package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.control.TreeItem;
import uk.dangrew.nuts.food.FoodPortion;

public class TreeTableLeafItem extends TreeItem< TreeTableController<FoodPortion> >{

   public TreeTableLeafItem( 
            FoodPortion concept, 
            TreeTableHolderControls parent
   ) {
      setValue( 
               new TreeTableLeafController( 
                  concept,
                  this, 
                  parent
               ) 
      );
      setExpanded( true );
   }//End Constructor
   
}//End Class
