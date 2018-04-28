package uk.dangrew.nuts.progress.custom;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;
import uk.dangrew.nuts.system.Properties;

public class ProgressSeriesTest {
   
   private static final String ID = "any id";
   private static final String NAME = "any name";
   
   @Mock private ProgressChangedListener< LocalDateTime > changeListener;
   private ProgressSeries systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProgressSeries( new Properties( ID, NAME ), changeListener );
   }//End Method
   
   @Test public void shouldConstructWithParams(){
      systemUnderTest = new ProgressSeries( ID, NAME );
      assertThat( systemUnderTest.properties().nameProperty().get(), is( NAME ) );
      assertThat( systemUnderTest.properties().id(), is( ID ) );
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideObject( ProgressSeries::properties )
         .shouldProvideObject( ProgressSeries::entries );
   }//End Method
   
   @Test public void shouldProvideChangedListener(){
      assertThat( systemUnderTest.progressChangedListener(), is( changeListener ) );
   }//End Method
   
   @Test public void shouldAddAndNotify(){
      LocalDateTime timestamp1 = LocalDateTime.now();
      double value1 = 100.0;
      
      systemUnderTest.record( timestamp1, value1 );
      verify( changeListener ).progressAdded( timestamp1, value1 );
      assertThat( systemUnderTest.entries().contains( timestamp1 ), is( true ) );
      assertThat( systemUnderTest.entryFor( timestamp1 ), is( value1 ) );
      
      LocalDateTime timestamp2 = LocalDateTime.now().plusDays( 100 );
      double value2 = 0.54;
      
      systemUnderTest.record( timestamp2, value2 );
      verify( changeListener ).progressAdded( timestamp2, value2 );
      
      assertThat( systemUnderTest.entries().contains( timestamp1 ), is( true ) );
      assertThat( systemUnderTest.entryFor( timestamp1 ), is( value1 ) );
      assertThat( systemUnderTest.entries().contains( timestamp2 ), is( true ) );
      assertThat( systemUnderTest.entryFor( timestamp2 ), is( value2 ) );
   }//End Method
   
   @Test public void shouldRemoveAndNotify(){
      LocalDateTime timestamp1 = LocalDateTime.now();
      double value1 = 100.0;
      
      systemUnderTest.record( timestamp1, value1 );
      verify( changeListener ).progressAdded( timestamp1, value1 );
      assertThat( systemUnderTest.entries().contains( timestamp1 ), is( true ) );
      assertThat( systemUnderTest.entryFor( timestamp1 ), is( value1 ) );
      
      systemUnderTest.record( timestamp1, null );
      verify( changeListener ).progressRemoved( timestamp1, value1 );
      
      assertThat( systemUnderTest.entries().contains( timestamp1 ), is( false ) );
      assertThat( systemUnderTest.entryFor( timestamp1 ), is( nullValue() ) );
      
      LocalDateTime notStored = LocalDateTime.now().plusDays( 100 );
      systemUnderTest.record( notStored, null );
      verify( changeListener, never() ).progressRemoved( eq( notStored ), Mockito.anyDouble() );
   }//End Method
   
   @Test public void shouldUpdateAndNotify(){
      LocalDateTime timestamp1 = LocalDateTime.now();
      double value1 = 100.0;
      
      systemUnderTest.record( timestamp1, value1 );
      verify( changeListener ).progressAdded( timestamp1, value1 );
      assertThat( systemUnderTest.entries().contains( timestamp1 ), is( true ) );
      assertThat( systemUnderTest.entryFor( timestamp1 ), is( value1 ) );
      
      double value2 = 0.54;
      
      systemUnderTest.record( timestamp1, value2 );
      verify( changeListener ).progressUpdated( timestamp1, value2 );
      
      assertThat( systemUnderTest.entries().contains( timestamp1 ), is( true ) );
      assertThat( systemUnderTest.entryFor( timestamp1 ), is( value2 ) );
   }//End Method
   
   @Test public void shouldNotUpdateIfEqual(){
      LocalDateTime timestamp1 = LocalDateTime.now();
      double value1 = 100.0;
      
      systemUnderTest.record( timestamp1, value1 );
      verify( changeListener ).progressAdded( timestamp1, value1 );
      assertThat( systemUnderTest.entries().contains( timestamp1 ), is( true ) );
      assertThat( systemUnderTest.entryFor( timestamp1 ), is( value1 ) );
      
      systemUnderTest.record( timestamp1, value1 );
      verify( changeListener, never() ).progressUpdated( timestamp1, value1 );
   }//End Method
   
   @Test public void shouldProvideProgressForRangeInDays(){
      LocalDateTime t1 = LocalDateTime.now();
      LocalDateTime t2 = LocalDateTime.now().plusDays( 1 );
      LocalDateTime t3 = LocalDateTime.now().plusDays( 2 );
      LocalDateTime t4 = LocalDateTime.now().plusDays( 3 );
      LocalDateTime t5 = LocalDateTime.now().plusDays( 4 );
      double v1 = 1223.2;
      double v2 = 95.2;
      double v3 = 0.1;
      double v4 = 578.4;
      double v5 = -34;
      
      systemUnderTest.record( t1, v1 );
      systemUnderTest.record( t2, v2 );
      systemUnderTest.record( t3, v3 );
      systemUnderTest.record( t4, v4 );
      systemUnderTest.record( t5, v5 );
      
      Map< LocalDateTime, Double > subMap = systemUnderTest.range( t1, t5 );
      assertThat( subMap, hasEntry( t1, v1 ) );
      assertThat( subMap, hasEntry( t2, v2 ) );
      assertThat( subMap, hasEntry( t3, v3 ) );
      assertThat( subMap, hasEntry( t4, v4 ) );
      assertThat( subMap, hasEntry( t5, v5 ) );
      
      subMap = systemUnderTest.range( t2, t4 );
      assertThat( subMap, not( hasEntry( t1, v1 ) ) );
      assertThat( subMap, hasEntry( t2, v2 ) );
      assertThat( subMap, hasEntry( t3, v3 ) );
      assertThat( subMap, hasEntry( t4, v4 ) );
      assertThat( subMap, not( hasEntry( t5, v5 ) ) );
   }//End Method
   
   @Test public void shouldProvideProgressForRangeInHours(){
      LocalDateTime t1 = LocalDateTime.of( 2018, 4, 20, 4, 45 );
      LocalDateTime t2 = t1.plusHours( 1 );
      LocalDateTime t3 = t1.plusHours( 2 );
      LocalDateTime t4 = t1.plusHours( 3 );
      LocalDateTime t5 = t1.plusHours( 4 );
      double v1 = 1223.2;
      double v2 = 95.2;
      double v3 = 0.1;
      double v4 = 578.4;
      double v5 = -34;
      
      systemUnderTest.record( t1, v1 );
      systemUnderTest.record( t2, v2 );
      systemUnderTest.record( t3, v3 );
      systemUnderTest.record( t4, v4 );
      systemUnderTest.record( t5, v5 );
      
      Map< LocalDateTime, Double > subMap = systemUnderTest.range( t1, t5 );
      assertThat( subMap, hasEntry( t1, v1 ) );
      assertThat( subMap, hasEntry( t2, v2 ) );
      assertThat( subMap, hasEntry( t3, v3 ) );
      assertThat( subMap, hasEntry( t4, v4 ) );
      assertThat( subMap, hasEntry( t5, v5 ) );
      
      subMap = systemUnderTest.range( t2, t4 );
      assertThat( subMap, not( hasEntry( t1, v1 ) ) );
      assertThat( subMap, hasEntry( t2, v2 ) );
      assertThat( subMap, hasEntry( t3, v3 ) );
      assertThat( subMap, hasEntry( t4, v4 ) );
      assertThat( subMap, not( hasEntry( t5, v5 ) ) );
      
      subMap = systemUnderTest.range( t2.toLocalDate() );
      assertThat( subMap, hasEntry( t1, v1 ) );
      assertThat( subMap, hasEntry( t2, v2 ) );
      assertThat( subMap, hasEntry( t3, v3 ) );
      assertThat( subMap, hasEntry( t4, v4 ) );
      assertThat( subMap, hasEntry( t5, v5 ) );
   }//End Method

}//End Class
