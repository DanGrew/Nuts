package uk.dangrew.nuts.persistence.stock;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.persistence.fooditems.FoodItemPersistence;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.stock.Stock;
import uk.dangrew.nuts.store.Database;

public class StockPersistenceTest {

   @Test public void shouldReadData() {
      Database database = new Database();
      FoodItemPersistence foodItemPersistence = new FoodItemPersistence( database );
      MealPersistence< Stock > persistence = new MealPersistence<>( database, database.stockLists() );
      
      String value = TestCommon.readFileIntoString( getClass(), "food-items.txt" );
      JSONObject json = new JSONObject( value );
      foodItemPersistence.readHandles().parse( json );
      
      value = TestCommon.readFileIntoString( getClass(), "stockLists.txt" );
      json = new JSONObject( value );
      persistence.readHandles().parse( json );
      
      Stock stock = database.stockLists().objectList().get( 0 );
      assertThat( stock.portions(), hasSize( 4 ) );
      assertThat( stock.portionFor( database.foodItems().get( "12345" ) ).portion().get(), is( 0.0 ) );
      assertThat( stock.portionFor( database.foodItems().get( "67890" ) ).portion().get(), is( 1000.0 ) );
      assertThat( stock.portionFor( database.foodItems().get( "3421" ) ).portion().get(), is( 20.0 ) );
      assertThat( stock.portionFor( database.foodItems().get( "1324" ) ).portion().get(), is( 100.0 ) );
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
      
      Stock stock = new Stock( "Stock" );
      stock.portions().add( new FoodPortion( item1, 100.0 ) );
      stock.portions().add( new FoodPortion( item2, 90.0 ) );
      stock.portions().add( new FoodPortion( item3, 50.0 ) );
      database.stockLists().store( stock );
      
      FoodItemPersistence foodItemPersistence = new FoodItemPersistence( database );
      JSONObject foodItemJson = new JSONObject();
      foodItemPersistence.structure().build( foodItemJson );
      foodItemPersistence.writeHandles().parse( foodItemJson );
      
      MealPersistence< Stock > persistence = new MealPersistence<>( database, database.stockLists() );
      JSONObject stockJson = new JSONObject();
      persistence.structure().build( stockJson );
      persistence.writeHandles().parse( stockJson );
      
      System.out.println( stockJson );
      
      database = new Database();
      foodItemPersistence = new FoodItemPersistence( database );
      
      assertThat( database.foodItems().objectList(), is( empty() ) );
      foodItemPersistence.readHandles().parse( foodItemJson );
      assertThat( database.foodItems().objectList(), hasSize( 4 ) );
      
      persistence = new MealPersistence<>( database, database.stockLists() );
      
      assertThat( database.stockLists().objectList(), is( empty() ) );
      persistence.readHandles().parse( stockJson );
      assertThat( database.stockLists().objectList(), hasSize( 1 ) );
      
      Stock readStock = database.stockLists().objectList().get( 0 );
      for ( FoodPortion portion : stock.portions() ) {
         FoodPortion readPortion = readStock.portions().stream().filter( p -> p.food().get().properties().id() == portion.food().get().properties().id() ).findFirst().get();
         assertThat( readPortion.portion().get(), is( portion.portion().get() ) );
      }
   }//End Method
   
}//End Class