package uk.dangrew.nuts.recipe.constraint.tightbound;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;
import uk.dangrew.nuts.recipe.constraint.RecipeConfiguration;

public class IngredientConstraints extends TightBoundConstraints< Food > {

   public IngredientConstraints() {
      super(
               ConstraintType.IngredientBounds,
               IngredientConstraint::new
      );
      description().set( "Bounds for Ingredients" );
   }//End Constructor
   
   @Override public void configure( RecipeConfiguration configuration ) {
      configuration.ingredients().forEach( this::putConstraintFor );
      configuration.ingredients().addListener( new FunctionListChangeListenerImpl<>( 
               this::putConstraintFor, this::removeConstraintFor 
      ) );
   }//End Constructor
   
}//End Class
