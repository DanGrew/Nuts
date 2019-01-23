package uk.dangrew.nuts.progress.custom;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.concept.Properties;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;

public class ProgressSeriesTest {
   
   private static final String ID = "any id";
   private static final String NAME = "any name";
   
   private ProgressSeries systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProgressSeries( new Properties( ID, NAME ) );
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
   
   @Test public void shouldProvideData(){
      assertThat( systemUnderTest.values(), is( notNullValue() ) );
      assertThat( systemUnderTest.headers(), is( notNullValue() ) );
      assertThat( systemUnderTest.notes(), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldNotDuplicate(){
      assertThat( systemUnderTest.duplicate(), is( systemUnderTest ) );
   }//End Method
   
   @Test public void shouldProvideCombinedEntries(){
      LocalDateTime a = LocalDateTime.now();
      LocalDateTime b = LocalDateTime.now().plusDays( 1 );
      LocalDateTime c = LocalDateTime.now().plusDays( 3 );
      LocalDateTime d = LocalDateTime.now().minusDays( 4 );
      
      systemUnderTest.values().record( a, 0.0 );
      systemUnderTest.values().record( b, 0.0 );
      systemUnderTest.values().record( c, 0.0 );
      systemUnderTest.headers().record( d, "heading" );
      systemUnderTest.notes().record( d, "note" );
      systemUnderTest.notes().record( a, "" );
      
      assertThat( systemUnderTest.entries(), contains( d, a, b, c ) );
   }//End Method
   
   @Test public void shouldClearData(){
      LocalDateTime a = LocalDateTime.now();
      LocalDateTime b = LocalDateTime.now().plusDays( 1 );
      LocalDateTime c = LocalDateTime.now().plusDays( 3 );
      LocalDateTime d = LocalDateTime.now().minusDays( 4 );
      
      systemUnderTest.values().record( a, 0.0 );
      systemUnderTest.values().record( b, 0.0 );
      systemUnderTest.values().record( c, 0.0 );
      systemUnderTest.headers().record( d, "heading" );
      systemUnderTest.notes().record( d, "note" );
      systemUnderTest.notes().record( a, "" );
      
      systemUnderTest.clear();
      assertThat( systemUnderTest.entries(), is( empty() ) );
      assertThat( systemUnderTest.values().entries(), is( empty() ) );
      assertThat( systemUnderTest.headers().entries(), is( empty() ) );
      assertThat( systemUnderTest.notes().entries(), is( empty() ) );
   }//End Method
   
}//End Class
