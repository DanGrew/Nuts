package uk.dangrew.nuts.recipe.constraint;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class RecipeFunctionTest {
   
   private Food food1;
   private Food food2;
   private Food food3;
   private List< Food > foods;
   private RecipeFunction systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      food1 = new FoodItem( "Item1" );
      food1.nutrition().of( NutritionalUnit.Fat ).set( 21.0 );
      food2 = new FoodItem( "Item2" );
      food2.nutrition().of( NutritionalUnit.Fat ).set( 101.0 );
      food3 = new FoodItem( "Item3" );
      food3.nutrition().of( NutritionalUnit.Fat ).set( 3.0 );
      foods = Arrays.asList( food1, food2, food3 );
      
      systemUnderTest = new RecipeFunction( NutritionalUnit.Fat, GoalType.MAXIMIZE );
   }//End Method

   @Test public void shouldGenerateConstraint() {
      LinearObjectiveFunction constraint = systemUnderTest.generate( foods ).get();
      assertThat( constraint.getCoefficients().getEntry( 0 ), is( 21.0 ) );
      assertThat( constraint.getCoefficients().getEntry( 1 ), is( 101.0 ) );
      assertThat( constraint.getCoefficients().getEntry( 2 ), is( 3.0 ) );
      assertThat( constraint.getConstantTerm(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldPrivideGoalType(){
      assertThat( systemUnderTest.goalType().get(), is( GoalType.MAXIMIZE ) );
   }//End Method

   @Test public void shouldIgnoreMissingProperties() {
      systemUnderTest = new RecipeFunction( null, GoalType.MAXIMIZE );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new RecipeFunction( NutritionalUnit.Calcium, null );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
   }//End Method
   
   @Test public void shouldGenerateNewConstraints(){
      assertThat( systemUnderTest.generate( foods ).get() == systemUnderTest.generate( foods ).get(), is( false ) );
   }//End Method

}//End Class
