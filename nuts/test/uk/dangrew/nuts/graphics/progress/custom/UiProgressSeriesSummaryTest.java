package uk.dangrew.nuts.graphics.progress.custom;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.store.Database;

public class UiProgressSeriesSummaryTest {

   private ProgressSeries subject;
   private UiProgressSeriesSummary systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      Database database = new Database();
      DataLocation.loadSampleProgressSeries( database );
      
      subject = database.progressSeries().objectList().get( 0 );
      systemUnderTest = new UiProgressSeriesSummary( subject );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 9999999 );
   }//End Method
   
}//End Class
