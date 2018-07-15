package uk.dangrew.nuts.recipe.constraint;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ConstraintTypeTest {

   @Test public void shouldProvideConstraint() {
      assertThat( ConstraintType.Bound.generate(), is( instanceOf( BoundConstraint.class ) ) );
      assertThat( ConstraintType.Ingredient.generate(), is( instanceOf( IngredientConstraint.class ) ) );
      assertThat( ConstraintType.Ratio.generate(), is( instanceOf( RatioConstraint.class ) ) );
   }//End Method

}//End Class
