package uk.dangrew.nuts.graphics.recipe.creator;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.graphics.recipe.creator.composite.UiTightBoundConstraintsConfigurer;
import uk.dangrew.nuts.recipe.constraint.RatioConstraint;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;
import uk.dangrew.nuts.recipe.constraint.tightbound.IngredientConstraints;
import uk.dangrew.nuts.recipe.constraint.tightbound.NutritionalUnitConstraints;

public class UiRecipeConstraintSelector extends BorderPane {

   private final UiRecipeConstraintController controller;
   
   public UiRecipeConstraintSelector( UiRecipeConstraintController controller ) {
      this.controller = controller;
      this.setTop( new UiFunctionConfigurer( controller, controller.configuration().function().get() ) );
   }//End Constructor
   
   public void select( RecipeConstraint constraint ) {
      switch ( constraint.type() ) {
//         case NutritionalUnitRaw:
//            setCenter( new UiNutritionalUnitConstraintConfigurer( controller, ( NutritionalUnitRawConstraint ) constraint ) );
//            break;
//         case IngredientRaw:
//            setCenter( new UiIngredientConstraintConfigurer( controller, ( IngredientRawConstraint ) constraint ) );
//            break;
         case Ratio:
            setCenter( new UiRatioConstraintConfigurer( controller, ( RatioConstraint ) constraint ) );
            break;
         case NutritionalUnitBounds:
            setCenter( new UiTightBoundConstraintsConfigurer<>( 
                     ( NutritionalUnitConstraints ) constraint,
                     controller
            ) );
            break;
         case IngredientBounds:
            setCenter( new UiTightBoundConstraintsConfigurer<>( 
                     ( IngredientConstraints ) constraint,
                     controller
            ) );
            break;
         default:
            setCenter( null );
      }
   }//End Method
   
}//End Class
