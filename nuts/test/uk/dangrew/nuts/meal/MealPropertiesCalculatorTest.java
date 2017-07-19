package uk.dangrew.nuts.meal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.food.FoodProperties;

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
      item1.properties().setMacros( 1, 2, 7 );
      portion1 = new FoodPortion();
      portion1.setFood( item1 );
      FoodItem item2 = new FoodItem( "Food2" );
      item2.properties().setMacros( 10, 2, 3 );
      portion2 = new FoodPortion();
      portion2.setFood( item2 );
      FoodItem item3 = new FoodItem( "Food3" );
      item3.properties().setMacros( 50, 10, 40 );
      portion3 = new FoodPortion();
      portion3.setFood( item3 );
      
      systemUnderTest = new MealPropertiesCalculator();
      meal = new Meal( registrations, new FoodProperties( "anything" ), systemUnderTest );
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
      
      assertThat( meal.properties().carbohydrates().inGrams(), is( 0.0 ) );
      assertThat( meal.properties().fats().inGrams(), is( 0.0 ) );
      assertThat( meal.properties().protein().inGrams(), is( 0.0 ) );
      
      assertThat( meal.properties().analytics().carbohydratesRatio(), is( 0.0 ) );
      assertThat( meal.properties().analytics().fatsRatio(), is( 0.0 ) );
      assertThat( meal.properties().analytics().proteinRatio(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldResetPropertiesWhenEmptyMeal(){
      meal.portions().addAll( portion1, portion2, portion3 );
      
      systemUnderTest.mealChanged();
      
      assertThat( meal.properties().carbohydrates().inGrams(), is( 61.0 ) );
      assertThat( meal.properties().fats().inGrams(), is( 14.0 ) );
      assertThat( meal.properties().protein().inGrams(), is( 50.0 ) );
      
      assertThat( meal.properties().analytics().carbohydratesRatio(), is( 48.8 ) );
      assertThat( meal.properties().analytics().fatsRatio(), is( 11.2 ) );
      assertThat( meal.properties().analytics().proteinRatio(), is( 40.0 ) );
   }//End Method
   
   @Test public void shouldIgnorePortionsWithNoFood(){
      meal.portions().addAll( portion1, portion2, portion3, new FoodPortion() );
      
      systemUnderTest.mealChanged();
      
      assertThat( meal.properties().carbohydrates().inGrams(), is( 61.0 ) );
      assertThat( meal.properties().fats().inGrams(), is( 14.0 ) );
      assertThat( meal.properties().protein().inGrams(), is( 50.0 ) );
      
      assertThat( meal.properties().analytics().carbohydratesRatio(), is( 48.8 ) );
      assertThat( meal.properties().analytics().fatsRatio(), is( 11.2 ) );
      assertThat( meal.properties().analytics().proteinRatio(), is( 40.0 ) );
   }//End Method

}//End Class
