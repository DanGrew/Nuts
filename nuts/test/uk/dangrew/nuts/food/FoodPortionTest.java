package uk.dangrew.nuts.food;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.MacroGoalRatioCalculator;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class FoodPortionTest {

   private Food food;
   
   private FoodProperties properties;
   private FoodAnalytics foodAnalytics;
   @Spy private MacroRatioCalculator ratioCalculator;
   private FoodPortion systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      food = new FoodItem( "Anything" );
      properties = new FoodProperties( "Food" );
      foodAnalytics = new FoodAnalytics();
      systemUnderTest = new FoodPortion( 
               properties, 
               foodAnalytics, 
               ratioCalculator 
      );
      systemUnderTest.setFood( food );
   }//End Method

   @Test public void shouldProvideFood() {
      assertThat( systemUnderTest.food().get(), is( food ) );
   }//End Method
   
   @Test public void shouldProvideFoodMacros() {
      systemUnderTest = new FoodPortion();
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionFor( macro ).get(), is( 0.0 ) );
         assertThat( systemUnderTest.nutritionRatioFor( macro ).get(), is( 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldSetNewFood(){
      FoodItem otherFood = new FoodItem( "another" );
      otherFood.properties().setMacros( 4, 6, 2 );
      
      systemUnderTest.setFood( otherFood );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 4.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).get(), is( 6.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).get(), is( 2.0 ) );
      
      otherFood.properties().setMacros( 100, 560, 990 );
      
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 100.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).get(), is( 560.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).get(), is( 990.0 ) );
   }//End Method
   
   @Test public void shouldNotRespondToPreviousFood(){
      food.properties().setMacros( 40, 60, 20 );
      
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 40.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).get(), is( 60.0 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).get(), is( 20.0 ) );
      
      systemUnderTest.setFood( null );
      
      food.properties().setMacros( 100, 560, 990 );
      
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
      food.properties().setMacros( 40, 60, 20 );
      
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
      
      food.properties().setMacros( 40, 60, 20 );
      
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
         assertThat( systemUnderTest.nutritionFor( macro ).get(), is( food.properties().nutritionFor( macro ).get() ) );
      }
      
      food.properties().setMacros( 40, 60, 20 );
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionRatioFor( macro ).get(), is( food.foodAnalytics().nutrientRatioFor( macro ).get() ) );
      }
   }//End Method
   
   @Test public void shouldProvideRatiosForNewFood(){
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionFor( macro ).get(), is( food.properties().nutritionFor( macro ).get() ) );
      }
      
      FoodItem otherFood = new FoodItem( "another" );
      otherFood.properties().setMacros( 40, 60, 20 );
      systemUnderTest.setFood( otherFood );
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionRatioFor( macro ).get(), is( otherFood.foodAnalytics().nutrientRatioFor( macro ).get() ) );
      }
      
      food.properties().setMacros( 3476, 90, 234 );
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionRatioFor( macro ).get(), is( otherFood.foodAnalytics().nutrientRatioFor( macro ).get() ) );
      }
   }//End Method
   
   @Test public void shouldResetRatiosWithNewFood(){
      food.properties().carbohydrates().set( 56.0 );
      food.properties().fats().set( 56.0 );
      food.properties().protein().set( 56.0 );
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionFor( macro ).get(), is( food.properties().nutritionFor( macro ).get() ) );
      }
      
      systemUnderTest.setFood( null );
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         assertThat( systemUnderTest.nutritionRatioFor( macro ).get(), is( 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldConstructWithInitialFoodAndPortion(){
      systemUnderTest = new FoodPortion( food, 67 );
      assertThat( systemUnderTest.food().get(), is( food ) );
      assertThat( systemUnderTest.portion().get(), is( 67.0 ) );
   }//End Method
   
   @Test public void shouldAssociateRatioCalculator(){
      verify( ratioCalculator ).associate( properties, foodAnalytics );
   }//End Method
   
   @Test public void shouldProvideProperties(){
      assertThat( systemUnderTest.properties(), is( properties ) );
   }//End Method
   
   @Test public void shouldProvideFoodAnalytics(){
      assertThat( systemUnderTest.foodAnalytics(), is( foodAnalytics ) );
   }//End Method
   
   @Test public void shouldUpdateCaloriesWhenPortionChanges(){
      assertThat( systemUnderTest.properties().calories().get(), is( 0.0 ) );
      food.properties().calories().set( 250.0 );
      assertThat( systemUnderTest.properties().calories().get(), is( 250.0 ) );
      systemUnderTest.setPortion( 50 );
      assertThat( systemUnderTest.properties().calories().get(), is( 125.0 ) );
   }//End Method
   
   @Test public void shouldIgnorePreviousFoodCalories(){
      systemUnderTest.setPortion( 50 );
      food.properties().calories().set( 250.0 );
      assertThat( systemUnderTest.properties().calories().get(), is( 125.0 ) );
      
      systemUnderTest.setFood( new FoodItem( "anything" ) );
      assertThat( systemUnderTest.properties().calories().get(), is( 0.0 ) );
      food.properties().calories().set( 250.0 );
      assertThat( systemUnderTest.properties().calories().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldUpdateCaloriesWhenCaloriesChanges(){
      assertThat( systemUnderTest.properties().calories().get(), is( 0.0 ) );
      systemUnderTest.setPortion( 50 );
      assertThat( systemUnderTest.properties().calories().get(), is( 0.0 ) );
      food.properties().calories().set( 250.0 );
      assertThat( systemUnderTest.properties().calories().get(), is( 125.0 ) );
   }//End Method
   
}//End Class
