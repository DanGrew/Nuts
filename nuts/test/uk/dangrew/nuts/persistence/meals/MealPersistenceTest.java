package uk.dangrew.nuts.persistence.meals;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import javafx.util.Pair;
import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.persistence.fooditems.FoodItemPersistence;
import uk.dangrew.nuts.store.Database;

public class MealPersistenceTest {

   @Test public void shouldReadData() {
      Database database = new Database();
      FoodItemPersistence foodItemPersistence = new FoodItemPersistence( database );
      MealPersistence persistence = new MealPersistence( database );
      
      String value = TestCommon.readFileIntoString( getClass(), "food-items.txt" );
      JSONObject json = new JSONObject( value );
      foodItemPersistence.readHandles().parse( json );
      
      value = TestCommon.readFileIntoString( getClass(), "meals.txt" );
      json = new JSONObject( value );
      persistence.readHandles().parse( json );
      
      Meal meal = database.meals().objectList().get( 0 );
      assertMealProperties( 
               meal, "99987", "Meal1", 
               new Pair<>( "12345", 100.0 ),
               new Pair<>( "67890", 90.0 ),
               new Pair<>( "3421", 50.0 ) 
      );
      meal = database.meals().objectList().get( 1 );
      assertMealProperties( 
               meal, "556676", "Meal2", 
               new Pair<>( "67890", 40.0 ),
               new Pair<>( "3421", 90.0 ),
               new Pair<>( "1324", 100.0 )
      );
      meal = database.meals().objectList().get( 2 );
      assertMealProperties( 
               meal, "8878886", "Meal3", 
               new Pair<>( "3421", 100.0 )
      );
   }//End Method
   
   @Test public void shouldWriteData(){
      Database database = new Database();
      
      FoodItem item1 = new FoodItem( "12345", "Food1" );
      item1.properties().setMacros( 45, 3.4, 98.1 );
      database.foodItems().store( item1 );
      
      FoodItem item2 = new FoodItem( "67890", "Food2" );
      item2.properties().setMacros( 2.11, 0.56, 123 );
      database.foodItems().store( item2 );
      
      FoodItem item3 = new FoodItem( "3421", "Food3" );
      item3.properties().setMacros( 2.3, 3.8, 8.6 );
      database.foodItems().store( item3 );
      
      FoodItem item4 = new FoodItem( "1324", "Food4" );
      item4.properties().setMacros( 0.1, 1.1, 0.3 );
      database.foodItems().store( item4 );
      
      Meal meal1 = new Meal( "99987", "Meal1" );
      meal1.portions().add( new FoodPortion( item1, 100.0 ) );
      meal1.portions().add( new FoodPortion( item2, 90.0 ) );
      meal1.portions().add( new FoodPortion( item3, 50.0 ) );
      database.meals().store( meal1 );
      
      Meal meal2 = new Meal( "556676", "Meal2" );
      meal2.portions().add( new FoodPortion( item2, 40.0 ) );
      meal2.portions().add( new FoodPortion( item3, 90.0 ) );
      meal2.portions().add( new FoodPortion( item4, 100.0 ) );
      meal2.portions().add( new FoodPortion() );
      database.meals().store( meal2 );
      
      Meal meal3 = new Meal( "8878886", "Meal3" );
      meal3.portions().add( new FoodPortion( item3, 100.0 ) );
      database.meals().store( meal3 );
      
      FoodItemPersistence foodItemPersistence = new FoodItemPersistence( database );
      JSONObject foodItemJson = new JSONObject();
      foodItemPersistence.structure().build( foodItemJson );
      foodItemPersistence.writeHandles().parse( foodItemJson );
      
      MealPersistence persistence = new MealPersistence( database );
      JSONObject mealJson = new JSONObject();
      persistence.structure().build( mealJson );
      persistence.writeHandles().parse( mealJson );
      
      System.out.println( mealJson );
      
      database = new Database();
      foodItemPersistence = new FoodItemPersistence( database );
      
      assertThat( database.foodItems().objectList(), is( empty() ) );
      foodItemPersistence.readHandles().parse( foodItemJson );
      assertThat( database.foodItems().objectList(), hasSize( 4 ) );
      
      persistence = new MealPersistence( database );
      
      assertThat( database.meals().objectList(), is( empty() ) );
      persistence.readHandles().parse( mealJson );
      assertThat( database.meals().objectList(), hasSize( 3 ) );
      
      Meal meal = database.meals().objectList().get( 0 );
      assertMealProperties( meal, meal1 );
      meal = database.meals().objectList().get( 1 );
      assertMealProperties( meal, meal2 );
      meal = database.meals().objectList().get( 2 );
      assertMealProperties( meal, meal3 );
   }//End Method

   private void assertMealProperties(
            Meal meal, 
            String id, String name,
            Pair< String, Double >... portions
   ){
      assertThat( meal.properties().id(), is( id ) );
      assertThat( meal.properties().nameProperty().get(), is( name ) );
      assertThat( meal.portions(), hasSize( portions.length ) );
      
      for ( int i = 0; i < portions.length; i++ ) {
         Pair< String, Double > expectedPortion = portions[ i ];
         FoodPortion portion = meal.portions().get( i );
         assertThat( expectedPortion.getKey(), is( portion.food().get().properties().id() ) );
         assertThat( expectedPortion.getValue(), is( portion.portion().get() ) );
      }
   }//End Method
   
   private void assertMealProperties(
            Meal meal, Meal expected
   ){
      assertThat( meal.properties().id(), is( expected.properties().id() ) );
      assertThat( meal.properties().nameProperty().get(), is( expected.properties().nameProperty().get() ) );
      assertThat( meal.portions(), hasSize( expected.portions().size() ) );
      
      for ( int i = 0; i < expected.portions().size(); i++ ) {
         FoodPortion expectedPortion = expected.portions().get( i );
         FoodPortion portion = meal.portions().get( i );
         if ( expectedPortion.food().get() == null ) {
            assertThat( portion.food().get(), is( nullValue() ) );
         } else {
            assertThat( expectedPortion.food().get().properties().id(), is( portion.food().get().properties().id() ) );
         }
         assertThat( expectedPortion.portion().get(), is( portion.portion().get() ) );
      }
   }//End Method
}//End Class