package uk.dangrew.nuts.graphics.progress.custom;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class ProgressSeriesDataRowTest {

   private ProgressSeries series;
   private LocalDateTime timestamp;
   private ProgressSeriesDataRow systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      timestamp = LocalDateTime.now();
      series = new ProgressSeries( "Anything" );
      series.record( timestamp, 34.0 );
      
      systemUnderTest = new ProgressSeriesDataRow( series, timestamp );
   }//End Method

   @Test public void shouldProvideValues() {
      assertThat( systemUnderTest.timestamp(), is( timestamp ) );
      assertThat( systemUnderTest.valueProperty().get(), is( series.entryFor( timestamp ) ) );
   }//End Method
   
   @Test public void shouldFeedPropertyUpdateBackToSeries() {
      systemUnderTest.valueProperty().set( 100.01 );
      assertThat( series.entryFor( timestamp ), is( 100.01 ) );
      
      systemUnderTest.valueProperty().set( null );
      assertThat( series.entryFor( timestamp ), is( nullValue() ) );
   }//End Method

}//End Class
