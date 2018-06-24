package uk.dangrew.nuts.persistence.fooditems;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.jupa.file.protocol.WorkspaceJsonPersistingProtocol;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;

public class FoodItemPersistenceTest {

   @Test public void shouldReadDataWithIndividualNutritionalUnits() {
      Database database = new Database();
      new DatabaseIo( database )
         .withFoodItems( new WorkspaceJsonPersistingProtocol( "food-items-individual-units.txt", getClass() ) )
         .read();
      
      FoodItem item = database.foodItems().objectList().get( 0 );
      assertFoodItemProperties( item, "de3c984c-f742-4cf7-98fd-501e40fa2291", "Egg", 0.6, 5.0, 6.0, 0.45, 1.0, 15.0 );
      item = database.foodItems().objectList().get( 1 );
      assertFoodItemProperties( item, "be66258a-d0ff-4e54-a13a-e6069c333a2a", "Oats So Simple", 26.3, 2.2, 3.2, 0.0, 1.0, 20.0 );
      item = database.foodItems().objectList().get( 2 );
      assertFoodItemProperties( item, "64303ce3-d19e-4ed6-b44f-9b59be6ca31d", "Apple", 31.4, 0.2, 1.0, 79.01, 1.0, 6.0 );
   }//End Method
   
   @Test public void shouldReadDataWithNutritionalUnitList() {
      Database database = new Database();
      new DatabaseIo( database )
         .withFoodItems( new WorkspaceJsonPersistingProtocol( "food-items.txt", getClass() ) )
         .read();
      
      FoodItem item = database.foodItems().objectList().get( 0 );
      assertFoodItemProperties( item, "de3c984c-f742-4cf7-98fd-501e40fa2291", "Egg", 0.6, 5.0, 6.0, 0.45, 1.0, 15.0 );
      item = database.foodItems().objectList().get( 1 );
      assertFoodItemProperties( item, "be66258a-d0ff-4e54-a13a-e6069c333a2a", "Oats So Simple", 26.3, 2.2, 3.2, 0.0, 1.0, 20.0 );
      item = database.foodItems().objectList().get( 2 );
      assertFoodItemProperties( item, "64303ce3-d19e-4ed6-b44f-9b59be6ca31d", "Apple", 31.4, 0.2, 1.0, 79.01, 1.0, 6.0 );
   }//End Method
   
   @Test public void shouldWriteData(){
      Database database = new Database();
      
      FoodItem item1 = new FoodItem( "12345", "Food1" );
      item1.properties().nutrition().setMacroNutrients( 45, 3.4, 98.1 );
      item1.properties().fiber().set( 87.34 );
      item1.stockProperties().setWeighting( 100, 750 );
      database.foodItems().store( item1 );
      
      FoodItem item2 = new FoodItem( "67890", "Food2" );
      item2.properties().nutrition().setMacroNutrients( 2.11, 0.56, 123 );
      item2.stockProperties().setWeighting( 10, 500 );
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
               item1.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(),
               item1.properties().nutrition().of( NutritionalUnit.Fat ).get(),
               item1.properties().protein().get(),
               item1.properties().fiber().get(),
               item1.stockProperties().loggedWeight().get(),
               item1.stockProperties().soldInWeight().get()
      );
      item = database.foodItems().objectList().get( 1 );
      assertFoodItemProperties( 
               item, 
               item2.properties().id(), item2.properties().nameProperty().get(),
               item2.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(),
               item2.properties().nutrition().of( NutritionalUnit.Fat ).get(),
               item2.properties().protein().get(),
               item2.properties().fiber().get(),
               item2.stockProperties().loggedWeight().get(),
               item2.stockProperties().soldInWeight().get()
      );
   }//End Method

   private void assertFoodItemProperties(
            FoodItem item,
            String id, String name,
            Double c, Double f, Double p, Double i,
            Double lw, Double siw
   ){
      assertThat( item.properties().id(), is( id ) );
      assertThat( item.properties().nameProperty().get(), is( name ) );
      assertThat( item.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( c ) );
      assertThat( item.properties().nutrition().of( NutritionalUnit.Fat ).get(), is( f ) );
      assertThat( item.properties().protein().get(), is( p ) );
      assertThat( item.properties().fiber().get(), is( i ) );
      assertThat( item.stockProperties().loggedWeight().get(), is( lw ) );
      assertThat( item.stockProperties().soldInWeight().get(), is( siw ) );
   }//End Method
}//End Class