package uk.dangrew.nuts.recipe.constraint.tightbound;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;
import uk.dangrew.nuts.recipe.constraint.raw.NutritionalUnitRawConstraint;

public class TightBoundConstraintTest {

   private Food food1;
   private Food food2;
   private List< Food > foods;
   private TightBoundConstraint< NutritionalUnit > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      food1 = DataLocation.randomizedFood();
      food2 = DataLocation.randomizedFood();
      foods = Arrays.asList( food1, food2 );
      
      systemUnderTest = new TightBoundConstraint<>( 
               ConstraintType.NutritionalUnit,
               NutritionalUnitRawConstraint::new
      );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.subject(), is( notNullValue() ) );
      assertThat( systemUnderTest.lowerBound(), is( notNullValue() ) );
      assertThat( systemUnderTest.upperBound(), is( notNullValue() ) );
      assertThat( systemUnderTest.lowerBound(), is( not( systemUnderTest.upperBound() ) ) );
      assertThat( systemUnderTest.type(), is( ConstraintType.NutritionalUnit ) );
   }//End Method
   
   @Test public void shouldGenerateConstraintsForBounds(){
      systemUnderTest.subject().set( NutritionalUnit.Fat );
      systemUnderTest.lowerBound().set( 20.0 );
      systemUnderTest.upperBound().set( 35.0 );
      
      List< LinearConstraint > constraints = systemUnderTest.generate( foods );
      for ( LinearConstraint constraint : constraints ) {
         for ( int i = 0; i < foods.size(); i++ ) {
            assertThat( constraint.getCoefficients().getEntry( i ), is( foods.get( i ).nutrition().of( NutritionalUnit.Fat ).get() ) );
         }
      }
      assertThat( constraints.get( 0 ).getRelationship(), is( Relationship.GEQ ) );
      assertThat( constraints.get( 1 ).getRelationship(), is( Relationship.LEQ ) );
      assertThat( constraints.get( 0 ).getValue(), is( 20.0 ) );
      assertThat( constraints.get( 1 ).getValue(), is( 35.0 ) );
   }//End Method
   
   @Test public void shouldNotGenerateIfInvalid(){
      systemUnderTest.subject().set( NutritionalUnit.Fat );
      systemUnderTest.lowerBound().set( 20.0 );
      systemUnderTest.upperBound().set( 35.0 );
      assertThat( systemUnderTest.generate( foods ), hasSize( 2 ) );
      
      systemUnderTest.lowerBound().set( null );
      List< LinearConstraint > constraints = systemUnderTest.generate( foods );
      assertThat( constraints, hasSize( 1 ) );
      assertThat( constraints.get( 0 ).getRelationship(), is( Relationship.LEQ ) );
      
      systemUnderTest.upperBound().set( null );
      assertThat( systemUnderTest.generate( foods ), is( empty() ) );
      
      systemUnderTest.subject().set( NutritionalUnit.Fat );
      systemUnderTest.lowerBound().set( 20.0 );
      systemUnderTest.upperBound().set( 35.0 );
      assertThat( systemUnderTest.generate( foods ), hasSize( 2 ) );
      
      systemUnderTest.upperBound().set( null );
      constraints = systemUnderTest.generate( foods );
      assertThat( constraints, hasSize( 1 ) );
      assertThat( constraints.get( 0 ).getRelationship(), is( Relationship.GEQ ) );
   }//End Method
   
}//End Class
