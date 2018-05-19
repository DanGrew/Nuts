package uk.dangrew.nuts.graphics.common;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.startsWith;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.kode.datetime.TimestampFormat;
import uk.dangrew.kode.launch.TestApplication;

public class TimestampBoxTest {

   private TimestampBox systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TimestampBox();
   }//End Method

   @Test public void shouldHighlightInvalidInput() {
      systemUnderTest.setText( "" );
      
      assertThat( systemUnderTest.isValid(), is( false ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getTopStroke(), is( TimestampBox.INVALID_BORDER_COLOUR ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getBottomStroke(), is( TimestampBox.INVALID_BORDER_COLOUR ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getRightStroke(), is( TimestampBox.INVALID_BORDER_COLOUR ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getLeftStroke(), is( TimestampBox.INVALID_BORDER_COLOUR ) );
      
      systemUnderTest.setText( new TimestampFormat().toTimestampString( LocalDateTime.now() ) );
      
      assertThat( systemUnderTest.isValid(), is( true ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getTopStroke(), is( TimestampBox.VALID_BORDER_COLOUR ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getBottomStroke(), is( TimestampBox.VALID_BORDER_COLOUR ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getRightStroke(), is( TimestampBox.VALID_BORDER_COLOUR ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getLeftStroke(), is( TimestampBox.VALID_BORDER_COLOUR ) );
      
      systemUnderTest.setText( "anyhting" );
      
      assertThat( systemUnderTest.isValid(), is( false ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getTopStroke(), is( TimestampBox.INVALID_BORDER_COLOUR ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getBottomStroke(), is( TimestampBox.INVALID_BORDER_COLOUR ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getRightStroke(), is( TimestampBox.INVALID_BORDER_COLOUR ) );
      assertThat( systemUnderTest.getBorder().getStrokes().get( 0 ).getLeftStroke(), is( TimestampBox.INVALID_BORDER_COLOUR ) );
   }//End Method
   
   @Test public void shouldProvideValidatedResult() {
      systemUnderTest.setText( "anything" );
      assertThat( systemUnderTest.getTextValue(), is( nullValue() ) );
      LocalDateTime now = LocalDateTime.now();
      systemUnderTest.setText( new TimestampFormat().toTimestampString( now ) );
      TestCommon.assertThatInputIsInRangeOf( systemUnderTest.getTextValue(), now, true );
   }//End Method

}//End Class
