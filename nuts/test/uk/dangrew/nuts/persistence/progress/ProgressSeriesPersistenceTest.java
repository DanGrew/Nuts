package uk.dangrew.nuts.persistence.progress;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.jupa.file.protocol.WorkspaceJsonPersistingProtocol;
import uk.dangrew.nuts.persistence.fooditems.DatabaseIo;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesPersistenceTest {

   private static final LocalDateTime NOW = LocalDateTime.of( 2018, 5, 8, 17, 15 );

   @Test public void shouldReadData() {
      Database database = new Database();
      new DatabaseIo( database )
         .withProgressSeries( new WorkspaceJsonPersistingProtocol( "progress-series.txt", getClass() ) )
         .read();
      
      assertParsedSeries( database );
   }//End Method
   
   @Test public void shouldWriteData(){
      Database database = new Database();
      
      ProgressSeries first = database.progressSeries().createConcept( "First" );
      for ( int i = 0; i < 5; i++ ) {
         first.values().record( NOW.plusDays( i ), i * 1.2 );
      }
      first.headers().record( NOW.plusDays( 2 ), "header2" );
      first.headers().record( NOW.plusDays( 3 ), "header3" );
      first.notes().record( NOW.plusDays( 3 ), "notes 3" );
      first.notes().record( NOW.plusDays( 4 ), "notes 4" );
      first.headers().record( NOW.plusDays( 5 ), "header5" );
      first.notes().record( NOW.plusDays( 5 ), "notes 5" );
      
      ProgressSeries second = database.progressSeries().createConcept( "Second" );
      for ( int i = 0; i < 100; i++ ) {
         second.values().record( NOW.plusHours( i ), i * 23.4 );
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
         assertThat( first.values().entryFor( keys.get( i ) ), is( i * 8.0 ) );
         
         if ( i != 4 && i != 5 ) {
            assertThat( first.headers().entryFor( keys.get( i ) ), is( nullValue() ) );      
         }
         if ( i != 5 && i != 9 ) {
            assertThat( first.notes().entryFor( keys.get( i ) ), is( nullValue() ) );      
         }
      }
      assertThat( first.headers().entryFor( keys.get( 4 ) ), is( "header4" ) );
      assertThat( first.headers().entryFor( keys.get( 5 ) ), is( "header5" ) );
      
      assertThat( first.notes().entryFor( keys.get( 5 ) ), is( "notes 5" ) );
      assertThat( first.notes().entryFor( keys.get( 9 ) ), is( "notes 9" ) );
      
      ProgressSeries second = database.progressSeries().objectList().get( 1 );
      keys = new ArrayList<>( second.entries() );
      for ( int i = 0; i < 33; i++ ) {
         assertThat( second.values().entryFor( keys.get( i ) ), is( i * 0.01 ) );
         assertThat( second.headers().entryFor( keys.get( i ) ), is( nullValue() ) );
         assertThat( second.notes().entryFor( keys.get( i ) ), is( nullValue() ) );
      }
   }//End Method
   
   private void assertWrittenSeries( Database database ){
      ProgressSeries first = database.progressSeries().objectList().get( 0 );
      List< LocalDateTime > keys = new ArrayList<>( first.entries() );
      for ( int i = 0; i < 5; i++ ) {
         assertThat( first.values().entryFor( keys.get( i ) ), is( i * 1.2 ) );
         
         if ( i != 2 && i != 3 ) {
            assertThat( first.headers().entryFor( keys.get( i ) ), is( nullValue() ) );
         }
         
         if ( i != 3 && i != 4 ) {
            assertThat( first.notes().entryFor( keys.get( i ) ), is( nullValue() ) );
         }
      }
      
      assertThat( first.values().entryFor( keys.get( 5 ) ), is( nullValue() ) );
      assertThat( first.headers().entryFor( keys.get( 2 ) ), is( "header2" ) );
      assertThat( first.headers().entryFor( keys.get( 3 ) ), is( "header3" ) );
      assertThat( first.notes().entryFor( keys.get( 3 ) ), is( "notes 3" ) );
      assertThat( first.notes().entryFor( keys.get( 4 ) ), is( "notes 4" ) );

      ProgressSeries second = database.progressSeries().objectList().get( 1 );
      keys = new ArrayList<>( second.entries() );
      for ( int i = 0; i < 100; i++ ) {
         assertThat( second.values().entryFor( keys.get( i ) ), is( i * 23.4 ) );
         assertThat( second.headers().entryFor( keys.get( i ) ), is( nullValue() ) );
         assertThat( second.notes().entryFor( keys.get( i ) ), is( nullValue() ) );
      }
   }//End Method
}//End Class