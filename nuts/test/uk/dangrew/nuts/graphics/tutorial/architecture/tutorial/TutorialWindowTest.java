package uk.dangrew.nuts.graphics.tutorial.architecture.tutorial;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.stage.Stage;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;

public class TutorialWindowTest {

   private Stage stage;
   private TutorialWindow systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );

      JavaFxThreading.runAndWait( () -> {
         stage = new Stage();
         systemUnderTest = new TutorialWindow( stage );
      } );
   }//End Method

   @Test public void shouldInitialise() {
      assertThat( stage.isAlwaysOnTop(), is( true ) );
      assertThat( stage.isShowing(), is( false ) );
   }//End Method
   
}//End Class
