package uk.dangrew.nuts.persistence.progress;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.datetime.DateTimeFormats;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesParseModelTest {

   private static final LocalDateTime NOW = LocalDateTime.of( 2018, 5, 8, 19, 38 );
   
   private DateTimeFormats formats;
   
   private ProgressSeries series;
   private Database database;
   private ProgressSeriesParseModel systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      formats = new DateTimeFormats();
      database = new Database();
      database.progressSeries().store( series = new ProgressSeries( "Existing" ) );
      systemUnderTest = new ProgressSeriesParseModel( database );
   }//End Method

   @Test public void shouldCreateLabelInStore() {
      systemUnderTest.setId( "id" );
      systemUnderTest.setName( "name" );
      systemUnderTest.finishSeries();
      
      assertThat( database.progressSeries().get( "id" ).properties().nameProperty().get(), is( "name" ) );
   }//End Method
   
   @Test public void shouldResetEntriesOfSeriesThatExists() {
      series.record( NOW, 100.0 );
      
      systemUnderTest.setId( series.properties().id() );
      systemUnderTest.setName( "name" );
      systemUnderTest.finishSeries();
      
      assertThat( series.entries(), hasSize( 0 ) );
      
   }//End Method
   
   @Test public void shouldPopulateEntries() {
      systemUnderTest.setId( series.properties().id() );
      systemUnderTest.setName( "name" );
      systemUnderTest.startEntry();
      systemUnderTest.setTimestamp( formats.toTimestampString( NOW ) );
      systemUnderTest.setValue( 100.0 );
      systemUnderTest.finishEntry();
      systemUnderTest.startEntry();
      systemUnderTest.setTimestamp( formats.toTimestampString( NOW.plusDays( 1 ) ) );
      systemUnderTest.setValue( 101.0 );
      systemUnderTest.finishEntry();
      systemUnderTest.startEntry();
      systemUnderTest.setTimestamp( formats.toTimestampString( NOW.plusDays( 2 ) ) );
      systemUnderTest.setValue( 102.0 );
      systemUnderTest.finishEntry();
      systemUnderTest.finishSeries();
      
      assertThat( database.progressSeries().objectList().get( 0 ).entries(), hasSize( 3 ) );
      assertThat( database.progressSeries().objectList().get( 0 ).entryFor( NOW ), is( 100.0 ) );
      assertThat( database.progressSeries().objectList().get( 0 ).entryFor( NOW.plusDays( 1 ) ), is( 101.0 ) );
      assertThat( database.progressSeries().objectList().get( 0 ).entryFor( NOW.plusDays( 2 ) ), is( 102.0 ) );
   }//End Method

}//End Class
