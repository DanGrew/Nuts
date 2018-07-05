package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.control.TreeItem;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptTableRowImpl;
import uk.dangrew.nuts.meal.FoodHolder;

public abstract class TreeTableHolderControlsImpl extends ConceptTableRowImpl< FoodPortion > implements TreeTableHolderControls {

   private final FoodHolder holder;
   private final TreeItem< TreeTableController > treeItem;
   
   public TreeTableHolderControlsImpl(
            FoodPortion concept,
            FoodHolder holder, 
            TreeItem< TreeTableController > treeItem 
   ) {
      super( concept );
      this.treeItem = treeItem;
      this.holder = holder;
      
      this.monitorHolderPortions();
   }//End Constructor
   
   protected FoodHolder foodHolder(){
      return holder;
   }//End Method
   
   protected TreeItem< TreeTableController > treeItem(){
      return treeItem;
   }//End Method
   
   private void monitorHolderPortions() {
      holder.portions().forEach( this::add );
      holder.portions().addListener( new FunctionListChangeListenerImpl<>( 
               this::add, this::remove, null 
      ) );
   }//End Method
   
   private void add( FoodPortion portion ){
      Food portionFood = portion.food().get();
      if ( portionFood instanceof FoodHolder ) {
         treeItem.getChildren().add( 
                  holder.portions().indexOf( portion ), 
                  new TreeTableBranchItem( portion, ( FoodHolder )portionFood, this ) 
         );
      } else {
         treeItem.getChildren().add( 
                  holder.portions().indexOf( portion ), 
                  new TreeTableLeafItem( portion, this ) 
         );
      }
   }//End Method
   
   @Override public abstract void add();
   
   @Override public abstract void requestAddTo( FoodHolder holder );
   
   @Override public abstract void copy();
   
   @Override public abstract void copy( FoodPortion concept );
   
   @Override public abstract void copy( FoodPortion concept, FoodHolder holder );
   
   @Override public abstract void remove();
   
   private void remove( FoodPortion portion ) {
      treeItem.getChildren().stream()
         .filter( t -> t.getValue().concept() == portion )
         .findFirst()
         .ifPresent( t -> treeItem.getChildren().remove( t ) );
   }//End Method
   
   @Override public abstract void remove( TreeItem< TreeTableController > treeItem );
   
   @Override public abstract void moveUp();
   
   @Override public void moveUp( FoodPortion portion ) {
      int index = holder.portions().indexOf( portion );
      if ( index <= 0 || index >= holder.portions().size() ) {
         return;
      }
      
      FoodPortion current = holder.portions().get( index - 1 );
      holder.swap( current, portion );
   }//End Method
   
   @Override public abstract void moveDown();
   
   @Override public void moveDown( FoodPortion portion ) {
      int index = holder.portions().indexOf( portion );
      if ( index < 0 || index >= holder.portions().size() - 1 ) {
         return;
      }
      
      FoodPortion next = holder.portions().get( index + 1 );
      holder.swap( portion, next );
   }//End Method

}//End Class
