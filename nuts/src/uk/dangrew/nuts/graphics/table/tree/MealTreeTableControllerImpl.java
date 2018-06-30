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
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.meal.MealTableController;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionForMealEvent;
import uk.dangrew.nuts.meal.Meal;

/**
 * The {@link MealTableController} is responsible for controlling and updating the {@link MealTable}.
 */
public class MealTreeTableControllerImpl implements MealTableController, ConceptTreeTableController< FoodPortion > {

   private DayPlanTreeTable table;
   
   public MealTreeTableControllerImpl() {
   }//End Constructor
   
   @Override public void associate( DayPlanTreeTable table ) {
      this.table = table;
   }//End Method
   
   /**
    * Method to show the given {@link Meal} in the {@link MealTable}.
    * @param meal the {@link Meal} to show.
    */
   @Override public void showMeal( Meal meal ) {
      table.setFocus( meal );
   }//End Method
   
   /**
    * Getter for the {@link Meal} being shown.
    * @return the {@link Meal} being shown.
    */
   @Override public Meal getShowingMeal(){
      return table.getFocus();
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public FoodPortion createConcept() {
      TreeItem< TreeTableBranchController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return null;
      }
      
      selection.getValue().add();
      
      return null;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public void removeSelectedConcept() {
      TreeItem< TreeTableBranchController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      selection.getValue().remove();
   }//End Method
   
   @Override public void copySelectedConcept() {
      TreeItem< TreeTableBranchController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      selection.getValue().copy();
   }//End Method 

   @Override public void moveUp() {
      TreeItem< TreeTableBranchController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      selection.getValue().moveUp();
   }//End Method
   
   @Override public void moveDown() {
      TreeItem< TreeTableBranchController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      selection.getValue().moveDown();
   }//End Method
   
}//End Class
