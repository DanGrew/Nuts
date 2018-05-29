package uk.dangrew.nuts.graphics.tutorial.architecture.tutor;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.controlsfx.control.PopOver.ArrowLocation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.BuilderVerifier;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorMessageBuilder;

public class TutorMessageBuilderTest {

   private TutorMessageBuilder systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TutorMessageBuilder();
   }//End Method

   @Test public void shouldBuildProperties() {
      new BuilderVerifier<>()
         .buildObject( systemUnderTest::highlighting, systemUnderTest::getHighlightSubject, new BorderPane() )
         .buildObject( systemUnderTest::pointing, systemUnderTest::getArrowDirection, ArrowLocation.RIGHT_TOP )
         .buildObject( systemUnderTest::withMessage, systemUnderTest::getMessage, new TextFlow() )
         .buildObject( systemUnderTest::withRespectTo, systemUnderTest::getComponent, new BorderPane() )
         .buildWithOptional( systemUnderTest::callingBackTo, systemUnderTest::callback, () -> {} )
         .buildBoolean( systemUnderTest::withConfirmation, systemUnderTest::shouldHaveConfirmation, true )
         .buildObject( systemUnderTest::withHighlight, systemUnderTest::getHighlightColour, Color.BEIGE );
   }//End Method
   
   @Test public void shouldProvideDefaults(){
      assertThat( systemUnderTest.getHighlightColour(), is( Color.RED ) );
      assertThat( systemUnderTest.getArrowDirection(), is( ArrowLocation.LEFT_CENTER ) );
   }//End Method
   
   @Test public void shouldFocusOn(){
      Node node = new BorderPane();
      systemUnderTest.focussingOn( node );
      assertThat( systemUnderTest.getComponent(), is( node ) );
      assertThat( systemUnderTest.getHighlightSubject(), is( node ) );
   }//End Method

}//End Class
