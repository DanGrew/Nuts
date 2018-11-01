package uk.dangrew.nuts.meal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.concept.Properties;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class MealRegistrationsTest {

   @Mock private MealChangeListener listener;
   
   private FoodItem food1;
   private FoodItem food2;
   private FoodPortion portion1;
   private FoodPortion portion2;
   
   private Meal meal;
   private Properties properties;
   private Nutrition nutrition;
   private MealRegistrations systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      food1 = new FoodItem( "Food1" );
      food2 = new FoodItem( "Food2" );
      portion1 = new FoodPortion();
      portion2 = new FoodPortion();
      
      properties = new Properties( "anything" );
      nutrition = new Nutrition();
      systemUnderTest = new MealRegistrations();
      meal = new Meal( 
               properties,
               nutrition,
               new FoodAnalytics(), 
               systemUnderTest, 
               mock( MealPropertiesCalculator.class ), new MacroRatioCalculator(), 
               new StockUsage()
      );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations(){
      systemUnderTest.associate( meal );
   }//End Method
   
   @Test public void shouldProvideMealChangeListener() {
      systemUnderTest.listen( listener );
      meal.portions().add( portion2 );
      verify( listener ).mealChanged();
   }//End Method
   
   @Test public void shouldNotifyWhenPortionAdded() {
      systemUnderTest.listen( listener );
      meal.portions().add( portion2 );
      verify( listener ).mealChanged();
   }//End Method
   
   @Test public void shouldNotifyWhenPortionFoodChanged() {
      meal.portions().add( portion1 );
      systemUnderTest.listen( listener );
      portion1.setFood( food1 );
      verify( listener, times( 1 ) ).mealChanged();
   }//End Method
   
   @Test public void shouldNotifyWhenFoodPortionPortionChanged() {
      meal.portions().add( portion1 );
      systemUnderTest.listen( listener );
      portion1.setPortion( 50 );
      verify( listener, times( 1 ) ).mealChanged();
   }//End Method
   
   @Test public void shouldNotifyWhenFoodMacrosChange() {
      meal.portions().add( portion1 );
      systemUnderTest.listen( listener );
      portion1.setFood( food1 );
      verify( listener, times( 1 ) ).mealChanged();
      
      food1.nutrition().of( NutritionalUnit.Carbohydrate ).set( 100.0 );
      verify( listener, times( 2 ) ).mealChanged();
      
      food1.nutrition().of( NutritionalUnit.Fat ).set( 100.0 );
      verify( listener, times( 3 ) ).mealChanged();
      
      food1.nutrition().of( NutritionalUnit.Protein ).set( 100.0 );
      verify( listener, times( 4 ) ).mealChanged();
      
      food1.nutrition().of( NutritionalUnit.Fibre ).set( 1.5 );
      verify( listener, times( 5 ) ).mealChanged();
   }//End Method
   
   @Test public void shouldNotifyWhenFoodCaloriesChange() {
      meal.portions().add( portion1 );
      systemUnderTest.listen( listener );
      portion1.setFood( food1 );
      verify( listener, times( 1 ) ).mealChanged();
      
      food1.nutrition().of( NutritionalUnit.Calories ).set( 100.0 );
      verify( listener, times( 2 ) ).mealChanged();
   }//End Method
   
   @Test public void shouldNotNotifyWhenFoodRemoved() {
      meal.portions().add( portion1 );
      portion1.setFood( food1 );
      systemUnderTest.listen( listener );
      portion1.setFood( null );
      verify( listener, times( 1 ) ).mealChanged();
      
      food1.nutrition().of( NutritionalUnit.Calories ).set( 230.0 );
      food1.nutrition().of( NutritionalUnit.Carbohydrate ).set( 100.0 );
      food1.nutrition().of( NutritionalUnit.Fat ).set( 100.0 );
      food1.nutrition().of( NutritionalUnit.Protein ).set( 100.0 );
      
      verify( listener, times( 1 ) ).mealChanged();
   }//End Method
   
   @Test public void shouldNotNotifyWhenPortionRemoved() {
      meal.portions().add( portion1 );
      portion1.setFood( food1 );
      systemUnderTest.listen( listener );
      meal.portions().remove( 0 );
      verify( listener, times( 1 ) ).mealChanged();
      
      portion1.setFood( food2 );
      portion1.setPortion( 89 );
      food2.nutrition().of( NutritionalUnit.Carbohydrate ).set( 100.0 );
      food2.nutrition().of( NutritionalUnit.Fat ).set( 100.0 );
      food2.nutrition().of( NutritionalUnit.Protein ).set( 100.0 );
      food2.nutrition().of( NutritionalUnit.Fibre ).set( 100.0 );
      
      verify( listener, times( 1 ) ).mealChanged();
   }//End Method

   @Test public void shouldStopListening(){
      meal.portions().add( portion1 );
      systemUnderTest.listen( listener );
      portion1.setPortion( 50 );
      verify( listener, times( 1 ) ).mealChanged();
      
      systemUnderTest.stopListening( listener );
      
      portion1.setFood( food2 );
      portion1.setPortion( 89 );
      food2.nutrition().of( NutritionalUnit.Carbohydrate ).set( 100.0 );
      food2.nutrition().of( NutritionalUnit.Fat ).set( 100.0 );
      food2.nutrition().of( NutritionalUnit.Protein ).set( 100.0 );
      food2.nutrition().of( NutritionalUnit.Fibre ).set( 100.0 );
      
      verifyNoMoreInteractions( listener );
   }//End Method
   
}//End Class
