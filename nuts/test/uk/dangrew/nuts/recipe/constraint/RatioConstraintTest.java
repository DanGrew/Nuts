package uk.dangrew.nuts.recipe.constraint;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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

public class RatioConstraintTest {

   private Food food1;
   private Food food2;
   private Food food3;
   private List< Food > foods;
   
   private RatioConstraint systemUnderTest;

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
      systemUnderTest = new RatioConstraint( 
               food1, 2.0, food3, 3.0, Relationship.EQ 
      );
   }//End Method

   @Test public void shouldGenerateConstraint() {
      LinearConstraint constraint = systemUnderTest.generate( foods ).get();
      assertThat( constraint.getCoefficients().getEntry( 0 ), is( 3.0 ) );
      assertThat( constraint.getCoefficients().getEntry( 1 ), is( 0.0 ) );
      assertThat( constraint.getCoefficients().getEntry( 2 ), is( -2.0 ) );
      assertThat( constraint.getRelationship(), is( Relationship.EQ ) );
      assertThat( constraint.getValue(), is( 0.0 ) );
   }//End Method

   @Test public void shouldIgnoreMissingFood() {
      systemUnderTest = new RatioConstraint( 
               new FoodItem( "Anything" ), 3.0, food3, 2.0, Relationship.EQ 
      );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new RatioConstraint( 
               food1, 3.0, new FoodItem( "Anything" ), 2.0, Relationship.EQ 
      );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new RatioConstraint( 
               null, 3.0, food2, 2.0, Relationship.EQ 
      );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new RatioConstraint( 
               food1, null, food2, 2.0, Relationship.EQ 
      );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new RatioConstraint( 
               food1, 3.0, null, 2.0, Relationship.EQ 
      );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new RatioConstraint( 
               food1, 3.0, food2, null, Relationship.EQ 
      );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new RatioConstraint( 
               food1, 3.0, food2, 2.0, null 
      );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
   }//End Method
   
   @Test public void shouldGenerateNewConstratins(){
      assertThat( systemUnderTest.generate( foods ).get() == systemUnderTest.generate( foods ).get(), is( false ) );
   }//End Method
   
   @Test public void shouldProvideDescription(){
      systemUnderTest = new RatioConstraint();
      assertThat( systemUnderTest.description().get(), is( "No First Ingredient, No First Amount, No Second Ingredient, No Second Amount, No Relationship" ) );
      systemUnderTest.firstIngredient().set( food1 );
      assertThat( systemUnderTest.description().get(), is( "Item1, No First Amount, No Second Ingredient, No Second Amount, No Relationship" ) );
      systemUnderTest.howMuchOfFirst().set( 56.4 );
      assertThat( systemUnderTest.description().get(), is( "Item1, 56.4, No Second Ingredient, No Second Amount, No Relationship" ) );
      systemUnderTest.secondIngredient().set( food2 );
      assertThat( systemUnderTest.description().get(), is( "Item1, 56.4, Item2, No Second Amount, No Relationship" ) );
      systemUnderTest.howMuchOfSecond().set( 56.4 );
      assertThat( systemUnderTest.description().get(), is( "Item1, 56.4, Item2, 56.4, No Relationship" ) );
      systemUnderTest.relationship().set( Relationship.GEQ );
      assertThat( systemUnderTest.description().get(), is( "Item1, 56.4, Item2, 56.4, >=" ) );
   }//End Method
   
   @Test public void shouldProvideType(){
      assertThat( systemUnderTest.type(), is( ConstraintType.Ratio ) );
   }//End Method

}//End Class
