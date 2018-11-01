/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table.tree;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;
import uk.dangrew.kode.javafx.tree.TreeStreamer;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.database.RecipeShareController;
import uk.dangrew.nuts.graphics.database.RecipeShareControllerImpl;
import uk.dangrew.nuts.graphics.meal.FoodHolderOperations;
import uk.dangrew.nuts.graphics.recipe.integration.RecipeGeneratorController;
import uk.dangrew.nuts.graphics.recipe.integration.RecipeGeneratorControllerImpl;
import uk.dangrew.nuts.meal.FoodHolder;
import uk.dangrew.nuts.store.Database;

public class DayPlanTreeTableController implements FoodHolderOperations, 
                                                   ConceptTreeTableController< FoodPortion >, 
                                                   RecipeShareController, 
                                                   RecipeGeneratorController 
{

   private final Database database;
   private final TreeStreamer treeStreamer;
   private final RecipeShareControllerImpl shareController;
   private final RecipeGeneratorControllerImpl generatorController;
   private DayPlanTreeTable table;
   
   public DayPlanTreeTableController( Database database ) {
      this( database, new TreeStreamer(), new RecipeShareControllerImpl(), new RecipeGeneratorControllerImpl( database ) );
   }//End Constructor
   
   DayPlanTreeTableController( 
            Database database,
            TreeStreamer treeStreamer, 
            RecipeShareControllerImpl shareController, 
            RecipeGeneratorControllerImpl generatorController 
   ) {
      this.database = database;
      this.treeStreamer = treeStreamer;
      this.shareController = shareController;
      this.generatorController = generatorController;
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
   
   @Override public void share() {
      TreeItem< TreeTableController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      shareController.share( selection.getValue().concept().food().get() );
   }//End Method
   
   @Override public void generate() {
      TreeItem< TreeTableController > selection = table.getSelectionModel().getSelectedItem();
      if ( selection == null ) {
         return;
      }
      
      Food food = selection.getValue().concept().food().get();
      if ( food instanceof FoodHolder ) {
         FoodHolder holder = ( FoodHolder ) food;
         generatorController.generateFor( holder ).ifPresent( recipe -> {
            List< FoodPortion > toRemove = new ArrayList<>( holder.portions() );
            toRemove.forEach( p -> database.dayPlanController().remove( p, getShowing() ) );
            recipe.portions().forEach( p -> database.dayPlanController().add( p, holder ) );
         } );
      }
   }//End Method
   
   private void select( FoodPortion portion ) {
      treeStreamer.flatten( table.getRoot() )
         .filter( item -> item.getValue().concept() == portion )
         .findFirst()
         .ifPresent( item -> table.getSelectionModel().select( item ) );
   }//End Method
   
}//End Class
