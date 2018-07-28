package uk.dangrew.nuts.recipe.constraint.tightbound;

import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;
import uk.dangrew.nuts.recipe.constraint.raw.NutritionalUnitRawConstraint;

public class NutritionalUnitConstraint extends TightBoundConstraint< NutritionalUnit > {

   public NutritionalUnitConstraint() {
      super( 
               ConstraintType.NutritionalUnit,
               NutritionalUnitRawConstraint::new 
      );
   }//End Constructor
   
}//End Class
