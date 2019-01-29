/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.recipe.creator;

import uk.dangrew.nuts.graphics.common.CheckBoxController;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;

public class UiRecipeConstraintColumns implements ConceptTableColumnsPopulator< RecipeConstraint >{

   static final String COLUMN_TITLE = "Title";
   
   private final TableConfiguration configuration;
   private final CheckBoxController< RecipeConstraint > controller;

   public UiRecipeConstraintColumns( TableComponents< RecipeConstraint > components ) {
      this.configuration = new TableConfiguration();
      this.controller = components.checkBoxController();
   }//End Constructor
   
   public void populateColumns( ConceptTable< RecipeConstraint > table ) {
      configuration.configureCheckBoxController( 
               new TableViewColumnConfigurer<>( table ), 
               controller, 
               0.1 
      );
      configuration.initialiseStringColumn( 
               new TableViewColumnConfigurer<>( table ), 
               COLUMN_TITLE, 
               0.9, 
               RecipeConstraint::description, 
               false 
      ); 
   }//End Method
   
}//End Class
