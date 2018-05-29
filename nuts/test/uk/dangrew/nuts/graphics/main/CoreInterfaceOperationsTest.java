package uk.dangrew.nuts.graphics.main;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.persistence.fooditems.FoodSessions;

public class CoreInterfaceOperationsTest {

   @Mock private FoodSessions sessions;
   private CoreInterfaceOperations systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      systemUnderTest = new CoreInterfaceOperations( sessions );
   }//End Method

   @Test public void shouldReadAutomatically() {
      verify( sessions ).read();
   }//End Method
   
   @Test public void shouldSaveOnCommand() {
      systemUnderTest.save();
      verify( sessions ).write();
   }//End Method

}//End Class
