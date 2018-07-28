package uk.dangrew.nuts.recipe.constraint;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.commons.math3.optim.PointValuePair;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;

public class RecipeAlgorithmTest {

   private RecipeConfiguration configuration;
   private RecipeAlgorithm systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      configuration = new RecipeConfiguration();
      systemUnderTest = new RecipeAlgorithm( configuration );
   }//End Method

   @Test public void shouldReturnEmptyMealIfNoSolution() {
      assertThat( systemUnderTest.solve(), is( Optional.empty() ) );
   }//End Method
   
   @Test public void shouldScaleIngredientsBasedOnSolution() {
      configuration.ingredients().add( new FoodItem( "Item1" ) );
      configuration.ingredients().add( new FoodItem( "Item2" ) );
      configuration.ingredients().add( new FoodItem( "Item3" ) );
      
      PointValuePair solution = new PointValuePair( new double[]{ 1,  2, 3 }, 200 );
      configuration.function().set( mock( RecipeFunction.class ) );
      when( configuration.function().get().solve( anyList(), any() ) ).thenReturn( Optional.of( solution ) );
      
      Meal result = systemUnderTest.solve().get();
      assertThat( result.portions().get( 0 ).food().get(), is( configuration.ingredients().get( 0 ) ) );
      assertThat( result.portions().get( 0 ).portion().get(), is( 100.0 ) );
      assertThat( result.portions().get( 1 ).food().get(), is( configuration.ingredients().get( 1 ) ) );
      assertThat( result.portions().get( 1 ).portion().get(), is( 200.0 ) );
      assertThat( result.portions().get( 2 ).food().get(), is( configuration.ingredients().get( 2 ) ) );
      assertThat( result.portions().get( 2 ).portion().get(), is( 300.0 ) );
   }//End Method

}//End Class
