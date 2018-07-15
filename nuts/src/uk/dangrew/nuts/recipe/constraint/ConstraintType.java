package uk.dangrew.nuts.recipe.constraint;

import java.util.function.Supplier;

public enum ConstraintType {

   Bound( BoundConstraint::new ),
   Ingredient( IngredientConstraint::new ),
   Ratio( RatioConstraint::new );
   
   private final Supplier< RecipeConstraint > constraintSupplier;
   
   private ConstraintType( Supplier< RecipeConstraint > constraintSupplier ) {
      this.constraintSupplier = constraintSupplier;
   }//End Constructor
   
   public RecipeConstraint generate(){
      return constraintSupplier.get();
   }//End Method
   
}//End Enum
