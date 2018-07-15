/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.recipe.creator;

import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.recipe.constraint.RecipeConfiguration;

public class UiRecipeConstraintColumns implements ConceptTableColumnsPopulator< RecipeConfiguration >{

   static final String COLUMN_TITLE = "Title";
   
   private final TableConfiguration configuration;

   public UiRecipeConstraintColumns( TableComponents< RecipeConfiguration > components ) {
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   public void populateColumns( ConceptTable< RecipeConfiguration > table ) {
      configuration.initialiseCustomStringPropertyColumn( table, COLUMN_TITLE, 1.0, r -> ( ( UiRecipeConstaintRow )r ).constraint().description() );
   }//End Method
   
}//End Class
