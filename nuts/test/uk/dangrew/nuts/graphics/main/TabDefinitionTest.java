package uk.dangrew.nuts.graphics.main;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.launch.TestApplication;

public class TabDefinitionTest {

   private String title;
   private Node node;
   private TabDefinition systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      title = "Title";
      node = new BorderPane();
      systemUnderTest = new TabDefinition( title, node );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.node(), is( node ) );
      assertThat( systemUnderTest.title(), is( title ) );
   }//End Method

}//End Class
