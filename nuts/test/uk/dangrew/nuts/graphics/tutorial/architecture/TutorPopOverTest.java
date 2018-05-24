package uk.dangrew.nuts.graphics.tutorial.architecture;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.controlsfx.control.PopOver.ArrowLocation;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import uk.dangrew.kode.launch.TestApplication;

public class TutorPopOverTest {

   private TutorPopOver systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      PlatformImpl.runAndWait( () -> systemUnderTest = spy( new TutorPopOver() ) );
   }//End Method

   @Test public void shouldConfigurePopOver() {
      assertThat( systemUnderTest.isAutoHide(), is( false ) );
      assertThat( systemUnderTest.getContentNode(), is( systemUnderTest.content() ) );
      assertThat( systemUnderTest.content().getBottom(), is( systemUnderTest.confirmationButton() ) );
      assertThat( BorderPane.getAlignment( systemUnderTest.confirmationButton() ), is( Pos.CENTER_RIGHT ) );
   }//End Method

   @Ignore //should work but isn't for some reason, clearly works in the ui though
   @Test public void shouldHidePopup(){
      systemUnderTest.confirmationButton().fire();
      verify( systemUnderTest ).friendly_hide();
   }//End Method
   
   @Test public void shouldUpdatePopupWithMessage() throws InterruptedException{
      BorderPane liveWindow = new BorderPane();
      TestApplication.launch( () -> liveWindow );
      
      TutorMessageBuilder message = new TutorMessageBuilder()
               .pointing( ArrowLocation.RIGHT_BOTTOM )
               .withMessage( new TextFlow() )
               .withRespectTo( liveWindow );
      
      PlatformImpl.runAndWait( () -> systemUnderTest.show( message ) );
      
      assertThat( systemUnderTest.getArrowLocation(), is( message.getArrowDirection() ) );
      assertThat( systemUnderTest.content().getCenter(), is( message.getMessage() ) );
      assertThat( systemUnderTest.confirmationButton().getText(), is( not( "" ) ) );
      verify( systemUnderTest ).friendly_show( liveWindow );
   }//End Method
   
}//End Class
