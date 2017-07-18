package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.measurement.NutrientMeasurement;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class FoodAnalyticsTest {

   private Food food;
   private FoodAnalytics systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new FoodAnalytics();
      
      food = new Food( "anything", "Something", systemUnderTest );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowAssociateAgain(){
      systemUnderTest.associate( food );
   }//End Method
   
   @Test public void shouldCalculateProportionsOnAssociation(){
      food.carbohydrates().setGrams( 45 );
      food.fats().setGrams( 15 );
      food.protein().setGrams( 40 );
      
      systemUnderTest = new FoodAnalytics();
      systemUnderTest.associate( food );
      assertMacroProportions( 45, 15, 40 );
   }//End Method
   
   @Test public void shouldProvideMacroProportions() {
      assertMacroProportions( 0, 0, 0 );
      
      food.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 10 );
      assertMacroProportions( 100, 0, 0 );
      
      food.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 15 );
      assertMacroProportions( 40, 60, 0 );
      
      food.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 75 );
      assertMacroProportions( 10, 15, 75 );
      
      food.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 115 );
      assertMacroProportions( 5, 57.5, 37.5 );
   }//End Method
   
   /**
    * Convenience method to assert that the {@link FoodAnalytics} reports the correct {@link MacroNutrient}s.
    * @param c the {@link MacroNutrient#Carbohydrates}.
    * @param f the {@link MacroNutrient#Fats}.
    * @param p the {@link MacroNutrient#Protein}.
    */
   private void assertMacroProportions( double c, double f, double p ) {
      assertThat( systemUnderTest.nutrientRatioFor( MacroNutrient.Carbohydrates ).get(), is( c ) );
      assertThat( systemUnderTest.nutrientRatioFor( MacroNutrient.Fats ).get(), is( f ) );
      assertThat( systemUnderTest.nutrientRatioFor( MacroNutrient.Protein ).get(), is( p ) );
      
      assertThat( systemUnderTest.carbohydratesRatio().get(), is( c ) );
      assertThat( systemUnderTest.fatsRatio().get(), is( f ) );
      assertThat( systemUnderTest.proteinRatio().get(), is( p ) );      
   }//End Method

}//End Class
