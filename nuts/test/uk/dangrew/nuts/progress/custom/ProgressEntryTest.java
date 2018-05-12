package uk.dangrew.nuts.progress.custom;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class ProgressEntryTest {

   private static final LocalDateTime NOW = LocalDateTime.now();
   
   private ProgressEntry systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProgressEntry( NOW );
   }//End Method

   @Test public void shouldProvideTimestamp() {
      assertThat( systemUnderTest.timestamp(), is( NOW ) );
   }//End Method

   @Test public void shouldProvideValue() {
      assertThat( systemUnderTest.value(), is( 0.0 ) );
      systemUnderTest.setValue( 11.23 );
      assertThat( systemUnderTest.value(), is( 11.23 ) );
   }//End Method
   
   @Test public void shouldProvideHeading() {
      assertThat( systemUnderTest.header(), is( nullValue() ) );
      systemUnderTest.setHeader( "anything" );
      assertThat( systemUnderTest.header(), is( "anything" ) );
   }//End Method
   
   @Test public void shouldProvideNotes() {
      assertThat( systemUnderTest.notes(), is( nullValue() ) );
      systemUnderTest.setNotes( "anything" );
      assertThat( systemUnderTest.notes(), is( "anything" ) );
   }//End Method

}//End Class
