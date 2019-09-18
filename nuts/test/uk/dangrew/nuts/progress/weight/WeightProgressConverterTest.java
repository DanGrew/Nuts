package uk.dangrew.nuts.progress.weight;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.file.protocol.WorkspaceJsonPersistingProtocol;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.persistence.fooditems.DatabaseIo;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.store.Database;

public class WeightProgressConverterTest {

   private WeightProgress progress;
   private WeightProgressConverter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      Database database = new Database();
      new DatabaseIo( database )
         .withWeightRecordings( new WorkspaceJsonPersistingProtocol( "weight-progress.txt", getClass() ) )
         .read();
      
      progress = database.weightProgress();
      systemUnderTest = new WeightProgressConverter();
   }//End Method

   @Test public void shouldConvertEmptyWeightProgress() {
      progress = new WeightProgress();
      
      List< ProgressSeries > converted = systemUnderTest.convert( progress );
      assertThat( converted, hasSize( 6 ) );
      for ( int i = 0; i < 6; i++ ) {
         assertThat( converted.get( i ).entries(), is( empty() ) );
         assertThat( converted.get( i ).values().entries(), is( empty() ) );
      }
   }//End Method
   
   @Test public void shouldConvertMultipleWeightRecords() {
      List< ProgressSeries > converted = systemUnderTest.convert( progress );
      assertThat( converted, is( notNullValue() ) );
      assertThat( converted, hasSize( 6 ) );
   }//End Method
   
   @Test public void shouldConvertMorningWeight() {
      List< ProgressSeries > converted = systemUnderTest.convert( progress );
      ProgressSeries first = converted.get( 0 );
      assertThat( first.entries(), hasSize( 3 ) );
      
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 24, true ) ), is( 4.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 26, true ) ), is( 10.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 30, true ) ), is( nullValue() ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 15, true ) ), is( nullValue() ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 16, true ) ), is( 10.0 ) );
   }//End Method
   
   @Test public void shouldConvertfalseWeight() {
      List< ProgressSeries > converted = systemUnderTest.convert( progress );
      ProgressSeries first = converted.get( 1 );
      assertThat( first.entries(), hasSize( 3 ) );
      
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 24, false ) ), is( 2.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 26, false ) ), is( 8.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 30, false ) ), is( nullValue() ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 15, false ) ), is( 20.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 16, false ) ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldConvertMorningBodyFat() {
      List< ProgressSeries > converted = systemUnderTest.convert( progress );
      ProgressSeries first = converted.get( 2 );
      assertThat( first.entries(), hasSize( 3 ) );
      
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 24, true ) ), is( 5.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 26, true ) ), is( 11.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 30, true ) ), is( nullValue() ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 15, true ) ), is( nullValue() ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 16, true ) ), is( 20.0 ) );
   }//End Method
   
   @Test public void shouldConvertfalseBOdyFat() {
      List< ProgressSeries > converted = systemUnderTest.convert( progress );
      ProgressSeries first = converted.get( 3 );
      assertThat( first.entries(), hasSize( 3 ) );
      
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 24, false ) ), is( 1.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 26, false ) ), is( 7.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 30, false ) ), is( nullValue() ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 15, false ) ), is( 10.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 16, false ) ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldConvertMorningLeanMass() {
      List< ProgressSeries > converted = systemUnderTest.convert( progress );
      ProgressSeries first = converted.get( 4 );
      assertThat( first.entries(), hasSize( 3 ) );
      
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 24, true ) ), is( 6.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 26, true ) ), is( 12.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 30, true ) ), is( nullValue() ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 15, true ) ), is( nullValue() ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 16, true ) ), is( 30.0 ) );
   }//End Method
   
   @Test public void shouldConvertfalseLeanMass() {
      List< ProgressSeries > converted = systemUnderTest.convert( progress );
      ProgressSeries first = converted.get( 5 );
      assertThat( first.entries(), hasSize( 3 ) );
      
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 24, false ) ), is( 3.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 26, false ) ), is( 9.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 4, 30, false ) ), is( nullValue() ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 15, false ) ), is( 30.0 ) );
      assertThat( first.values().entryFor( dateTimeFor( 2017, 5, 16, false ) ), is( nullValue() ) );
   }//End Method
   
   private LocalDateTime dateTimeFor( int y, int m, int d, boolean morning ) {
      LocalTime time = morning ? LocalTime.NOON : LocalTime.MIDNIGHT;
      return LocalDateTime.of( 
               y, m, d, 
               time.getHour(), time.getMinute() 
      );
   }//End Method

}//End Class
