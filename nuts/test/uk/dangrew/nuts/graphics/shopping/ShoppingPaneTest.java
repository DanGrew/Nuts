package uk.dangrew.nuts.graphics.shopping;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.persistence.fooditems.FoodItemPersistence;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class ShoppingPaneTest {

   private Database database;
   private ShoppingPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      database = new Database();
      systemUnderTest = new ShoppingPane( database );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      FoodItemPersistence foodItemPersistence = new FoodItemPersistence( database );
      MealPersistence mealPersistence = new MealPersistence( database, database.meals() );
      MealPersistence planPersistence = new MealPersistence( database, database.plans() );
      
      String value = TestCommon.readFileIntoString( DataLocation.class, "food-items.json" );
      JSONObject json = new JSONObject( value );
      foodItemPersistence.readHandles().parse( json );
      
      value = TestCommon.readFileIntoString( DataLocation.class, "meals.json" );
      json = new JSONObject( value );
      mealPersistence.readHandles().parse( json );
      
      value = TestCommon.readFileIntoString( DataLocation.class, "plans.json" );
      json = new JSONObject( value );
      planPersistence.readHandles().parse( json );
      
      Thread.sleep( 99999999 );
   }//End Method
   
   @Test public void untested() {
      System.out.println( "WARNING: UNTESTED - " + getClass().getName() );
   }//End Method

}//End Class
