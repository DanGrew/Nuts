package uk.dangrew.nuts.day;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanController;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.meal.MealStore;
import uk.dangrew.nuts.system.ConceptStore;

public class DayPlanControllerTest {

   private FoodItem foodItem1;
   private FoodItem foodItem2;
   private Meal meal1;
   private Meal meal2;
   
   private FoodItemStore foodItems;
   private MealStore meals;
   
   private DayPlan dayPlan;
   private DayPlanController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      foodItem1 = new FoodItem( "Item1" );
      foodItem2 = new FoodItem( "Item2" );
      meal1 = new Meal( "Meal1" );
      meal2 = new Meal( "Meal2" );
      
      foodItems = new FoodItemStore();
      meals = new MealStore();
      dayPlan = new DayPlan( "Plan" );
      systemUnderTest = new DayPlanController( 
               foodItems, 
               meals 
      );
   }//End Method
   
   @Test public void shouldProvideStores(){
      assertThat( systemUnderTest.foodItems(), is( foodItems ) );
      assertThat( systemUnderTest.meals(), is( meals ) );
   }//End Method

   @Test public void shouldAddCopyOfFoodItemToDayPlan() {
      FoodPortion added = systemUnderTest.add( new FoodPortion( foodItem1, 34.0 ), dayPlan );
      assertThat( foodItems.objectList().contains( foodItem1 ), is( false ) );
      
      FoodItem stored = foodItems.objectList().get( 0 );
      assertThat( stored.properties().nameProperty().get(), is( foodItem1.properties().nameProperty().get() ) );
      
      assertThat( added, is( dayPlan.portions().get( 0 ) ) );
      assertThat( dayPlan.portions(), hasSize( 1 ) );
      assertThat( dayPlan.portions().get( 0 ).food().get(), is( stored ) );
      assertThat( dayPlan.portions().get( 0 ).portion().get(), is( 34.0 ) );
   }//End Method
   
   @Test public void shouldAddCopyOfMealToDayPlan() {
      meal1.portions().add( new FoodPortion( foodItem1, 20 ) );
      meal1.portions().add( new FoodPortion( foodItem2, 40 ) );
      
      systemUnderTest.add( new FoodPortion( meal1, 25.0 ), dayPlan );
      assertThat( meals.objectList().contains( meal1 ), is( false ) );
      assertThat( foodItems.objectList().contains( foodItem1 ), is( false ) );
      assertThat( foodItems.objectList().contains( foodItem2 ), is( false ) );
      
      Meal storedMeal = meals.objectList().get( 0 );
      assertThat( storedMeal.properties().nameProperty().get(), is( meal1.properties().nameProperty().get() ) );
      
      FoodItem firstStoredItem = foodItems.objectList().get( 0 );
      assertThat( firstStoredItem.properties().nameProperty().get(), is( foodItem1.properties().nameProperty().get() ) );
      FoodItem secondStoredItem = foodItems.objectList().get( 1 );
      assertThat( secondStoredItem.properties().nameProperty().get(), is( foodItem2.properties().nameProperty().get() ) );
      
      assertThat( dayPlan.portions(), hasSize( 1 ) );
      assertThat( dayPlan.portions().get( 0 ).food().get(), is( storedMeal ) );
      assertThat( dayPlan.portions().get( 0 ).portion().get(), is( 25.0 ) );
      
      assertThat( storedMeal.portions(), hasSize( 2 ) );
      assertThat( storedMeal.portions().get( 0 ).food().get(), is( firstStoredItem ) );
      assertThat( storedMeal.portions().get( 0 ).portion().get(), is( 20.0 ) );
      assertThat( storedMeal.portions().get( 1 ).food().get(), is( secondStoredItem ) );
      assertThat( storedMeal.portions().get( 1 ).portion().get(), is( 40.0 ) );
   }//End Method
   
   @Test public void shouldAddDeepCopyOfNestedMealsToDayPlan() {
      meal1.portions().add( new FoodPortion( foodItem1, 20 ) );
      meal1.portions().add( new FoodPortion( foodItem2, 40 ) );
      meal2.portions().add( new FoodPortion( meal1, 50 ) );
      meal2.portions().add( new FoodPortion( foodItem1, 60 ) );
      
      systemUnderTest.add( new FoodPortion( meal2, 70.0 ), dayPlan );
      assertThat( meals.objectList().contains( meal2 ), is( false ) );
      assertThat( meals.objectList().contains( meal1 ), is( false ) );
      assertThat( foodItems.objectList().contains( foodItem1 ), is( false ) );
      assertThat( foodItems.objectList().contains( foodItem2 ), is( false ) );
      
      assertMealHasBeenStored( 0, meal2 );
      assertMealHasBeenStored( 1, meal1 );
      
      assertFoodItemHasBeenStored( 0, foodItem1 );
      assertFoodItemHasBeenStored( 1, foodItem2 );
      assertFoodItemHasBeenStored( 2, foodItem1 );
      
      assertThat( dayPlan.portions(), hasSize( 1 ) );
      assertThat( dayPlan.portions().get( 0 ).food().get(), is( storedFood( 0, meals ) ) );
      assertThat( dayPlan.portions().get( 0 ).portion().get(), is( 70.0 ) );
      
      assertThat( storedFood( 0, meals ).portions(), hasSize( 2 ) );
      assertThat( storedFood( 0, meals ).portions().get( 0 ).food().get(), is( storedFood( 1, meals ) ) );
      assertThat( storedFood( 0, meals ).portions().get( 0 ).portion().get(), is( 50.0 ) );
      assertThat( storedFood( 0, meals ).portions().get( 1 ).food().get(), is( storedFood( 2, foodItems ) ) );
      assertThat( storedFood( 0, meals ).portions().get( 1 ).portion().get(), is( 60.0 ) );
      
      assertThat( storedFood( 1, meals ).portions(), hasSize( 2 ) );
      assertThat( storedFood( 1, meals ).portions().get( 0 ).food().get(), is( storedFood( 0, foodItems ) ) );
      assertThat( storedFood( 1, meals ).portions().get( 0 ).portion().get(), is( 20.0 ) );
      assertThat( storedFood( 1, meals ).portions().get( 1 ).food().get(), is( storedFood( 1, foodItems ) ) );
      assertThat( storedFood( 1, meals ).portions().get( 1 ).portion().get(), is( 40.0 ) );
   }//End Method
   
   @Test public void shouldRemoveFoodFromDayPlanAndDatabase(){
      meal1.portions().add( new FoodPortion( foodItem1, 20 ) );
      meal1.portions().add( new FoodPortion( foodItem2, 40 ) );
      meal2.portions().add( new FoodPortion( meal1, 50 ) );
      meal2.portions().add( new FoodPortion( foodItem1, 60 ) );
      
      systemUnderTest.add( new FoodPortion( meal2, 70 ), dayPlan );
      
      Meal firstMeal = ( Meal ) dayPlan.portions().get( 0 ).food().get();
      Meal secondMeal = ( Meal )firstMeal.portions().get( 0 ).food().get();
      FoodPortion toRemove = secondMeal.portions().get( 1 ); 
      systemUnderTest.remove( toRemove, dayPlan );
      
      assertThat( dayPlan.portions(), hasSize( 1 ) );
      assertThat( dayPlan.portions().get( 0 ).food().get(), is( storedFood( 0, meals ) ) );
      assertThat( dayPlan.portions().get( 0 ).portion().get(), is( 70.0 ) );
      
      assertThat( storedFood( 0, meals ).portions(), hasSize( 2 ) );
      assertThat( storedFood( 0, meals ).portions().get( 0 ).food().get(), is( storedFood( 1, meals ) ) );
      assertThat( storedFood( 0, meals ).portions().get( 0 ).portion().get(), is( 50.0 ) );
      assertThat( storedFood( 0, meals ).portions().get( 1 ).food().get(), is( storedFood( 1, foodItems ) ) );
      assertThat( storedFood( 0, meals ).portions().get( 1 ).portion().get(), is( 60.0 ) );
      
      assertThat( storedFood( 1, meals ).portions(), hasSize( 1 ) );
      assertThat( storedFood( 1, meals ).portions().get( 0 ).food().get(), is( storedFood( 0, foodItems ) ) );
      assertThat( storedFood( 1, meals ).portions().get( 0 ).portion().get(), is( 20.0 ) );
      
      assertThat( foodItems.objectList(), hasSize( 2 ) );
   }//End Method
   
   @Test public void shouldCopyMealWhenAddedToSubMeal() {
      meal1.portions().add( new FoodPortion( foodItem1, 20 ) );
      meal1.portions().add( new FoodPortion( foodItem2, 40 ) );
      meal2.portions().add( new FoodPortion( meal1, 50 ) );
      meal2.portions().add( new FoodPortion( foodItem1, 60 ) );
      
      systemUnderTest.add( new FoodPortion( meal2, 70.0 ), dayPlan );
      
      Meal firstMeal = ( Meal ) dayPlan.portions().get( 0 ).food().get();
      Meal toAdd = ( Meal )firstMeal.portions().get( 0 ).food().get();
      systemUnderTest.add( new FoodPortion( toAdd, 85 ), firstMeal );
      
      assertThat( dayPlan.portions(), hasSize( 1 ) );
      assertThat( dayPlan.portions().get( 0 ).food().get(), is( storedFood( 0, meals ) ) );
      assertThat( dayPlan.portions().get( 0 ).portion().get(), is( 70.0 ) );
      
      assertThat( storedFood( 0, meals ).portions(), hasSize( 3 ) );
      assertThat( storedFood( 0, meals ).portions().get( 0 ).food().get(), is( storedFood( 1, meals ) ) );
      assertThat( storedFood( 0, meals ).portions().get( 0 ).portion().get(), is( 50.0 ) );
      assertThat( storedFood( 0, meals ).portions().get( 1 ).food().get(), is( storedFood( 2, foodItems ) ) );
      assertThat( storedFood( 0, meals ).portions().get( 1 ).portion().get(), is( 60.0 ) );
      assertThat( storedFood( 0, meals ).portions().get( 2 ).food().get(), is( storedFood( 2, meals ) ) );
      assertThat( storedFood( 0, meals ).portions().get( 2 ).portion().get(), is( 85.0 ) );
      
      assertThat( storedFood( 1, meals ).portions(), hasSize( 2 ) );
      assertThat( storedFood( 1, meals ).portions().get( 0 ).food().get(), is( storedFood( 0, foodItems ) ) );
      assertThat( storedFood( 1, meals ).portions().get( 0 ).portion().get(), is( 20.0 ) );
      assertThat( storedFood( 1, meals ).portions().get( 1 ).food().get(), is( storedFood( 1, foodItems ) ) );
      assertThat( storedFood( 1, meals ).portions().get( 1 ).portion().get(), is( 40.0 ) );
      
      assertThat( foodItems.objectList(), hasSize( 5 ) );
      assertFoodItemHasBeenStored( 0, foodItem1 );
      assertFoodItemHasBeenStored( 1, foodItem2 );
      assertFoodItemHasBeenStored( 2, foodItem1 );
      assertFoodItemHasBeenStored( 3, foodItem1 );
      assertFoodItemHasBeenStored( 4, foodItem2 );
   }//End Method
   
//   @Test public void shouldRemovePortionFromSubMealAndDatabase(){
//      meal1.portions().add( new FoodPortion( foodItem1, 20 ) );
//      meal1.portions().add( new FoodPortion( foodItem2, 40 ) );
//      meal2.portions().add( new FoodPortion( meal1, 50 ) );
//      meal2.portions().add( new FoodPortion( foodItem1, 60 ) );
//      
//      systemUnderTest.add( new FoodPortion( meal2, 70 ), dayPlan );
//      
//      Meal firstMeal = ( Meal ) dayPlan.portions().get( 0 ).food().get();
//      Meal secondMeal = ( Meal )firstMeal.portions().get( 0 ).food().get();
//      FoodPortion toRemove = secondMeal.portions().get( 1 ); 
//      systemUnderTest.remove( toRemove, firstMeal );
//      
//      assertThat( dayPlan.portions(), hasSize( 1 ) );
//      assertThat( dayPlan.portions().get( 0 ).food().get(), is( storedFood( 0, meals ) ) );
//      assertThat( dayPlan.portions().get( 0 ).portion().get(), is( 70.0 ) );
//      
//      assertThat( storedFood( 0, meals ).portions(), hasSize( 2 ) );
//      assertThat( storedFood( 0, meals ).portions().get( 0 ).food().get(), is( storedFood( 1, meals ) ) );
//      assertThat( storedFood( 0, meals ).portions().get( 0 ).portion().get(), is( 50.0 ) );
//      assertThat( storedFood( 0, meals ).portions().get( 1 ).food().get(), is( storedFood( 1, foodItems ) ) );
//      assertThat( storedFood( 0, meals ).portions().get( 1 ).portion().get(), is( 60.0 ) );
//      
//      assertThat( storedFood( 1, meals ).portions(), hasSize( 1 ) );
//      assertThat( storedFood( 1, meals ).portions().get( 0 ).food().get(), is( storedFood( 0, foodItems ) ) );
//      assertThat( storedFood( 1, meals ).portions().get( 0 ).portion().get(), is( 20.0 ) );
//      
//      assertThat( foodItems.objectList(), hasSize( 2 ) );
//   }//End Method
   
   private < FoodTypeT extends Food > FoodTypeT storedFood( int index, ConceptStore< FoodTypeT > store ) {
      return store.objectList().get( index );
   }//End Method
   
   private void assertFoodItemHasBeenStored( int index, FoodItem original ) {
      FoodItem storedItem = foodItems.objectList().get( index );
      assertThat( storedItem.properties().nameProperty().get(), is( original.properties().nameProperty().get() ) );
   }//End Method
   
   private void assertMealHasBeenStored( int index, Meal original ) {
      Meal storedItem = meals.objectList().get( index );
      assertThat( storedItem.properties().nameProperty().get(), is( original.properties().nameProperty().get() ) );
   }//End Method

}//End Class
