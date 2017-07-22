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
      assertFoodItemProperties( item, "36aa4059-24c1-496c-af11-8e61a4ccc4ea", "Egg", 0.0, 8.0, 9.0 );
      item = database.foodItems().objectList().get( 1 );
      assertFoodItemProperties( item, "3156adb6-e267-4e79-8993-ea30399f84f0", "Oats So Simple", 26.3, 2.2, 3.2 );
      item = database.foodItems().objectList().get( 2 );
      assertFoodItemProperties( item, "39f8fd1c-b08a-42fe-aa42-4e2f7798372c", "Apple", 31.4, 0.2, 1.0 );
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
               item1.properties().carbohydrates().inGrams(),
               item1.properties().fats().inGrams(),
               item1.properties().protein().inGrams()
      );
      item = database.foodItems().objectList().get( 1 );
      assertFoodItemProperties( 
               item, 
               item2.properties().id(), item2.properties().nameProperty().get(),
               item2.properties().carbohydrates().inGrams(),
               item2.properties().fats().inGrams(),
               item2.properties().protein().inGrams()
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
      assertThat( item.properties().carbohydrates().inGrams(), is( c ) );
      assertThat( item.properties().fats().inGrams(), is( f ) );
      assertThat( item.properties().protein().inGrams(), is( p ) );
   }//End Method
}//End Class