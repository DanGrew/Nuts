package uk.dangrew.nuts.graphics.recipe.creator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.recipe.constraint.RecipeConstraint;
import uk.dangrew.nuts.recipe.constraint.tightbound.IngredientConstraint;

public class UiRecipeConstraintCheckBoxControllerTest {

   private RecipeConstraint constraint;
   @Mock private UiRecipeConstraintController controller;
   private UiRecipeConstraintCheckBoxController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      constraint = new IngredientConstraint();
      systemUnderTest = new UiRecipeConstraintCheckBoxController( controller );
      systemUnderTest.propertyFor( constraint );
   }//End Method

   @Test public void shouldProvideModelValue() {
      assertThat( systemUnderTest.getModelValue( constraint ), is( true ) );
      constraint.enabled().set( false );
      assertThat( systemUnderTest.getModelValue( constraint ), is( false ) );
   }//End Method
   
   @Test public void shouldApplyValue() {
      systemUnderTest.apply( systemUnderTest.propertyFor( constraint ), true, false );
      assertThat( constraint.enabled().get(), is( false ) );
      verify( controller ).recalculate();
   }//End Method

}//End Class
