package uk.dangrew.nuts.graphics.main;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.store.Database;

public class NutsTabsTest {

   private Database database;
   @Mock private CoreInterfaceOperations operations;
   private NutsTabs systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      database.shoppingLists().createConcept( "anything" );
      database.stockLists().createConcept( "anything" );
      PlatformImpl.runAndWait( () -> systemUnderTest = new NutsTabs( database, operations ) );
   }//End Method

   @Test public void shouldProvideConcreteTabs() {
      assertThat( systemUnderTest.tabPane().getTabs(), hasSize( 13 ) );
      assertThat( systemUnderTest.getChildren().contains( systemUnderTest.tabPane() ), is( true ) );
   }//End Method
   
   @Test public void shouldProvideControls(){
      assertThat( systemUnderTest.getChildren().contains( systemUnderTest.controls() ), is( true ) );
   }//End Method
   
}//End Class
