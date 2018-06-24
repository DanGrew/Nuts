package uk.dangrew.nuts.meal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.system.Properties;

public class MealPropertiesCalculatorTest {

   private FoodPortion portion1;
   private FoodPortion portion2;
   private FoodPortion portion3;
   
   private Meal meal;
   @Mock private MealRegistrations registrations; 
   private MealPropertiesCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      FoodItem item1 = new FoodItem( "Food1" );
      item1.nutrition().setMacroNutrients( 1, 2, 7 );
      item1.nutrition().of( NutritionalUnit.Fibre ).set( 1.2 );
      item1.nutrition().of( NutritionalUnit.Calories ).set( 100.0 );
      portion1 = new FoodPortion();
      portion1.setFood( item1 );
      FoodItem item2 = new FoodItem( "Food2" );
      item2.nutrition().setMacroNutrients( 10, 2, 3 );
      item2.nutrition().of( NutritionalUnit.Fibre ).set( 0.5 );
      item2.nutrition().of( NutritionalUnit.Calories ).set( 150.0 );
      portion2 = new FoodPortion();
      portion2.setFood( item2 );
      FoodItem item3 = new FoodItem( "Food3" );
      item3.nutrition().setMacroNutrients( 50, 10, 40 );
      item3.nutrition().of( NutritionalUnit.Fibre ).set( 3.9 );
      item3.nutrition().of( NutritionalUnit.Calories ).set( 50.0 );
      portion3 = new FoodPortion();
      portion3.setFood( item3 );
      
      systemUnderTest = new MealPropertiesCalculator();
      meal = new Meal( 
               new Properties( "anything" ),
               new Nutrition(),
               new FoodAnalytics(), 
               registrations, 
               systemUnderTest, 
               new MacroRatioCalculator(),
               new StockUsage()
      );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations(){
      systemUnderTest.associate( meal );
   }//End Method
   
   @Test public void shouldRegisterForMealChanges() {
      verify( registrations ).listen( systemUnderTest );
   }//End Method
   
   @Test public void shouldCalculatePropertiesOnChange(){
      meal.portions().addAll( portion1, portion2, portion3 );
      systemUnderTest.mealChanged();
      meal.portions().clear();
      systemUnderTest.mealChanged();
      
      assertThat( meal.nutrition().of( NutritionalUnit.Calories ).get(), is( 0.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 0.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Fat ).get(), is( 0.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Protein ).get(), is( 0.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Fibre ).get(), is( 0.0 ) );
      
      assertThat( meal.foodAnalytics().carbohydratesRatio(), is( 0.0 ) );
      assertThat( meal.foodAnalytics().fatsRatio(), is( 0.0 ) );
      assertThat( meal.foodAnalytics().proteinRatio(), is( 0.0 ) );
      assertThat( meal.foodAnalytics().fiberRatioProperty().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldResetPropertiesWhenEmptyMeal(){
      meal.portions().addAll( portion1, portion2, portion3 );
      
      systemUnderTest.mealChanged();
      
      assertThat( meal.nutrition().of( NutritionalUnit.Calories ).get(), is( 300.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 61.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Fat ).get(), is( 14.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Protein ).get(), is( 50.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Fibre ).get(), is( 5.6 ) );
      
      assertThat( meal.foodAnalytics().carbohydratesRatio(), is( 48.8 ) );
      assertThat( meal.foodAnalytics().fatsRatio(), is( 11.2 ) );
      assertThat( meal.foodAnalytics().proteinRatio(), is( 40.0 ) );
      assertThat( meal.foodAnalytics().fiberRatioProperty().get(), is( 4.48 ) );
   }//End Method
   
   @Test public void shouldIgnorePortionsWithNoFood(){
      meal.portions().addAll( portion1, portion2, portion3, new FoodPortion() );
      
      systemUnderTest.mealChanged();
      
      assertThat( meal.nutrition().of( NutritionalUnit.Calories ).get(), is( 300.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 61.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Fat ).get(), is( 14.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Protein ).get(), is( 50.0 ) );
      assertThat( meal.nutrition().of( NutritionalUnit.Fibre ).get(), is( 5.6 ) );
      
      assertThat( meal.foodAnalytics().carbohydratesRatio(), is( 48.8 ) );
      assertThat( meal.foodAnalytics().fatsRatio(), is( 11.2 ) );
      assertThat( meal.foodAnalytics().proteinRatio(), is( 40.0 ) );
      assertThat( meal.foodAnalytics().fiberRatioProperty().get(), is( 4.48 ) );
   }//End Method

}//End Class
