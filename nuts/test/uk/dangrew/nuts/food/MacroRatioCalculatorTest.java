package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.nutrients.MacroNutrient;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class MacroRatioCalculatorTest {

   private FoodProperties properties;
   private FoodAnalytics analytics;
   private MacroRatioCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      properties = new FoodProperties( "Props" );
      analytics = new FoodAnalytics();
      systemUnderTest = new MacroRatioCalculator();
      systemUnderTest.associate( properties, analytics );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowAssociateAgain(){
      systemUnderTest.associate( properties, analytics );
   }//End Method
   
   @Test public void shouldCalculateProportionsOnAssociation(){
      properties.nutrition().of( NutritionalUnit.Carbohydrate ).set( 45.0 );
      properties.nutrition().of( NutritionalUnit.Fat ).set( 15.0 );
      properties.nutrition().of( NutritionalUnit.Protein ).set( 40.0 );
      properties.fiber().set( 1.0 );
      
      systemUnderTest = new MacroRatioCalculator();
      systemUnderTest.associate( properties, analytics );
      assertPropertyProportions( 45, 15, 40, 1 );
   }//End Method
   
   @Test public void shouldProvideMacroProportions() {
      assertPropertyProportions( 0, 0, 0, 0 );
      
      properties.nutritionFor( MacroNutrient.Carbohydrates ).set( 10.0 );
      assertPropertyProportions( 100, 0, 0, 0 );
      
      properties.nutritionFor( MacroNutrient.Fats ).set( 15.0 );
      assertPropertyProportions( 40, 60, 0, 0 );
      
      properties.nutritionFor( MacroNutrient.Protein ).set( 75.0 );
      assertPropertyProportions( 10, 15, 75, 0 );
      
      properties.fiber().set( 1.75 );
      assertPropertyProportions( 10, 15, 75, 1.75 );
      
      properties.nutritionFor( MacroNutrient.Fats ).set( 115.0 );
      assertPropertyProportions( 5, 57.5, 37.5, 0.875 );
   }//End Method
   
   private void assertPropertyProportions( double c, double f, double p, double i ) {
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Carbohydrates ).get(), is( c ) );
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Fats ).get(), is( f ) );
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Protein ).get(), is( p ) );
      
      assertThat( analytics.carbohydratesRatioProperty().get(), is( c ) );
      assertThat( analytics.fatsRatioProperty().get(), is( f ) );
      assertThat( analytics.proteinRatioProperty().get(), is( p ) );
      assertThat( analytics.fiberRatioProperty().get(), is( i ) );
      
      assertThat( analytics.carbohydratesRatio(), is( c ) );
      assertThat( analytics.fatsRatio(), is( f ) );
      assertThat( analytics.proteinRatio(), is( p ) );  
   }//End Method
   
}//End Class
