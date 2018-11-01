package uk.dangrew.nuts.graphics.common;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class UiWindowControlsTest {

   @Mock private UiWindowControlsActions actions;
   private UiWindowControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new UiWindowControls( actions );
   }//End Method

   @Test public void shouldApplySelection() {
      systemUnderTest.applyButton().fire();
      verify( actions ).apply();
   }//End Method
   
   @Test public void shouldCancel() {
      systemUnderTest.cancelButton().fire();
      verify( actions ).cancel();
   }//End Method

}//End Class
