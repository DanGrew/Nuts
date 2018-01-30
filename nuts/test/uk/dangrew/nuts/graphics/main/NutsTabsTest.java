package uk.dangrew.nuts.graphics.main;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.event.structure.Event;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.store.Database;

public class NutsTabsTest {

   private Database database;
   private OpenTabEvent events;
   private NutsTabs systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      database.shoppingLists().createConcept( "anything" );
      
      events = new OpenTabEvent();
      events.clearAllSubscriptions();
      PlatformImpl.runAndWait( () -> systemUnderTest = new NutsTabs( database ) );
   }//End Method

   @Test public void shouldProvideConcreteTabs() {
      assertThat( systemUnderTest.getTabs(), hasSize( 9 ) );
   }//End Method
   
   @Test public void shouldRespondToEventAndDisplayClosableTab(){
      String title = "myTitle";
      Node node = new BorderPane();
      events.notify( new Event<>( new TabDefinition( title, node ) ) );
      
      assertThat( systemUnderTest.getTabs(), hasSize( 10 ) );
      Tab tab = systemUnderTest.getTabs().get( 9 );
      assertThat( tab.getContent(), is( node ) );
      assertThat( tab.getText(), is( title ) );
      assertThat( tab.isClosable(), is( true ) );
      assertThat( systemUnderTest.getSelectionModel().getSelectedItem(), is( tab ) );
   }//End Method

}//End Class
