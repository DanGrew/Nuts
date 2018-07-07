/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table.tree;

import javafx.scene.control.TreeItem;
import uk.dangrew.kode.javafx.tree.TreeStreamer;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.meal.FoodHolderOperations;

public class DayPlanTreeTableController implements FoodHolderOperations, ConceptTreeTableController< FoodPortion > {

   private final TreeStreamer treeStreamer;
   private DayPlanTreeTable table;
   
   public DayPlanTreeTableController() {
      this( new TreeStreamer() );
   }//End Constructor
   
   DayPlanTreeTableController( TreeStreamer treeStreamer ) {
      this.treeStreamer = treeStreamer;
   }//End Constructor
   
   @Override public void associate( DayPlanTreeTable table ) {
      this.table = table;
   }//End Method
   
   public void show( DayPlan plan ) {
      table.setFocus( plan );
   }//End Method
   
   public DayPlan getShowing(){
      return table.getFocus();
   }//End Method
   
   @Override public FoodPortion createConcept() {
      TreeItem< TreeTableController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return null;
      }
      
      selection.getValue().add();
      
      return null;
   }//End Method
   
   @Override public void removeSelectedConcept() {
      TreeItem< TreeTableController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      selection.getValue().remove();
   }//End Method
   
   @Override public void copySelectedConcept() {
      TreeItem< TreeTableController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      selection.getValue().copy();
   }//End Method 

   @Override public void moveUp() {
      TreeItem< TreeTableController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      selection.getValue().moveUp();
      select( selection.getValue().concept() );
   }//End Method
   
   @Override public void moveDown() {
      TreeItem< TreeTableController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      selection.getValue().moveDown();
      select( selection.getValue().concept() );
   }//End Method
   
   private void select( FoodPortion portion ) {
      treeStreamer.flatten( table.getRoot() )
         .filter( item -> item.getValue().concept() == portion )
         .findFirst()
         .ifPresent( item -> table.getSelectionModel().select( item ) );
   }//End Method
   
}//End Class
