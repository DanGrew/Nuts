package uk.dangrew.nuts.graphics.table.tree;

import uk.dangrew.kode.javafx.table.base.ConceptTableRowImpl;
import uk.dangrew.nuts.food.FoodPortion;

public class TreeTableLeafController extends ConceptTableRowImpl< FoodPortion > implements TreeTableController<FoodPortion>{

   private final TreeTableHolderControls parent;
   private final TreeTableLeafItem treeItem;
   
   public TreeTableLeafController( 
            FoodPortion concept, 
            TreeTableLeafItem treeItem,
            TreeTableHolderControls parent
   ) {
      super( concept );
      this.treeItem = treeItem;
      this.parent = parent;
   }//End Constructor
   
   @Override public void add() {
      parent.add();
   }//End Method
   
   @Override public void copy(){
      parent.copy( concept() );
   }//End Method
   
   @Override public void remove(){
      parent.remove( treeItem );
   }//End Method
   
   @Override public void moveUp(){
      parent.moveUp( concept() );
   }//End Method
   
   @Override public void moveDown(){
      parent.moveDown( concept() );
   }//End Method
   
}//End Class
