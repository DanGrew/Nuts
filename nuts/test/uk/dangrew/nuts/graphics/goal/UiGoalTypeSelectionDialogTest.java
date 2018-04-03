package uk.dangrew.nuts.graphics.goal;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.goal.GoalTypes;

public class UiGoalTypeSelectionDialogTest {

   private UiGoalTypeSelectionDialog systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      PlatformImpl.runAndWait( () -> systemUnderTest = new UiGoalTypeSelectionDialog() );
   }//End Method

   @Test public void shouldContainOptions() {
      assertThat( systemUnderTest.getItems(), containsInAnyOrder( GoalTypes.values() ) );
   }//End Method

}//End Class
