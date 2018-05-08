package uk.dangrew.nuts.graphics.graph.custom;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class GraphTest {

   private Graph systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new Graph( new GraphBuilder() );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      ProgressSeries progressSeries = new ProgressSeries( "Series" );
      for ( int i = 0; i < 7; i++ ) {
         progressSeries.record( LocalDateTime.now().plusDays( i ), i* 10.0 );
      }
      
      TestApplication.launch( () -> {
         systemUnderTest.controller().seriesVisibility().add( progressSeries );
         systemUnderTest.controller().setDateLowerBound( LocalDateTime.now().toEpochSecond( ZoneOffset.UTC ) );
         systemUnderTest.controller().setDateUpperBound( LocalDateTime.now().plusDays( 10 ).toEpochSecond( ZoneOffset.UTC ) );
         
         return systemUnderTest;
      } );
      
      Thread.sleep( 99999999 );
   }//End Method

}//End Class
