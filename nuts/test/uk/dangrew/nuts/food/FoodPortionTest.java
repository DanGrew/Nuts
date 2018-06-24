package uk.dangrew.nuts.food;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.system.Properties;

public class FoodPortionTest {

   private Food food;
   
   private Properties properties;
   private Nutrition nutrition;
   private FoodAnalytics foodAnalytics;
   @Spy private MacroRatioCalculator ratioCalculator;
   private FoodPortion systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      food = new FoodItem( "Anything" );
      properties = new Properties( "Food" );
      nutrition = new Nutrition();
      foodAnalytics = new FoodAnalytics();
      systemUnderTest = new FoodPortion( 
               properties, 
               nutrition,
               foodAnalytics, 
               ratioCalculator 
      );
      systemUnderTest.setFood( food );
   }//End Method
   
   @Test public void shouldProvideNutrition(){
      assertThat( systemUnderTest.properties(), is( properties ) );
      assertThat( systemUnderTest.nutrition(), is( nutrition ) );
   }//End Method

   @Test public void shouldProvideFood() {
      assertThat( systemUnderTest.food().get(), is( food ) );
   }//End Method
   
   @Test public void shouldProvideFoodMacros() {
      systemUnderTest = new FoodPortion();
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.nutrition().of( unit ).get(), is( 0.0 ) );
         assertThat( systemUnderTest.foodAnalytics().nutrition().of( unit ).get(), is( 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldSetNewFood(){
      FoodItem otherFood = new FoodItem( "another" );
      otherFood.nutrition().setMacroNutrients( 4, 6, 2 );
      
      systemUnderTest.setFood( otherFood );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 4.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get(), is( 6.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get(), is( 2.0 ) );
      
      otherFood.nutrition().setMacroNutrients( 100, 560, 990 );
      
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 100.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get(), is( 560.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get(), is( 990.0 ) );
   }//End Method
   
   @Test public void shouldNotRespondToPreviousFood(){
      food.nutrition().setMacroNutrients( 40, 60, 20 );
      
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 40.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get(), is( 60.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get(), is( 20.0 ) );
      
      systemUnderTest.setFood( null );
      
      food.nutrition().setMacroNutrients( 100, 560, 990 );
      
      assertThat( systemUnderTest.foodAnalytics().nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 0.0 ) );
      assertThat( systemUnderTest.foodAnalytics().nutrition().of( NutritionalUnit.Fat ).get(), is( 0.0 ) );
      assertThat( systemUnderTest.foodAnalytics().nutrition().of( NutritionalUnit.Protein ).get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldSetNoFood(){
      systemUnderTest.setFood( null );
      systemUnderTest.setFood( food );
      systemUnderTest.setFood( null );
   }//End Method
   
   @Test public void shouldProvidePortionedMacros() {
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.nutrition().of( unit ), is( notNullValue() ) );
      }
   }//End Method
   
   @Test public void shouldUpdateMacrosWithPortion() {
      food.nutrition().setMacroNutrients( 40, 60, 20 );
      
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 40.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get(), is( 60.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get(), is( 20.0 ) );
      
      systemUnderTest.setPortion( 200 );
      
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 80.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get(), is( 120.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get(), is( 40.0 ) );
   }//End Method
   
   @Test public void shouldUpdateMacrosWhenFoodChanges() {
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 0.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get(), is( 0.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get(), is( 0.0 ) );
      
      food.nutrition().setMacroNutrients( 40, 60, 20 );
      
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 40.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get(), is( 60.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get(), is( 20.0 ) );
   }//End Method
   
   @Test public void shouldProvidePortion() {
      assertThat( systemUnderTest.portion().get(), is( 100.0 ) );
   }//End Method
   
   @Test public void shouldNotAllowPortionBelowZero(){
      systemUnderTest.setPortion( -10 );
      assertThat( systemUnderTest.portion().get(), is( 0.0 ) );
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.foodAnalytics().nutrition().of( unit ).get(), is( 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldAllowPortionZero(){
      systemUnderTest.setPortion( 0 );
      assertThat( systemUnderTest.portion().get(), is( 0.0 ) );
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.foodAnalytics().nutrition().of( unit ).get(), is( 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldProvideRatios(){
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.nutrition().of( unit ).get(), is( food.nutrition().of( unit ).get() ) );
      }
      
      food.nutrition().setMacroNutrients( 40, 60, 20 );
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.foodAnalytics().nutrition().of( unit ).get(), is( food.foodAnalytics().nutrition().of( unit ).get() ) );
      }
   }//End Method
   
   @Test public void shouldProvideRatiosForNewFood(){
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.nutrition().of( unit ).get(), is( food.nutrition().of( unit ).get() ) );
      }
      
      FoodItem otherFood = new FoodItem( "another" );
      otherFood.nutrition().setMacroNutrients( 40, 60, 20 );
      systemUnderTest.setFood( otherFood );
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.foodAnalytics().nutrition().of( unit ).get(), is( otherFood.foodAnalytics().nutrition().of( unit ).get() ) );
      }
      
      food.nutrition().setMacroNutrients( 3476, 90, 234 );
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.foodAnalytics().nutrition().of( unit ).get(), is( otherFood.foodAnalytics().nutrition().of( unit ).get() ) );
      }
   }//End Method
   
   @Test public void shouldResetRatiosWithNewFood(){
      food.nutrition().of( NutritionalUnit.Carbohydrate ).set( 56.0 );
      food.nutrition().of( NutritionalUnit.Fat ).set( 56.0 );
      food.nutrition().of( NutritionalUnit.Protein ).set( 56.0 );
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.nutrition().of( unit ).get(), is( food.nutrition().of( unit ).get() ) );
      }
      
      systemUnderTest.setFood( null );
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.foodAnalytics().nutrition().of( unit ).get(), is( 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldConstructWithInitialFoodAndPortion(){
      systemUnderTest = new FoodPortion( food, 67 );
      assertThat( systemUnderTest.food().get(), is( food ) );
      assertThat( systemUnderTest.portion().get(), is( 67.0 ) );
   }//End Method
   
   @Test public void shouldAssociateRatioCalculator(){
      verify( ratioCalculator ).associate( nutrition, foodAnalytics );
   }//End Method
   
   @Test public void shouldProvideFoodAnalytics(){
      assertThat( systemUnderTest.foodAnalytics(), is( foodAnalytics ) );
   }//End Method
   
   @Test public void shouldUpdateCaloriesWhenPortionChanges(){
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Calories ).get(), is( 0.0 ) );
      food.nutrition().of( NutritionalUnit.Calories ).set( 250.0 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Calories ).get(), is( 250.0 ) );
      systemUnderTest.setPortion( 50 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Calories ).get(), is( 125.0 ) );
   }//End Method
   
   @Test public void shouldIgnorePreviousFoodCalories(){
      systemUnderTest.setPortion( 50 );
      food.nutrition().of( NutritionalUnit.Calories ).set( 250.0 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Calories ).get(), is( 125.0 ) );
      
      systemUnderTest.setFood( new FoodItem( "anything" ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Calories ).get(), is( 0.0 ) );
      food.nutrition().of( NutritionalUnit.Calories ).set( 250.0 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Calories ).get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldUpdateCaloriesWhenCaloriesChanges(){
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Calories ).get(), is( 0.0 ) );
      systemUnderTest.setPortion( 50 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Calories ).get(), is( 0.0 ) );
      food.nutrition().of( NutritionalUnit.Calories ).set( 250.0 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Calories ).get(), is( 125.0 ) );
   }//End Method
   
   @Test public void shouldUpdateFiberWhenPortionChanges(){
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fibre ).get(), is( 0.0 ) );
      food.nutrition().of( NutritionalUnit.Fibre ).set( 250.0 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fibre ).get(), is( 250.0 ) );
      systemUnderTest.setPortion( 50 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fibre ).get(), is( 125.0 ) );
   }//End Method
   
   @Test public void shouldIgnorePreviousFoodFiber(){
      systemUnderTest.setPortion( 50 );
      food.nutrition().of( NutritionalUnit.Fibre ).set( 250.0 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fibre ).get(), is( 125.0 ) );
      
      systemUnderTest.setFood( new FoodItem( "anything" ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fibre ).get(), is( 0.0 ) );
      food.nutrition().of( NutritionalUnit.Fibre ).set( 250.0 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fibre ).get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldUpdateFiberWhenFiberChanges(){
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fibre ).get(), is( 0.0 ) );
      systemUnderTest.setPortion( 50 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fibre ).get(), is( 0.0 ) );
      food.nutrition().of( NutritionalUnit.Fibre ).set( 250.0 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fibre ).get(), is( 125.0 ) );
   }//End Method
   
   @Test public void shouldDuplicatePortion(){
      String ref = "ref";
      
      systemUnderTest.setFood( food );
      FoodPortion duplicate = systemUnderTest.duplicate( ref );
      assertFalse( duplicate == systemUnderTest );
      assertThat( duplicate.portion().get(), is( systemUnderTest.portion().get() ) );
      assertTrue( duplicate.food().get() == food );
   }//End Method
   
   @Test public void shouldDuplicatePortionWithNoFood(){
      String ref = "ref";
      
      systemUnderTest.setFood( null );
      FoodPortion duplicate = systemUnderTest.duplicate( ref );
      assertFalse( duplicate == systemUnderTest );
      assertThat( duplicate.portion().get(), is( systemUnderTest.portion().get() ) );
      assertThat( duplicate.food().get(), is( nullValue() ) );
   }//End Method
   
}//End Class
