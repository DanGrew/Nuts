/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.recipe.creator;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.graphics.table.ConceptTableRowImpl;
import uk.dangrew.nuts.recipe.constraint.RecipeConfiguration;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;

public class UiRecipeConstaintRow extends ConceptTableRowImpl< RecipeConfiguration >{
   
   private final RecipeConstraint constraint;
   
   public UiRecipeConstaintRow( RecipeConfiguration concept, RecipeConstraint constraint ) {
      super( concept );
      this.constraint = constraint;
   }//End Constructor
   
   public RecipeConstraint constraint(){
      return constraint;
   }//End Method
   
   public ObjectProperty< String > nameProperty(){
      return new SimpleObjectProperty<>( "Recipe Constraint" );
   }//End Method

}//End Class
