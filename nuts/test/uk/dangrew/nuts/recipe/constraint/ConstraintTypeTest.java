package uk.dangrew.nuts.recipe.constraint;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.recipe.constraint.raw.IngredientRawConstraint;
import uk.dangrew.nuts.recipe.constraint.raw.NutritionalUnitRawConstraint;
import uk.dangrew.nuts.recipe.constraint.tightbound.IngredientConstraint;
import uk.dangrew.nuts.recipe.constraint.tightbound.IngredientConstraints;
import uk.dangrew.nuts.recipe.constraint.tightbound.NutritionalUnitConstraint;
import uk.dangrew.nuts.recipe.constraint.tightbound.NutritionalUnitConstraints;

public class ConstraintTypeTest {

   @Test public void shouldProvideConstraint() {
      RecipeConfiguration configuration = new RecipeConfiguration();
      assertThat( ConstraintType.NutritionalUnitRaw.generate( configuration ), is( instanceOf( NutritionalUnitRawConstraint.class ) ) );
      assertThat( ConstraintType.IngredientRaw.generate( configuration ), is( instanceOf( IngredientRawConstraint.class ) ) );
      assertThat( ConstraintType.NutritionalUnit.generate( configuration ), is( instanceOf( NutritionalUnitConstraint.class ) ) );
      assertThat( ConstraintType.Ingredient.generate( configuration ), is( instanceOf( IngredientConstraint.class ) ) );
      assertThat( ConstraintType.NutritionalUnitBounds.generate( configuration ), is( instanceOf( NutritionalUnitConstraints.class ) ) );
      assertThat( ConstraintType.IngredientBounds.generate( configuration ), is( instanceOf( IngredientConstraints.class ) ) );
      assertThat( ConstraintType.Ratio.generate( configuration ), is( instanceOf( RatioConstraint.class ) ) );
   }//End Method
   
   @Test public void shouldConfigure(){
      RecipeConfiguration configuration = new RecipeConfiguration();
      configuration.ingredients().add( new FoodItem( "Item" ) );
      IngredientConstraints constraint = ( IngredientConstraints ) ConstraintType.IngredientBounds.generate( configuration );
      assertThat( constraint.constraintFor( configuration.ingredients().get( 0 ) ), is( notNullValue() ) );
   }//End Method

}//End Class
