package uk.dangrew.nuts.persistence.progress;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesPersistenceTest {

   private static final LocalDateTime NOW = LocalDateTime.of( 2018, 5, 8, 17, 15 );

   @Test public void shouldReadData() {
      Database database = new Database();
      
      ProgressSeriesPersistence persistence = new ProgressSeriesPersistence( database );
      String value = TestCommon.readFileIntoString( getClass(), "progress-series.txt" );
      JSONObject json = new JSONObject( value );
      persistence.readHandles().parse( json );
      
      assertParsedSeries( database );
   }//End Method
   
   @Test public void shouldWriteData(){
      Database database = new Database();
      
      ProgressSeries first = database.progressSeries().createConcept( "First" );
      for ( int i = 0; i < 5; i++ ) {
         first.record( NOW.plusDays( i ), i * 1.2 );
      }
      ProgressSeries second = database.progressSeries().createConcept( "Second" );
      for ( int i = 0; i < 100; i++ ) {
         second.record( NOW.plusHours( i ), i * 23.4 );
      }
      
      ProgressSeriesPersistence persistence = new ProgressSeriesPersistence( database );
      JSONObject json = new JSONObject();
      persistence.structure().build( json );
      persistence.writeHandles().parse( json );
      
      System.out.println( json );
      
      database = new Database();
      persistence = new ProgressSeriesPersistence( database );
      persistence.readHandles().parse( json );
      assertWrittenSeries( database );
   }//End Method
   
   private void assertParsedSeries( Database database ){
      ProgressSeries first = database.progressSeries().objectList().get( 0 );
      List< LocalDateTime > keys = new ArrayList<>( first.entries() );
      for ( int i = 0; i < 10; i++ ) {
         assertThat( first.entryFor( keys.get( i ) ), is( i * 8.0 ) );
      }
      ProgressSeries second = database.progressSeries().objectList().get( 1 );
      keys = new ArrayList<>( second.entries() );
      for ( int i = 0; i < 33; i++ ) {
         assertThat( second.entryFor( keys.get( i ) ), is( i * 0.01 ) );
      }
   }//End Method
   
   private void assertWrittenSeries( Database database ){
      ProgressSeries first = database.progressSeries().objectList().get( 0 );
      List< LocalDateTime > keys = new ArrayList<>( first.entries() );
      for ( int i = 0; i < 5; i++ ) {
         assertThat( first.entryFor( keys.get( i ) ), is( i * 1.2 ) );
      }

      ProgressSeries second = database.progressSeries().objectList().get( 1 );
      keys = new ArrayList<>( second.entries() );
      for ( int i = 0; i < 100; i++ ) {
         assertThat( second.entryFor( keys.get( i ) ), is( i * 23.4 ) );
      }
   }//End Method
}//End Class