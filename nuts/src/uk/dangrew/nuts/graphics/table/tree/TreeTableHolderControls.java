package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.control.TreeItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.FoodHolder;

public interface TreeTableHolderControls extends TreeTableController {
   
   public void requestAddTo( FoodHolder holder );

   public void copy( FoodPortion concept );
   
   public void copy( FoodPortion concept, FoodHolder holder );
   
   public void remove( TreeItem< TreeTableController > treeItem );

   public void moveUp( FoodPortion portion );

   public void moveDown( FoodPortion portion );

}//End Interface
