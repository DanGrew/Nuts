package uk.dangrew.nuts.graphics.tutorial.architecture;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uk.dangrew.kode.launch.TestApplication;

public class TutorHighlightTest {

   private TutorHighlight systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TutorHighlight();
   }//End Method

   @Test public void shouldConfigure() {
      assertThat( systemUnderTest.getStrokeWidth(), is( TutorHighlight.STROKE_WIDTH ) );
      assertThat( systemUnderTest.getFill(), is( TutorHighlight.TRANSPARENT ) );
      assertThat( systemUnderTest.getStroke(), is( TutorHighlight.TRANSPARENT ) );
   }//End Method
   
   @Test public void shouldUpdatePosition() {
      systemUnderTest.focus( new Rectangle( 20, 25, 40, 50 ), Color.ANTIQUEWHITE );
      assertThat( systemUnderTest.getX(), is( 20.0 ) );
      assertThat( systemUnderTest.getY(), is( 25.0 ) );
      assertThat( systemUnderTest.getWidth(), is( 40.0 ) );
      assertThat( systemUnderTest.getHeight(), is( 50.0 ) );
   }//End Method
   
   @Test public void shouldBecomeOpaque() {
      systemUnderTest.focus( new BorderPane(), Color.ANTIQUEWHITE );
      assertThat( systemUnderTest.getStroke(), is( Color.ANTIQUEWHITE ) );
   }//End Method
   
   @Test public void shouldHide() {
      shouldBecomeOpaque();
      systemUnderTest.hide();
      assertThat( systemUnderTest.getStroke(), is( TutorHighlight.TRANSPARENT ) );
   }//End Method

}//End Class
