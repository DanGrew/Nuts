package uk.dangrew.nuts.recipe.constraint;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Observer;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class IngredientConstraintTest {

   private Food food1;
   private Food food2;
   private Food food3;
   private List< Food > foods;
   
   private IngredientConstraint systemUnderTest;

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
      systemUnderTest = new IngredientConstraint( food2, Relationship.GEQ, 2.0 );
   }//End Method

   @Test public void shouldGenerateConstraint() {
      LinearConstraint constraint = systemUnderTest.generate( foods ).get();
      assertThat( constraint.getCoefficients().getEntry( 0 ), is( 0.0 ) );
      assertThat( constraint.getCoefficients().getEntry( 1 ), is( 1.0 ) );
      assertThat( constraint.getCoefficients().getEntry( 2 ), is( 0.0 ) );
      assertThat( constraint.getRelationship(), is( Relationship.GEQ ) );
      assertThat( constraint.getValue(), is( 2.0 ) );
   }//End Method

   @Test public void shouldIgnoreMissingProperties() {
      systemUnderTest = new IngredientConstraint( new FoodItem( "Anything" ), Relationship.EQ, 1.0 );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new IngredientConstraint( null, Relationship.EQ, 1.0 );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new IngredientConstraint( food1, null, 1.0 );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new IngredientConstraint( food1, Relationship.EQ, null );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
   }//End Method
   
   @Test public void shouldGenerateNewConstraints(){
      assertThat( systemUnderTest.generate( foods ).get() == systemUnderTest.generate( foods ).get(), is( false ) );
   }//End Method
   
   @Test public void shouldProvideDescription(){
      systemUnderTest = new IngredientConstraint();
      assertThat( systemUnderTest.description().get(), is( "No Ingredient, No Relationship, No Amount" ) );
      systemUnderTest.ingredient().set( food1 );
      assertThat( systemUnderTest.description().get(), is( "Item1, No Relationship, No Amount" ) );
      systemUnderTest.relationship().set( Relationship.GEQ );
      assertThat( systemUnderTest.description().get(), is( "Item1, >=, No Amount" ) );
      systemUnderTest.amountOfIngredient().set( 56.4 );
      assertThat( systemUnderTest.description().get(), is( "Item1, >=, 56.4" ) );
   }//End Method
   
   @Test public void shouldProvideType(){
      assertThat( systemUnderTest.type(), is( ConstraintType.Ingredient ) );
   }//End Method
   
}//End Class
