package uk.dangrew.nuts.graphics.progress.custom;

import java.time.LocalDateTime;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesPaneTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      Database database = new Database();
      ProgressSeries first = database.progressSeries().createConcept( "First" );
      for ( int i = 0; i < 20; i++ ) {
         first.record( LocalDateTime.now().plusDays( i ), i* 23.1 );
      }
      
      database.progressSeries().createConcept( "Second" );
      TestApplication.launch( () -> new ProgressSeriesPane( database ) );
      
      Thread.sleep( 99999999 );
   }//End Method

}//End Class
