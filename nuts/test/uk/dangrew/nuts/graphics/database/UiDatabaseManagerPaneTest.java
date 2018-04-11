package uk.dangrew.nuts.graphics.database;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.store.Database;

public class UiDatabaseManagerPaneTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      Database database = new Database();
      DataLocation.loadSampleFoodData( database );
      
      TestApplication.launch( () -> new UiDatabaseManagerPane( database ) );
      
      Thread.sleep( 99999 );
   }//End Method

}//End Class
