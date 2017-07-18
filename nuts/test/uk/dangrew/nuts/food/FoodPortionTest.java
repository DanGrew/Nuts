package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.measurement.NutrientMeasurement;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class FoodPortionTest {

   private Food food;
   private FoodPortion systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food = new Food( "Anything" );
      systemUnderTest = new FoodPortion();
      systemUnderTest.setFood( food );
   }//End Method

   @Test public void shouldProvideFood() {
      assertThat( systemUnderTest.food().get(), is( food ) );
   }//End Method
   
   @Test public void shouldSetNewFood(){
      Food otherFood = new Food( "another" );
      otherFood.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 4 );
      otherFood.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 6 );
      otherFood.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 2 );
      
      systemUnderTest.setFood( otherFood );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 4.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).get(), is( 6.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).get(), is( 2.0 ) );
      
      otherFood.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 100 );
      otherFood.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 560 );
      otherFood.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 990 );
      
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 100.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).get(), is( 560.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).get(), is( 990.0 ) );
   }//End Method
   
   @Test public void shouldNotRespondToPreviousFood(){
      food.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 40 );
      food.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 60 );
      food.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 20 );
      
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 40.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).get(), is( 60.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).get(), is( 20.0 ) );
      
      systemUnderTest.setFood( null );
      
      food.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 100 );
      food.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 560 );
      food.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 990 );
      
      assertThat( systemUnderTest.nutritionRatioFor( MacroNutrient.Carbohydrates ).get(), is( 0.0 ) );
      assertThat( systemUnderTest.nutritionRatioFor( MacroNutrient.Fats ).get(), is( 0.0 ) );
      assertThat( systemUnderTest.nutritionRatioFor( MacroNutrient.Protein ).get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldSetNoFood(){
      systemUnderTest.setFood( null );
      systemUnderTest.setFood( food );
      systemUnderTest.setFood( null );
   }//End Method
   
   @Test public void shouldProvidePortionedMacros() {
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionFor( macro ), is( notNullValue() ) );
      }
   }//End Method
   
   @Test public void shouldUpdateMacrosWithPortion() {
      food.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 40 );
      food.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 60 );
      food.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 20 );
      
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 40.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).get(), is( 60.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).get(), is( 20.0 ) );
      
      systemUnderTest.setPortion( 200 );
      
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 80.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).get(), is( 120.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).get(), is( 40.0 ) );
   }//End Method
   
   @Test public void shouldUpdateMacrosWhenFoodChanges() {
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 0.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).get(), is( 0.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).get(), is( 0.0 ) );
      
      food.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 40 );
      food.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 60 );
      food.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 20 );
      
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 40.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).get(), is( 60.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).get(), is( 20.0 ) );
   }//End Method
   
   @Test public void shouldProvidePortion() {
      assertThat( systemUnderTest.portion().get(), is( 100.0 ) );
   }//End Method
   
   @Test public void shouldNotAllowPortionBelowZero(){
      systemUnderTest.setPortion( -10 );
      assertThat( systemUnderTest.portion().get(), is( 0.0 ) );
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionRatioFor( macro ).get(), is( 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldAllowPortionZero(){
      systemUnderTest.setPortion( 0 );
      assertThat( systemUnderTest.portion().get(), is( 0.0 ) );
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionRatioFor( macro ).get(), is( 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldProvideRatios(){
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionFor( macro ).get(), is( food.nutritionFor( macro ).inGrams() ) );
      }
      
      food.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 40 );
      food.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 60 );
      food.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 20 );
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionRatioFor( macro ).get(), is( food.analytics().nutrientRatioFor( macro ).get() ) );
      }
   }//End Method
   
   @Test public void shouldProvideRatiosForNewFood(){
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionFor( macro ).get(), is( food.nutritionFor( macro ).inGrams() ) );
      }
      
      Food otherFood = new Food( "another" );
      otherFood.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 40 );
      otherFood.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 60 );
      otherFood.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 20 );
      systemUnderTest.setFood( otherFood );
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionRatioFor( macro ).get(), is( otherFood.analytics().nutrientRatioFor( macro ).get() ) );
      }
      
      food.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 3476 );
      food.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 90 );
      food.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 234 );
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionRatioFor( macro ).get(), is( otherFood.analytics().nutrientRatioFor( macro ).get() ) );
      }
   }//End Method
   
   @Test public void shouldResetRatiosWithNewFood(){
      food.carbohydrates().setGrams( 56 );
      food.fats().setGrams( 56 );
      food.protein().setGrams( 56 );
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionFor( macro ).get(), is( food.nutritionFor( macro ).inGrams() ) );
      }
      
      systemUnderTest.setFood( null );
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionRatioFor( macro ).get(), is( 0.0 ) );
      }
   }//End Method
   
}//End Class
