package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.control.TreeItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.FoodHolder;

public class TreeTableBranchController extends TreeTableHolderControlsImpl implements TreeTableHolderControls{

   private final TreeTableHolderControls parent;
   
   public TreeTableBranchController( 
            FoodPortion concept, 
            FoodHolder holder,
            TreeTableBranchItem treeItem, 
            TreeTableHolderControls parent
   ) {
      super( concept, holder, treeItem );
      this.parent = parent;
   }//End Constructor
   
   @Override public void add() {
      parent.requestAddTo( foodHolder() );
   }//End Method
   
   @Override public void requestAddTo( FoodHolder holder ) {
      parent.requestAddTo( holder );
   }//End Method
   
   @Override public void copy(){
      this.parent.copy( concept() );
   }//End Method
   
   @Override public void copy( FoodPortion concept ) {
      this.parent.copy( concept, foodHolder() );
   }//End Method
   
   @Override public void copy( FoodPortion concept, FoodHolder holder ) {
      this.parent.copy( concept, holder );
   }//End Method
   
   @Override public void remove(){
      this.parent.remove( treeItem() );
   }//End Method
   
   @Override public void remove( TreeItem< TreeTableController<FoodPortion> > treeItem ) {
      this.parent.remove( treeItem );
   }//End Method
   
   @Override public void moveUp(){
      this.parent.moveUp( concept() );
   }//End Method
   
   @Override public void moveDown(){
      this.parent.moveDown( concept() );
   }//End Method
   
}//End Class
