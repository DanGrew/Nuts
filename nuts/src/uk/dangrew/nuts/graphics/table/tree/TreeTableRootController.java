package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.control.TreeItem;
import uk.dangrew.kode.event.structure.Event;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanController;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionEvent;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionRequest;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.template.Template;

public class TreeTableRootController extends TreeTableHolderControlsImpl implements TreeTableHolderControls {

   private final DayPlanController dayPlanController;
   private final DayPlan plan;
   
   public TreeTableRootController( 
            DayPlan plan, 
            TreeTableRootItem treeItem, 
            DayPlanController dayPlanController 
   ) {
      super( 
               new FoodPortion( plan, 100 ),
               plan,
               treeItem
      );
      this.plan = plan;
      this.dayPlanController = dayPlanController;
   }//End Constructor
   
   public void add() {
      requestAddTo( plan );
   }//End Method
   
   @Override public void requestAddTo( FoodHolder holder ) {
      Template temporaryStorage = new Template( "Preview" );
      temporaryStorage.goalAnalytics().goal().set( plan.goalAnalytics().goal().get() );
      holder.portions().forEach( p -> temporaryStorage.portions().add( p ) );
      
      new FoodSelectionEvent().fire( new Event<>( new FoodSelectionRequest( temporaryStorage ) ) );
      temporaryStorage.portions().stream()
         .filter( p -> !holder.portions().contains( p ) )
         .forEach( p -> addUsingController( holder, p ) );
   }//End Method
   
   private void addUsingController( FoodHolder holder, FoodPortion portion ) {
      dayPlanController.add( portion, holder );
   }//End Method
   
   public void copy(){
      //Do nothing, cannot copy the day plan.
   }//End Method
   
   @Override public void copy( FoodPortion portion ) {
      dayPlanController.add( portion, plan );
   }//End Method
   
   @Override public void copy( FoodPortion portion, FoodHolder holder ) {
      dayPlanController.add( portion, holder );
   }//End Method
   
   public void remove(){
      //Do nothing, cannot remove the root.
   }//End Method
   
   public void remove( TreeItem< TreeTableController<FoodPortion> > treeItem ) {
      this.dayPlanController.remove( treeItem.getValue().concept(), plan );
   }//End Method
   
   public void moveUp(){
      //Do nothing, only one root
   }//End Method
   
   public void moveDown(){
      //Do nothing, only one root   
   }//End Method
   
}//End Class
