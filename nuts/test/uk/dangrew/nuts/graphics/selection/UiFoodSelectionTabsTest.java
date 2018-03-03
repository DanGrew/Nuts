package uk.dangrew.nuts.graphics.selection;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiFoodSelectionTabsTest {

   @Test public void untested() {
      System.out.println( "Not tested" );
   }//End Method
   
   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.startPlatform();
      
      Database database = new Database();
      DataLocation.loadSampleFoodData( database );
      database.stockLists().createConcept( "Stock" );
      
//      new UiFoodSelectionTabs( database, new UiFoodSelectionController( database, new Template( "Temp" ) ) );
      TestApplication.launch( () -> new UiFoodSelectionTabs( database, new UiFoodSelectionController( database, new Template( "Temp" ) ) ) );
      
      Thread.sleep( 999999999 );
   }//End Method

}//End Class
