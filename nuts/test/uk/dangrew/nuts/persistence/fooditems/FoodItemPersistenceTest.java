package uk.dangrew.nuts.persistence.fooditems;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.store.Database;

public class FoodItemPersistenceTest {

   @Test public void shouldReadData() {
      Database database = new Database();
      FoodItemPersistence persistence = new FoodItemPersistence( database );
      
      String value = TestCommon.readFileIntoString( getClass(), "food-items.txt" );
      JSONObject json = new JSONObject( value );
      persistence.readHandles().parse( json );
      
      FoodItem item = database.foodItems().objectList().get( 0 );
      assertFoodItemProperties( item, "de3c984c-f742-4cf7-98fd-501e40fa2291", "Egg", 0.6, 5.0, 6.0 );
      item = database.foodItems().objectList().get( 1 );
      assertFoodItemProperties( item, "be66258a-d0ff-4e54-a13a-e6069c333a2a", "Oats So Simple", 26.3, 2.2, 3.2 );
      item = database.foodItems().objectList().get( 2 );
      assertFoodItemProperties( item, "64303ce3-d19e-4ed6-b44f-9b59be6ca31d", "Apple", 31.4, 0.2, 1.0 );
   }//End Method
   
   @Test public void shouldWriteData(){
      Database database = new Database();
      
      FoodItem item1 = new FoodItem( "12345", "Food1" );
      item1.properties().setMacros( 45, 3.4, 98.1 );
      database.foodItems().store( item1 );
      
      FoodItem item2 = new FoodItem( "67890", "Food2" );
      item2.properties().setMacros( 2.11, 0.56, 123 );
      database.foodItems().store( item2 );
      
      FoodItemPersistence persistence = new FoodItemPersistence( database );
      JSONObject json = new JSONObject();
      persistence.structure().build( json );
      persistence.writeHandles().parse( json );
      
      database = new Database();
      persistence = new FoodItemPersistence( database );
      assertThat( database.foodItems().objectList(), is( empty() ) );
      
      persistence.readHandles().parse( json );
      assertThat( database.foodItems().objectList(), hasSize( 2 ) );
      
      FoodItem item = database.foodItems().objectList().get( 0 );
      assertFoodItemProperties( 
               item, 
               item1.properties().id(), item1.properties().nameProperty().get(),
               item1.properties().carbohydrates().get(),
               item1.properties().fats().get(),
               item1.properties().protein().get()
      );
      item = database.foodItems().objectList().get( 1 );
      assertFoodItemProperties( 
               item, 
               item2.properties().id(), item2.properties().nameProperty().get(),
               item2.properties().carbohydrates().get(),
               item2.properties().fats().get(),
               item2.properties().protein().get()
      );
   }//End Method

   /**
    * Method to assert that the {@link uk.dangrew.nuts.food.FoodProperties} are expected.
    * @param item the {@link FoodItem} to test.
    * @param id the expected.
    * @param name the expected.
    * @param c the expected carbs.
    * @param f the expected fats.
    * @param p the expected protein.
    */
   private void assertFoodItemProperties(
            FoodItem item,
            String id, String name,
            Double c, Double f, Double p
   ){
      assertThat( item.properties().id(), is( id ) );
      assertThat( item.properties().nameProperty().get(), is( name ) );
      assertThat( item.properties().carbohydrates().get(), is( c ) );
      assertThat( item.properties().fats().get(), is( f ) );
      assertThat( item.properties().protein().get(), is( p ) );
   }//End Method
}//End Class