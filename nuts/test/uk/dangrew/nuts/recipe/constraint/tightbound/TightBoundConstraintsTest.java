package uk.dangrew.nuts.recipe.constraint.tightbound;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;

public class TightBoundConstraintsTest {

   private Food food1;
   private Food food2;
   private List< Food > foods;
   private TightBoundConstraints< NutritionalUnit > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      food1 = DataLocation.randomizedFood();
      food2 = DataLocation.randomizedFood();
      foods = Arrays.asList( food1, food2 );
      systemUnderTest = new TightBoundConstraints<>( 
               ConstraintType.NutritionalUnitBounds,
               NutritionalUnitConstraint::new
      );
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         systemUnderTest.putConstraintFor( unit );
      }
   }//End Method

   @Test public void shouldProvideBoundConstraintForEachUnit() {
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.constraintFor( unit ), is( notNullValue() ) );
         assertThat( systemUnderTest.constraintFor( unit ).subject().get(), is( unit ) );
      }
   }//End Method
   
   @Test public void shouldProvideType() {
      assertThat( systemUnderTest.type(), is( ConstraintType.NutritionalUnitBounds ) );
   }//End Method 
   
   @Test public void shouldGenerateConstraintForEachUnitForEachFood(){
      for ( int i = 0; i < NutritionalUnit.values().length; i++ ) {
         systemUnderTest.constraintFor( NutritionalUnit.values()[ i ] ).lowerBound().set( 0.0 + i );
         systemUnderTest.constraintFor( NutritionalUnit.values()[ i ] ).upperBound().set( 1.0 + i );
      }
      
      List< LinearConstraint > constraints = systemUnderTest.generate( foods );
      assertThat( constraints, hasSize( NutritionalUnit.values().length * 2 ) );
      
      for ( int i = 0; i < NutritionalUnit.values().length; i++ ) {
         LinearConstraint lower = constraints.get( i * 2 );
         LinearConstraint upper = constraints.get( i * 2 + 1 );
         for ( int j = 0; j < foods.size(); j++ ) {
            assertThat( 
                     lower.getCoefficients().getEntry( j ), 
                     is( foods.get( j ).nutrition().of( NutritionalUnit.values()[ i ] ).get() ) 
            );
            assertThat( 
                     upper.getCoefficients().getEntry( j ), 
                     is( foods.get( j ).nutrition().of( NutritionalUnit.values()[ i ] ).get() ) 
            );
         }
         assertThat( lower.getValue(), is( 0.0 + i ) );
         assertThat( upper.getValue(), is( 1.0 + i ) );
      }
   }//End Method
   
   @Test public void shouldGenerateConstraintOnlyForValidBounds(){
      systemUnderTest.constraintFor( NutritionalUnit.Fat ).lowerBound().set( 78.0 );
      systemUnderTest.constraintFor( NutritionalUnit.Fat ).upperBound().set( 79.0 );
      
      List< LinearConstraint > constraints = systemUnderTest.generate( foods );
      assertThat( constraints, hasSize( 2 ) );
      
      LinearConstraint lower = constraints.get( 0 );
      LinearConstraint upper = constraints.get( 1 );
      for ( int j = 0; j < foods.size(); j++ ) {
         assertThat( 
                  lower.getCoefficients().getEntry( j ), 
                  is( foods.get( j ).nutrition().of( NutritionalUnit.Fat ).get() ) 
         );
         assertThat( 
                  upper.getCoefficients().getEntry( j ), 
                  is( foods.get( j ).nutrition().of( NutritionalUnit.Fat ).get() ) 
         );
      }
      assertThat( lower.getValue(), is( 78.0 ) );
      assertThat( upper.getValue(), is( 79.0 ) );
   }//End Method
   
   @Test public void shouldProvideDescription(){
      assertThat( systemUnderTest.description().get(), is( nullValue() ) );
   }//End Method
   
}//End Class
