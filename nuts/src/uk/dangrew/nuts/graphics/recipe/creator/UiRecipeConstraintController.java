package uk.dangrew.nuts.graphics.recipe.creator;

import java.util.Optional;

import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import uk.dangrew.nuts.graphics.common.UiEnumTypeSelectionDialog;
import uk.dangrew.nuts.graphics.meal.MealTableController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableViewController;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;
import uk.dangrew.nuts.recipe.constraint.RecipeAlgorithm;
import uk.dangrew.nuts.recipe.constraint.RecipeConfiguration;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;

public class UiRecipeConstraintController implements ConceptTableViewController< RecipeConfiguration > {

   private final RecipeConfiguration selected;
   private final RecipeAlgorithm algorithm;
   private final UiEnumTypeSelectionDialog< ConstraintType > constraintSelector;
   
   private ConceptTable< RecipeConfiguration > table;
   private UiRecipeConstraintSelector selector;
   private MealTableController resultController;
   
   public UiRecipeConstraintController( RecipeConfiguration configuration ) {
      this.selected = configuration;
      this.selected.function().get().unit().set( NutritionalUnit.Fat );
      this.selected.function().get().goalType().set( GoalType.MAXIMIZE );
      this.algorithm = new RecipeAlgorithm( configuration );
      this.constraintSelector = new UiEnumTypeSelectionDialog<>( ConstraintType.class, ConstraintType.Bound );
   }//End Constructor
   
   @Override public void associate( ConceptTable< RecipeConfiguration > table ) {
      this.table = table;
      this.table.getSelectionModel().selectedItemProperty().addListener( ( s, o, n ) -> select() );
   }//End Method
   
   public void associate( UiRecipeConstraintSelector selector, MealTableController resultController ) {
      this.selector = selector;
      this.resultController = resultController;
   }//End Method
   
   private void select() {
      UiRecipeConstaintRow selected = internal_getSelectedRow();
      if ( selected == null ) {
         return;
      }
      this.selector.select( selected.constraint() );
   }//End Method
   
   public RecipeConfiguration configuration() {
      return selected;
   }//End Method
   
   @Override public RecipeConfiguration createConcept() {
      if ( selected == null ) {
         return null;
      }
      
      Optional< ConstraintType > result = constraintSelector.friendly_showAndWait();
      if ( result == null || !result.isPresent() ) {
         return null;
      }
      
      RecipeConstraint constraint = result.get().generate();
      selected.constraints().add( constraint );
      table.getRows().add( new UiRecipeConstaintRow( selected, constraint ) );
      return selected;
   }//End Method

   @Override public void removeSelectedConcept() {
      UiRecipeConstaintRow selectedRow = internal_getSelectedRow();
      if ( selectedRow == null ) {
         return;
      }
      
      selected.constraints().remove( selectedRow.constraint() );
   }//End Method

   @Override public void copySelectedConcept() {
   }//End Method
   
   private UiRecipeConstaintRow internal_getSelectedRow(){
      if ( selected == null ) {
         return null;
      }
      
      return ( UiRecipeConstaintRow ) table.getSelectionModel().getSelectedItem();
   }//End Method

   public void recalculate() {
      resultController.showMeal( null );
      algorithm.solve().ifPresent( resultController::showMeal );
   }//End Method
}//End Class