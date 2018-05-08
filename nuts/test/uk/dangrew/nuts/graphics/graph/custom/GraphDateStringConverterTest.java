package uk.dangrew.nuts.graphics.graph.custom;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.datetime.DateTimeFormats;
import uk.dangrew.kode.launch.TestApplication;

public class GraphDateStringConverterTest {

   private DateTimeFormats formats;
   private GraphDateStringConverter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      formats = new DateTimeFormats();
      systemUnderTest = new GraphDateStringConverter();
   }//End Method

   @Test public void shouldConvertToNumber() {
      LocalDateTime now = LocalDateTime.now();
      long nowInSeconds = now.toEpochSecond( ZoneOffset.UTC );
      
      assertThat( systemUnderTest.fromString( formats.toTimestampString( now ) ), is( nowInSeconds ) );
      assertThat( systemUnderTest.fromString( null ), is( 0L ) );
      assertThat( systemUnderTest.fromString( "anything" ), is( 0L ) );
   }//End Method
   
   @Test public void shouldConvertToString() {
      LocalDateTime now = LocalDateTime.now();
      double nowInSeconds = now.toEpochSecond( ZoneOffset.UTC );
      
      assertThat( systemUnderTest.toString( nowInSeconds ), is( formats.toTimestampString( now ) ) );
      assertThat( systemUnderTest.toString( null ), is( DateTimeFormats.UNKNOWN ) );
   }//End Method

}//End Class
