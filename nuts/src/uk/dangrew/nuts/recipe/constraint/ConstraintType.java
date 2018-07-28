package uk.dangrew.nuts.recipe.constraint;

import java.util.function.Supplier;

import uk.dangrew.nuts.recipe.constraint.raw.IngredientRawConstraint;
import uk.dangrew.nuts.recipe.constraint.raw.NutritionalUnitRawConstraint;
import uk.dangrew.nuts.recipe.constraint.tightbound.IngredientConstraint;
import uk.dangrew.nuts.recipe.constraint.tightbound.IngredientConstraints;
import uk.dangrew.nuts.recipe.constraint.tightbound.NutritionalUnitConstraint;
import uk.dangrew.nuts.recipe.constraint.tightbound.NutritionalUnitConstraints;

public enum ConstraintType {

   NutritionalUnitRaw( NutritionalUnitRawConstraint::new ),
   NutritionalUnit( NutritionalUnitConstraint::new ),
   NutritionalUnitBounds( NutritionalUnitConstraints::new ),
   IngredientRaw( IngredientRawConstraint::new ),
   Ingredient( IngredientConstraint::new ),
   IngredientBounds( IngredientConstraints::new ),
   Ratio( RatioConstraint::new );
   
   private final Supplier< RecipeConstraint > constraintSupplier;
   
   private ConstraintType( Supplier< RecipeConstraint > constraintSupplier ) {
      this.constraintSupplier = constraintSupplier;
   }//End Constructor
   
   public RecipeConstraint generate( RecipeConfiguration configuration ){
      RecipeConstraint constaint = constraintSupplier.get();
      constaint.configure( configuration );
      return constaint;
   }//End Method
   
}//End Enum
