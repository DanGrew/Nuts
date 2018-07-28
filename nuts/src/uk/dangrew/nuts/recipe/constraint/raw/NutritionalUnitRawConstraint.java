package uk.dangrew.nuts.recipe.constraint.raw;

import org.apache.commons.math3.optim.linear.Relationship;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;

public class NutritionalUnitRawConstraint extends RawBoundConstraint< NutritionalUnit > implements RecipeConstraint {

   public NutritionalUnitRawConstraint() {
      this( null, null, null );
   }//End Constructor
   
   public NutritionalUnitRawConstraint( NutritionalUnit unit, Relationship relationship, Double bound ) {
      super( ConstraintType.NutritionalUnitRaw, unit, relationship, bound );
   }//End Constructor
   
   @Override public String subjectDisplayName() {
      return subject().get() == null ? "No Unit" : subject().get().displayName();
   }//End Method
   
   @Override protected double coefficientFor( Food food ) {
      return food.nutrition().of( subject().get() ).get();
   }//End Method
   
   @Override public ConstraintType type() {
      return ConstraintType.NutritionalUnitRaw;
   }//End Method

}//End Class
