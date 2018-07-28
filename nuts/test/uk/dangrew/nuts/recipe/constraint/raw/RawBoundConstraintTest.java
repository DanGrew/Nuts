package uk.dangrew.nuts.recipe.constraint.raw;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.recipe.constraint.ConstraintType;

public class RawBoundConstraintTest {

   private Map< Food, Double > coefficients;
   
   private String subject;
   private RawBoundConstraint< String > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      subject = "Object";
      coefficients = new LinkedHashMap<>();
      coefficients.put( new FoodItem( "Item1" ), 34.5 );
      coefficients.put( new FoodItem( "Item2" ), 12.2 );
      coefficients.put( new FoodItem( "Item3" ), 0.5 );
      systemUnderTest = new RawBoundConstraint< String >( 
               ConstraintType.Ingredient, 
               subject, 
               Relationship.EQ,
               34.2
      ) {
         
         @Override public String subjectDisplayName() {
            return subject().get().toString();
         }
         
         @Override protected double coefficientFor( Food food ) {
            return coefficients.get( food );
         }//End Method
      };
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.type(), is( ConstraintType.Ingredient ) );
      assertThat( systemUnderTest.relationship().get(), is( Relationship.EQ ) );
      assertThat( systemUnderTest.bound().get(), is( 34.2 ) );
   }//End Method
   
   @Test public void shouldUpdateDescription(){
      assertThat( systemUnderTest.description().get(), is( "Object, =, 34.2" ) );
      systemUnderTest.subject().set( "Other" );
      assertThat( systemUnderTest.description().get(), is( "Other, =, 34.2" ) );
      systemUnderTest.relationship().set( Relationship.GEQ );
      assertThat( systemUnderTest.description().get(), is( "Other, >=, 34.2" ) );
      systemUnderTest.bound().set( 89.0 );
      assertThat( systemUnderTest.description().get(), is( "Other, >=, 89.0" ) );
   }//End Method

   @Test public void shouldNotGenerateWithInsufficientParameters(){
      assertThat( systemUnderTest.unconditionalGenerate( new ArrayList<>( coefficients.keySet() ) ), is( not( empty() ) ) );
      
      systemUnderTest.subject().set( null );
      assertThat( systemUnderTest.unconditionalGenerate( new ArrayList<>( coefficients.keySet() ) ), is( empty() ) );
      
      systemUnderTest.subject().set( subject );
      systemUnderTest.relationship().set( null );
      assertThat( systemUnderTest.unconditionalGenerate( new ArrayList<>( coefficients.keySet() ) ), is( empty() ) );
      
      systemUnderTest.relationship().set( Relationship.EQ );
      systemUnderTest.bound().set( null );
      assertThat( systemUnderTest.unconditionalGenerate( new ArrayList<>( coefficients.keySet() ) ), is( empty() ) );
   }//End Method
   
   @Test public void shouldUseCoefficients(){
      LinearConstraint result = systemUnderTest.unconditionalGenerate( new ArrayList<>( coefficients.keySet() ) ).get( 0 );
      List< Food > foods = new ArrayList<>( coefficients.keySet() );
      for ( int i = 0; i < foods.size(); i++ ) {
         assertThat( result.getCoefficients().getEntry( i ), is( coefficients.get( foods.get( i ) ) ) );
      }
   }//End Method
}//End Class
