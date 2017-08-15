package uk.dangrew.nuts.graphics.shopping;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class ShoppingPaneTest {

   private Database database;
   private ShoppingPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      database = new Database();
      systemUnderTest = new ShoppingPane( database, new Meal( "List" ) );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      DataLocation.loadSampleFoodData( database );
      
      Thread.sleep( 99999999 );
   }//End Method
   
   @Test public void untested() {
      System.out.println( "WARNING: UNTESTED - " + getClass().getName() );
   }//End Method

}//End Class
