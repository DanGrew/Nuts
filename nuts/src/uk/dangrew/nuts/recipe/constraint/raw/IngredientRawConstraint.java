package uk.dangrew.nuts.recipe.constraint.raw;

import java.util.List;

import org.apache.commons.math3.optim.linear.Relationship;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;

public class IngredientRawConstraint extends RawBoundConstraint< Food > implements RecipeConstraint {

   public IngredientRawConstraint() {
      this( null, null, null );
   }//End Constructor
   
   public IngredientRawConstraint( Food ingredient, Relationship relationship, Double amountOfIngredient ) {
      super( ConstraintType.IngredientRaw, ingredient, relationship, amountOfIngredient );
   }//End Constructor
   
   @Override public String subjectDisplayName() {
      return subject().get() == null ? "No Ingredient" : subject().get().properties().nameProperty().get();
   }//End Method
   
   @Override protected double coefficientFor( Food food ) {
      if ( food.properties().id().equals( subject().get().properties().id() ) ) {
         return 1.0;
      } else {
         return 0.0;
      }
   }//End Method
   
   @Override public ConstraintType type() {
      return ConstraintType.IngredientRaw;
   }//End Method

   @Override protected boolean hasSufficientParameters( List< Food > foods ){
      if ( super.hasSufficientParameters( foods ) ) {
         return foods.contains( subject().get() );
      }
      return false;
   }//End Method

}//End Class
