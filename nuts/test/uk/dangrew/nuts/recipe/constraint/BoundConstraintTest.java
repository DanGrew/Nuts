package uk.dangrew.nuts.recipe.constraint;

import static org.hamcrest.CoreMatchers.not;
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

public class BoundConstraintTest {

   private Food food1;
   private Food food2;
   private Food food3;
   private List< Food > foods;
   
   private BoundConstraint systemUnderTest;

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
      systemUnderTest = new BoundConstraint( NutritionalUnit.Fat, Relationship.GEQ, 25.0 );
   }//End Method

   @Test public void shouldGenerateConstraint() {
      LinearConstraint constraint = systemUnderTest.generate( foods ).get();
      for ( int i = 0; i < foods.size(); i++ ) {
         assertThat( constraint.getCoefficients().getEntry( i ), is( foods.get( i ).nutrition().of( NutritionalUnit.Fat ).get() ) );
      }
      assertThat( constraint.getRelationship(), is( Relationship.GEQ ) );
      assertThat( constraint.getValue(), is( 25.0 ) );
   }//End Method
   
   @Test public void shouldGenerateNewConstraints(){
      assertThat( systemUnderTest.generate( foods ) == systemUnderTest.generate( foods ), is( false ) );
   }//End Method
   
   @Test public void shouldIgnoreMissingProperties() {
      systemUnderTest = new BoundConstraint( null, Relationship.EQ, 1.0 );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new BoundConstraint( NutritionalUnit.Calcium, null, 1.0 );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
      
      systemUnderTest = new BoundConstraint( NutritionalUnit.Calcium, Relationship.EQ, null );
      assertThat( systemUnderTest.generate( foods ).isPresent(), is( false ) );
   }//End Method
   
   @Test public void shouldProvideDescription(){
      systemUnderTest = new BoundConstraint();
      assertThat( systemUnderTest.description().get(), is( "No Unit, No Relationship, No Bound" ) );
      systemUnderTest.unit().set( NutritionalUnit.Calories );
      assertThat( systemUnderTest.description().get(), is( "Calories, No Relationship, No Bound" ) );
      systemUnderTest.relationship().set( Relationship.GEQ );
      assertThat( systemUnderTest.description().get(), is( "Calories, >=, No Bound" ) );
      systemUnderTest.bound().set( 56.4 );
      assertThat( systemUnderTest.description().get(), is( "Calories, >=, 56.4" ) );
   }//End Method
   
   @Test public void shouldProvideType(){
      assertThat( systemUnderTest.type(), is( ConstraintType.Bound ) );
   }//End Method

}//End Class
