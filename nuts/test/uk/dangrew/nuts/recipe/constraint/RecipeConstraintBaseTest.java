package uk.dangrew.nuts.recipe.constraint;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;

public class RecipeConstraintBaseTest {

   private List< Food > foods;
   private List< LinearConstraint > unconditionalConstraints;
   private RecipeConstraintBase systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      foods = Arrays.asList( new FoodItem( "Item" ) );
      unconditionalConstraints = new ArrayList<>();
      systemUnderTest = new RecipeConstraintBase( ConstraintType.Ingredient ){
         
         @Override protected List< LinearConstraint > unconditionalGenerate( List< Food > foods ) {
            return unconditionalConstraints;
         }
      };
   }//End Method

   @Test public void shouldNotGenerateIfNotEnabled() {
      assertThat( systemUnderTest.generate( foods ), is( unconditionalConstraints ) );
      systemUnderTest.enabled().set( false );
      assertThat( systemUnderTest.generate( foods ), is( empty() ) );
   }//End Method

}//End Class
