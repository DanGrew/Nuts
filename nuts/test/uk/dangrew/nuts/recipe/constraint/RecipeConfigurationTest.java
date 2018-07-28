package uk.dangrew.nuts.recipe.constraint;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.Relationship;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.recipe.constraint.raw.NutritionalUnitRawConstraint;

public class RecipeConfigurationTest {

   private RecipeConfiguration systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new RecipeConfiguration();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideObject( RecipeConfiguration::properties )
         .shouldProvideCollection( RecipeConfiguration::ingredients, new FoodItem( "Anything" ) )
         .shouldProvideProperty( RecipeConfiguration::function, systemUnderTest.function().get(), new RecipeFunction() )
         .shouldProvideCollection( RecipeConfiguration::constraints, new NutritionalUnitRawConstraint() );
   }//End Method
   
   @Test public void shouldGenerateConstraints(){
      FoodItem item = new FoodItem( "Anything" );
      item.nutrition().of( NutritionalUnit.Calcium ).set( 43.0 );
      systemUnderTest.ingredients().add( item );
      
      NutritionalUnitRawConstraint invalid = new NutritionalUnitRawConstraint();
      NutritionalUnitRawConstraint valid = new NutritionalUnitRawConstraint( NutritionalUnit.Calcium, Relationship.EQ, 25.0 );
      
      systemUnderTest.constraints().add( invalid );
      systemUnderTest.constraints().add( valid );
      
      LinearConstraintSet set = systemUnderTest.generateConstraints();
      assertThat( set.getConstraints(), hasSize( 2 ) );
      
      Iterator< LinearConstraint > constraints = set.getConstraints().iterator();
      LinearConstraint bound = constraints.next();
      assertThat( bound.getRelationship(), is( Relationship.EQ ) );
      assertThat( bound.getValue(), is( 25.0 ) );
      assertThat( bound.getCoefficients().getEntry( 0 ), is( 43.0 ) );
      
      LinearConstraint minimum = constraints.next();
      assertThat( minimum.getRelationship(), is( Relationship.GEQ ) );
      assertThat( minimum.getValue(), is( 0.0 ) );
      assertThat( minimum.getCoefficients().getEntry( 0 ), is( 1.0 ) );
   }//End Method

}//End Class
