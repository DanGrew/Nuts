package uk.dangrew.nuts.graphics.graph.custom;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.datetime.DateTimeFormats;
import uk.dangrew.kode.datetime.TimestampFormat;
import uk.dangrew.kode.launch.TestApplication;

public class GraphDateStringConverterTest {

   private TimestampFormat format;
   private GraphDateStringConverter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      format = new TimestampFormat();
      systemUnderTest = new GraphDateStringConverter();
   }//End Method

   @Test public void shouldConvertToNumber() {
      LocalDateTime now = LocalDateTime.now();
      long nowInSeconds = now.toEpochSecond( ZoneOffset.UTC );
      
      assertThat( systemUnderTest.fromString( format.toTimestampString( now ) ), is( nowInSeconds ) );
      assertThat( systemUnderTest.fromString( null ), is( 0L ) );
      assertThat( systemUnderTest.fromString( "anything" ), is( 0L ) );
   }//End Method
   
   @Test public void shouldConvertToString() {
      LocalDateTime now = LocalDateTime.now();
      double nowInSeconds = now.toEpochSecond( ZoneOffset.UTC );
      
      assertThat( systemUnderTest.toString( nowInSeconds ), is( format.toTimestampString( now ) ) );
      assertThat( systemUnderTest.toString( null ), is( DateTimeFormats.UNKNOWN ) );
   }//End Method

}//End Class
