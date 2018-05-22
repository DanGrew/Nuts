package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.model.ModelVerifier;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class FoodAnalyticsTest {

   private FoodAnalytics systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new FoodAnalytics();
   }//End Method

   @Test public void shouldProvideMacros(){
      systemUnderTest.carbohydratesRatioProperty().set( 101.0 );
      assertThat( systemUnderTest.carbohydratesRatio(), is( 101.0 ) );
      assertThat( systemUnderTest.nutrientRatioFor( MacroNutrient.Carbohydrates ).get(), is( 101.0 ) );
      
      systemUnderTest.fatsRatioProperty().set( 45.0 );
      assertThat( systemUnderTest.fatsRatio(), is( 45.0 ) );
      assertThat( systemUnderTest.nutrientRatioFor( MacroNutrient.Fats ).get(), is( 45.0 ) );
      
      systemUnderTest.proteinRatioProperty().set( 54.0 );
      assertThat( systemUnderTest.proteinRatio(), is( 54.0 ) );
      assertThat( systemUnderTest.nutrientRatioFor( MacroNutrient.Protein ).get(), is( 54.0 ) );
   }//End Method
   
   @Test public void shouldProvideCalorieDensity(){
      systemUnderTest.calorieDensityProperty().set( 101.0 );
      assertThat( systemUnderTest.calorieDensity(), is( 101.0 ) );
      assertThat( systemUnderTest.calorieDensityProperty().get(), is( 101.0 ) );
   }//End Method
   
   @Test public void shouldProvideProperties(){
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideDoubleProperty( FoodAnalytics::fiberRatioProperty, 0.0 );
   }//End Method

}//End Class
