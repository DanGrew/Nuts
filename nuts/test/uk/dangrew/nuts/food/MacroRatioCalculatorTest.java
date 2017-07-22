package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.measurement.NutrientMeasurement;
import uk.dangrew.nuts.nutrients.MacroNutrient;

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
      properties.carbohydrates().setGrams( 45 );
      properties.fats().setGrams( 15 );
      properties.protein().setGrams( 40 );
      
      systemUnderTest = new MacroRatioCalculator();
      systemUnderTest.associate( properties, analytics );
      assertMacroProportions( 45, 15, 40 );
   }//End Method
   
   @Test public void shouldProvideMacroProportions() {
      assertMacroProportions( 0, 0, 0 );
      
      properties.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 10 );
      assertMacroProportions( 100, 0, 0 );
      
      properties.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 15 );
      assertMacroProportions( 40, 60, 0 );
      
      properties.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 75 );
      assertMacroProportions( 10, 15, 75 );
      
      properties.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 115 );
      assertMacroProportions( 5, 57.5, 37.5 );
   }//End Method
   
   /**
    * Convenience method to assert that the {@link FoodAnalytics} reports the correct {@link MacroNutrient}s.
    * @param c the {@link MacroNutrient#Carbohydrates}.
    * @param f the {@link MacroNutrient#Fats}.
    * @param p the {@link MacroNutrient#Protein}.
    */
   private void assertMacroProportions( double c, double f, double p ) {
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Carbohydrates ).get(), is( c ) );
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Fats ).get(), is( f ) );
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Protein ).get(), is( p ) );
      
      assertThat( analytics.carbohydratesRatioProperty().get(), is( c ) );
      assertThat( analytics.fatsRatioProperty().get(), is( f ) );
      assertThat( analytics.proteinRatioProperty().get(), is( p ) );
      
      assertThat( analytics.carbohydratesRatio(), is( c ) );
      assertThat( analytics.fatsRatio(), is( f ) );
      assertThat( analytics.proteinRatio(), is( p ) );  
   }//End Method

}//End Class
