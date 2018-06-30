package uk.dangrew.nuts.graphics.table.tree;

import java.util.Optional;

import javafx.scene.control.TreeItem;
import uk.dangrew.kode.event.structure.Event;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionForMealEvent;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.meal.Meal;

public class TreeTableBranchController extends ConceptTableRow< FoodPortion >{

   private final TreeTableBranchItem treeItem;
   
   public TreeTableBranchController( FoodPortion concept, TreeTableBranchItem treeItem ) {
      super( concept );
      this.treeItem = treeItem;
      
      meal().ifPresent( this::monitorMealPortions );
   }//End Constructor
   
   private Optional< Meal > meal(){
      if ( concept().food().get() instanceof Meal ) {
         return Optional.of( ( Meal )concept().food().get() );
      }
      return Optional.empty();
   }//End Method
   
   private void monitorMealPortions( Meal meal ) {
      meal.portions().forEach( this::add );
      meal.portions().addListener( new FunctionListChangeListenerImpl<>( 
               this::add, this::remove, null 
      ) );
   }//End Method
   
   private void raiseFoodSelectionEvent( Meal meal ){
      new FoodSelectionForMealEvent().fire( new Event<>( meal ) );
   }//End Method
   
   public void add( FoodPortion portion ){
      treeItem.getChildren().add( new TreeTableBranchItem( portion ) );
   }//End Method
   
   public void add() {
      meal().ifPresent( this::raiseFoodSelectionEvent );
   }//End Method
   
   public void copy(){
      
   }//End Method
   
   public void remove(){
      TreeItem< TreeTableBranchController > parent = treeItem.getParent();
      if ( parent == null ) {
         return;
      }
      parent.getValue().remove( treeItem );
   }//End Method
   
   private void remove( FoodPortion portion ) {
      treeItem.getChildren().stream()
         .filter( t -> t.getValue().concept() == portion )
         .findFirst()
         .ifPresent( this::remove );
   }//End Method
   
   public void remove( TreeItem< TreeTableBranchController > treeItem ) {
      if ( concept().food().get() instanceof Meal ) {
         Meal meal = ( Meal ) concept().food().get();
         meal.portions().remove( treeItem.getValue().concept() );
      }
      this.treeItem.getChildren().remove( treeItem );
   }//End Method
   
   public void moveUp(){
      TreeItem< TreeTableBranchController > parent = treeItem.getParent();
      if ( parent == null ) {
         return;
      }
      
      int index = parent.getChildren().indexOf( treeItem );
      if ( index == 0 ) {
         return;
      }
      
      TreeItem< TreeTableBranchController > current = parent.getChildren().set( index - 1, treeItem );
      parent.getChildren().set( index, current );
   }//End Method
   
   public void moveDown(){
      TreeItem< TreeTableBranchController > parent = treeItem.getParent();
      if ( parent == null ) {
         return;
      }
      
      int index = parent.getChildren().indexOf( treeItem );
      if ( index == parent.getChildren().size() - 1 ) {
         return;
      }
      
      TreeItem< TreeTableBranchController > current = parent.getChildren().set( index + 1, treeItem );
      parent.getChildren().set( index, current );
   }//End Method

}//End Class
