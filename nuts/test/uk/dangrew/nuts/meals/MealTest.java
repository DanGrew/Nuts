package uk.dangrew.nuts.meals;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.measurement.NutrientMeasurement;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class MealTest {

   @Mock private MealChangeListener listener;
   
   private Food food1;
   private Food food2;
   private FoodPortion portion1;
   private FoodPortion portion2;
   
   private Meal systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      food1 = new Food( "Food1" );
      food2 = new Food( "Food2" );
      portion1 = new FoodPortion();
      portion2 = new FoodPortion();
      systemUnderTest = new Meal();
   }//End Method

   @Test public void shouldProvideFoodPortions() {
      assertThat( systemUnderTest.portions(), is( empty() ) );
      systemUnderTest.portions().add( portion1 );
      assertThat( systemUnderTest.portions(), contains( portion1 ) );
   }//End Method
   
   @Test public void shouldProvideMealChangeListener() {
      systemUnderTest.listen( listener );
      systemUnderTest.portions().add( portion2 );
      verify( listener ).mealChanged();
   }//End Method
   
   @Test public void shouldNotifyWhenPortionAdded() {
      systemUnderTest.listen( listener );
      systemUnderTest.portions().add( portion2 );
      verify( listener ).mealChanged();
   }//End Method
   
   @Test public void shouldNotifyWhenPortionFoodChanged() {
      systemUnderTest.portions().add( portion1 );
      systemUnderTest.listen( listener );
      portion1.setFood( food1 );
      verify( listener ).mealChanged();
   }//End Method
   
   @Test public void shouldNotifyWhenFoodPortionPortionChanged() {
      systemUnderTest.portions().add( portion1 );
      systemUnderTest.listen( listener );
      portion1.setPortion( 50 );
      verify( listener ).mealChanged();
   }//End Method
   
   @Test public void shouldNotifyWhenFoodMacrosChange() {
      systemUnderTest.portions().add( portion1 );
      systemUnderTest.listen( listener );
      portion1.setFood( food1 );
      verify( listener ).mealChanged();
      
      food1.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 100 );
      verify( listener, times( 2 ) ).mealChanged();
      
      food1.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 100 );
      verify( listener, times( 3 ) ).mealChanged();
      
      food1.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 100 );
      verify( listener, times( 4 ) ).mealChanged();
   }//End Method
   
   @Test public void shouldNotNotifyWhenFoodRemoved() {
      systemUnderTest.portions().add( portion1 );
      portion1.setFood( food1 );
      systemUnderTest.listen( listener );
      portion1.setFood( null );
      verify( listener, times( 1 ) ).mealChanged();
      
      food1.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 100 );
      food1.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 100 );
      food1.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 100 );
      
      verify( listener, times( 1 ) ).mealChanged();
   }//End Method
   
   @Test public void shouldNotNotifyWhenPortionRemoved() {
      systemUnderTest.portions().add( portion1 );
      portion1.setFood( food1 );
      systemUnderTest.listen( listener );
      systemUnderTest.portions().remove( 0 );
      verify( listener, times( 1 ) ).mealChanged();
      
      portion1.setFood( food2 );
      portion1.setPortion( 89 );
      food2.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 100 );
      food2.nutritionFor( MacroNutrient.Fats ).setValue( NutrientMeasurement.Grams, 100 );
      food2.nutritionFor( MacroNutrient.Protein ).setValue( NutrientMeasurement.Grams, 100 );
      
      verify( listener, times( 1 ) ).mealChanged();
   }//End Method

}//End Class
