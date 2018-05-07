package uk.dangrew.nuts.graphics.common;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import uk.dangrew.kode.launch.TestApplication;

public class UiDateTimeInputDialogTest {

   private UiDateTimeInputDialog systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      PlatformImpl.runAndWait( () -> systemUnderTest = new UiDateTimeInputDialog() );
   }//End Method

   @Ignore
   @Test public void manual() {
      PlatformImpl.runAndWait( () -> systemUnderTest.showAndWait() );
   }//End Method
   
   @Test public void shouldInitialiseDateAndTimeToCurrent(){
      assertThatInputIsInRangeOf( LocalDateTime.now(), true );
   }//End Method
   
   private void assertThatInputIsInRangeOf( LocalDateTime timestamp, boolean insideRange ) {
      LocalDate dateInBox = LocalDate.parse( systemUnderTest.dateField().getText(), UiDateTimeInputDialog.DATE_FORMATTER );
      LocalTime timeInBox = LocalTime.parse( systemUnderTest.timeField().getText(), UiDateTimeInputDialog.TIME_FORMATTER );
      LocalDateTime now = LocalDateTime.of( dateInBox, timeInBox );
      assertThat( now.isAfter( timestamp.minusMinutes( 5 ) ) && now.isBefore( timestamp.plusMinutes( 5 ) ), is( insideRange ) );
   }//End Method
   
   @Test public void shouldSetDateAndTimeToCurrent(){
      systemUnderTest.dateField().setText( "01-01-1900" );
      assertThatInputIsInRangeOf( LocalDateTime.now(), false );
      systemUnderTest.resetInputToNow();
      assertThatInputIsInRangeOf( LocalDateTime.now(), true );
   }//End Method
   
   @Test public void shouldShowValidDate(){
      assertThatBoxIsValid( systemUnderTest.dateField() );
      systemUnderTest.dateField().setText( "flipper" );
      assertThatBoxIsInvalid( systemUnderTest.dateField() );
      systemUnderTest.dateField().setText( "01-01-1900" );
      assertThatBoxIsValid( systemUnderTest.dateField() );
      systemUnderTest.dateField().setText( "32-01-1900" );
      assertThatBoxIsInvalid( systemUnderTest.dateField() );
   }//End Method
   
   private void assertThatBoxIsValid( TextField box ) {
      assertThat( box.getBorder().getStrokes().get( 0 ).getBottomStroke(), is( Color.GREEN ) );
      assertThat( box.getBorder().getStrokes().get( 0 ).getTopStroke(), is( Color.GREEN ) );
      assertThat( box.getBorder().getStrokes().get( 0 ).getRightStroke(), is( Color.GREEN ) );
      assertThat( box.getBorder().getStrokes().get( 0 ).getLeftStroke(), is( Color.GREEN ) );
   }//End Method
   
   private void assertThatBoxIsInvalid( TextField box ) {
      assertThat( box.getBorder().getStrokes().get( 0 ).getBottomStroke(), is( Color.RED ) );
      assertThat( box.getBorder().getStrokes().get( 0 ).getTopStroke(), is( Color.RED ) );
      assertThat( box.getBorder().getStrokes().get( 0 ).getRightStroke(), is( Color.RED ) );
      assertThat( box.getBorder().getStrokes().get( 0 ).getLeftStroke(), is( Color.RED ) );
   }//End Method
   
   @Test public void shouldShowValidTime(){
      assertThatBoxIsValid( systemUnderTest.timeField() );
      systemUnderTest.timeField().setText( "flipper" );
      assertThatBoxIsInvalid( systemUnderTest.timeField() );
      systemUnderTest.timeField().setText( "16:06:06" );
      assertThatBoxIsValid( systemUnderTest.timeField() );
      systemUnderTest.timeField().setText( "13:61:00" );
      assertThatBoxIsInvalid( systemUnderTest.timeField() );
   }//End Method
   
   @Test public void shouldConvertResult(){
      LocalDateTime now = LocalDateTime.now();
      now = now.minusNanos( now.getNano() );
      
      systemUnderTest.dateField().setText( UiDateTimeInputDialog.DATE_FORMATTER.format( now ) );
      systemUnderTest.timeField().setText( UiDateTimeInputDialog.TIME_FORMATTER.format( now ) );
      assertThat( systemUnderTest.getResultConverter().call( ButtonType.OK ), is( now ) );
   }//End Method

}//End Class
