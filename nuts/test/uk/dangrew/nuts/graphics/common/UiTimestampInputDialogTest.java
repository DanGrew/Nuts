package uk.dangrew.nuts.graphics.common;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.control.ButtonType;
import uk.dangrew.kode.datetime.TimestampFormat;
import uk.dangrew.kode.launch.TestApplication;

public class UiTimestampInputDialogTest {

   private UiTimestampInputDialog systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      PlatformImpl.runAndWait( () -> systemUnderTest = new UiTimestampInputDialog() );
   }//End Method

   @Ignore
   @Test public void manual() {
      PlatformImpl.runAndWait( () -> systemUnderTest.showAndWait() );
   }//End Method
   
   @Test public void shouldInitialiseDateAndTimeToCurrent(){
      assertThatInputIsInRangeOf( LocalDateTime.now(), true );
   }//End Method
   
   private void assertThatInputIsInRangeOf( LocalDateTime timestamp, boolean insideRange ) {
      LocalDateTime now = systemUnderTest.timestampBox().getTextValue();
      assertThat( now.isAfter( timestamp.minusMinutes( 5 ) ) && now.isBefore( timestamp.plusMinutes( 5 ) ), is( insideRange ) );
   }//End Method
   
   @Test public void shouldSetDateAndTimeToCurrent(){
      systemUnderTest.timestampBox().setText( "01-01-1900 05:06:07" );
      assertThatInputIsInRangeOf( LocalDateTime.now(), false );
      systemUnderTest.resetInputToNow();
      assertThatInputIsInRangeOf( LocalDateTime.now(), true );
   }//End Method
   
   @Test public void shouldConvertResult(){
      LocalDateTime now = LocalDateTime.now();
      now = now.minusNanos( now.getNano() );
      
      systemUnderTest.timestampBox().setText( new TimestampFormat().toTimestampString( now ) );
      assertThat( systemUnderTest.getResultConverter().call( ButtonType.OK ), is( now ) );
   }//End Method

}//End Class
