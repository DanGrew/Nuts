package uk.dangrew.nuts.graphics.recipe.creator;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.recipe.constraint.BoundConstraint;
import uk.dangrew.nuts.recipe.constraint.IngredientConstraint;
import uk.dangrew.nuts.recipe.constraint.RatioConstraint;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;

public class UiRecipeConstraintSelector extends BorderPane {

   private final UiRecipeConstraintController controller;
   
   public UiRecipeConstraintSelector( UiRecipeConstraintController controller ) {
      this.controller = controller;
   }//End Constructor
   
   public void select( RecipeConstraint constraint ) {
      switch ( constraint.type() ) {
         case Bound:
            setCenter( new UiBoundConstraintConfigurer( controller, ( BoundConstraint ) constraint ) );
            break;
         case Ingredient:
            setCenter( new UiIngredientConstraintConfigurer( controller, ( IngredientConstraint ) constraint ) );
            break;
         case Ratio:
            setCenter( new UiRatioConstraintConfigurer( controller, ( RatioConstraint ) constraint ) );
            break;
         default:
            setCenter( null );
      }
   }//End Method
   
}//End Class
