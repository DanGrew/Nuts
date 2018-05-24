package uk.dangrew.nuts.graphics.tutorial.architecture;

import org.controlsfx.control.PopOver.ArrowLocation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.BuilderVerifier;

public class TutorMessageBuilderTest {

   private TutorMessageBuilder systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TutorMessageBuilder();
   }//End Method

   @Test public void shouldBuildProperties() {
      new BuilderVerifier<>()
         .build( systemUnderTest::highlighting, systemUnderTest::getHighlightSubject, new BorderPane() )
         .build( systemUnderTest::pointing, systemUnderTest::getArrowDirection, ArrowLocation.RIGHT_TOP )
         .build( systemUnderTest::withMessage, systemUnderTest::getMessage, new TextFlow() )
         .build( systemUnderTest::withRespectTo, systemUnderTest::getComponent, new BorderPane() );
   }//End Method

}//End Class
