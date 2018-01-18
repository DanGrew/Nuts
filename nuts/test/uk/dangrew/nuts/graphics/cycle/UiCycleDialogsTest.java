package uk.dangrew.nuts.graphics.cycle;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.cycle.UiCycleDialogs.NoBaseGoalDialog;
import uk.dangrew.nuts.graphics.cycle.UiCycleDialogs.NoFocusDialog;

public class UiCycleDialogsTest {

   @Mock private NoBaseGoalDialog noBaseGoalDialog;
   @Mock private NoFocusDialog noFocusDialog;
   private UiCycleDialogs systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      systemUnderTest = new UiCycleDialogs( noBaseGoalDialog, noFocusDialog );
   }//End Method

   @Test public void shouldShowAndWaitNoBaseGoalDialog() {
      systemUnderTest.showNoBaseGoalDialog();
      verify( noBaseGoalDialog ).friendly_showAndWait();
   }//End Method
   
   @Test public void shouldShowAndWaitNoFocusDialog() {
      systemUnderTest.showNoFocusDialog();
      verify( noFocusDialog ).friendly_showAndWait();
   }//End Method

}//End Class
