package uk.dangrew.nuts.recipe.constraint.raw;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;

public class IngredientRawConstraintTest {

   private Food food1;
   private Food food2;
   private Food food3;
   private List< Food > foods;
   
   private IngredientRawConstraint systemUnderTest;

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
      systemUnderTest = new IngredientRawConstraint( food2, Relationship.GEQ, 2.0 );
   }//End Method

   @Test public void shouldGenerateConstraint() {
      LinearConstraint constraint = systemUnderTest.generate( foods ).get( 0 );
      assertThat( constraint.getCoefficients().getEntry( 0 ), is( 0.0 ) );
      assertThat( constraint.getCoefficients().getEntry( 1 ), is( 1.0 ) );
      assertThat( constraint.getCoefficients().getEntry( 2 ), is( 0.0 ) );
      assertThat( constraint.getRelationship(), is( Relationship.GEQ ) );
      assertThat( constraint.getValue(), is( 2.0 ) );
   }//End Method

   @Test public void shouldIgnoreMissingProperties() {
      systemUnderTest = new IngredientRawConstraint( new FoodItem( "Anything" ), Relationship.EQ, 1.0 );
      assertThat( systemUnderTest.generate( foods ), is( empty() ) );
      
      systemUnderTest = new IngredientRawConstraint( null, Relationship.EQ, 1.0 );
      assertThat( systemUnderTest.generate( foods ), is( empty() ) );
      
      systemUnderTest = new IngredientRawConstraint( food1, null, 1.0 );
      assertThat( systemUnderTest.generate( foods ), is( empty() ) );
      
      systemUnderTest = new IngredientRawConstraint( food1, Relationship.EQ, null );
      assertThat( systemUnderTest.generate( foods ), is( empty() ) );
   }//End Method
   
   @Test public void shouldGenerateNewConstraints(){
      assertThat( systemUnderTest.generate( foods ).get( 0 ) == systemUnderTest.generate( foods ).get( 0 ), is( false ) );
   }//End Method
   
   @Test public void shouldProvideDescription(){
      systemUnderTest = new IngredientRawConstraint();
      assertThat( systemUnderTest.description().get(), is( "No Ingredient, No Relationship, No Bound" ) );
      systemUnderTest.subject().set( food1 );
      assertThat( systemUnderTest.description().get(), is( "Item1, No Relationship, No Bound" ) );
      systemUnderTest.relationship().set( Relationship.GEQ );
      assertThat( systemUnderTest.description().get(), is( "Item1, >=, No Bound" ) );
      systemUnderTest.bound().set( 56.4 );
      assertThat( systemUnderTest.description().get(), is( "Item1, >=, 56.4" ) );
   }//End Method
   
   @Test public void shouldProvideType(){
      assertThat( systemUnderTest.type(), is( ConstraintType.IngredientRaw ) );
   }//End Method
   
}//End Class
