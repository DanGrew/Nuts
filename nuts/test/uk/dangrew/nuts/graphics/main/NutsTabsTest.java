package uk.dangrew.nuts.graphics.main;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.store.Database;

public class NutsTabsTest {

   private Database database;
   private NutsTabs systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      database.shoppingLists().createConcept( "anything" );
      PlatformImpl.runAndWait( () -> systemUnderTest = new NutsTabs( database ) );
   }//End Method

   @Test public void shouldProvideConcreteTabs() {
      assertThat( systemUnderTest.getTabs(), hasSize( 9 ) );
   }//End Method
   
}//End Class
