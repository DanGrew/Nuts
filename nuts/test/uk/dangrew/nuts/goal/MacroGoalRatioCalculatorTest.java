package uk.dangrew.nuts.goal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.measurement.NutrientMeasurement;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class MacroGoalRatioCalculatorTest {

   private Goal goal;
   private FoodProperties properties;
   private FoodAnalytics analytics;
   private MacroGoalRatioCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      properties = new FoodProperties( "Props" );
      analytics = new FoodAnalytics();
      goal = new Goal( "Goal" );
      
      systemUnderTest = new MacroGoalRatioCalculator();
      systemUnderTest.associate( properties, analytics, goal );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowAssociateAgain(){
      systemUnderTest.associate( properties, analytics, goal );
   }//End Method
   
   @Test public void shouldCalculateProportionsOnAssociation(){
      goal.properties().setMacros( 300, 60, 200 );
      
      properties.carbohydrates().setGrams( 45 );
      properties.fats().setGrams( 15 );
      properties.protein().setGrams( 40 );
      
      systemUnderTest = new MacroGoalRatioCalculator();
      systemUnderTest.associate( properties, analytics, goal );
      assertMacroProportions( 15, 25, 20 );
   }//End Method
   
   @Test public void shouldProvideMacroProportions() {
      assertMacroProportions( 0, 0, 0 );
      goal.properties().setMacros( 300, 60, 200 );
      
      properties.carbohydrates().setValue( NutrientMeasurement.Grams, 30 );
      assertMacroProportions( 10, 0, 0 );
      
      properties.fats().setValue( NutrientMeasurement.Grams, 30 );
      assertMacroProportions( 10, 50, 0 );
      
      properties.protein().setValue( NutrientMeasurement.Grams, 75 );
      assertMacroProportions( 10, 50, 37.5 );
      
      properties.fats().setValue( NutrientMeasurement.Grams, 180 );
      assertMacroProportions( 10, 300, 37.5 );
      
      goal.properties().setMacros( 0.0, 0.0, 0.0 );
      assertMacroProportions( 0, 0, 0 );
   }//End Method
   
   @Test public void shouldRespondToGoalChanges() {
      properties.carbohydrates().setGrams( 20 );
      properties.fats().setGrams( 15 );
      properties.protein().setGrams( 10 );
      
      goal.properties().carbohydrates().setGrams( 50 );
      assertMacroProportions( 40, 0, 0 );
      
      goal.properties().fats().setGrams( 150 );
      assertMacroProportions( 40, 10, 0 );
      
      goal.properties().protein().setGrams( 80 );
      assertMacroProportions( 40, 10, 12.5 );
      
      goal.properties().fats().setGrams( 20 );
      assertMacroProportions( 40, 75, 12.5 );
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
