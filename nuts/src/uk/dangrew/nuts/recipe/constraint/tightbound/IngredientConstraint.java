package uk.dangrew.nuts.recipe.constraint.tightbound;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;
import uk.dangrew.nuts.recipe.constraint.raw.IngredientRawConstraint;

public class IngredientConstraint extends TightBoundConstraint< Food > {

   public IngredientConstraint() {
      super( 
               ConstraintType.Ingredient,
               IngredientRawConstraint::new 
      );
   }//End Constructor
   
}//End Class
