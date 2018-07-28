package uk.dangrew.nuts.recipe.constraint.tightbound;

import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;

public class NutritionalUnitConstraints extends TightBoundConstraints< NutritionalUnit > {

   public NutritionalUnitConstraints() {
      super(
               ConstraintType.NutritionalUnitBounds,
               NutritionalUnitConstraint::new
      );
      description().set( "Bounds for Nutritional Units" );
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         putConstraintFor( unit );
      }
   }//End Constructor
   
}//End Class
