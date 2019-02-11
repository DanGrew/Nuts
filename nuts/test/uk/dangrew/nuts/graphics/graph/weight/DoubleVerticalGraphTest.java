package uk.dangrew.nuts.graphics.graph.weight;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import uk.dangrew.kode.launch.TestApplication;

public class DoubleVerticalGraphTest {

   private DoubleVerticalGraph systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new DoubleVerticalGraph( "Title", "xAxis", "innerY", "outerY" );
   }//End Method

   @Test public void shouldContainComponents() {
      assertThat( ( ( StackPane )systemUnderTest.getCenter() ).getChildren().get( 0 ), is( systemUnderTest.graph1() ) );
      assertThat( ( ( StackPane )systemUnderTest.getCenter() ).getChildren().get( 1 ), is( systemUnderTest.graph2() ) );
      
      assertThat( ( ( BorderPane )systemUnderTest.getRight() ).getTop(), is( systemUnderTest.graphSettings1() ) );
      assertThat( ( ( BorderPane )systemUnderTest.getRight() ).getBottom(), is( systemUnderTest.graphSettings2() ) );
   }//End Method

}//End Class
