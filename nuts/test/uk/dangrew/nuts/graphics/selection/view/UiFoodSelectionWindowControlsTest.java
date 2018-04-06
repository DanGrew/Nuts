package uk.dangrew.nuts.graphics.selection.view;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.selection.view.FoodSelectionWindowStageControls;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionController;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionWindowControls;

public class UiFoodSelectionWindowControlsTest {

   @Mock private UiFoodSelectionController selectionController;
   @Mock private FoodSelectionWindowStageControls stageControls;
   private UiFoodSelectionWindowControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new UiFoodSelectionWindowControls( selectionController, stageControls );
   }//End Method

   @Test public void shouldApplySelection() {
      Set< FoodPortion > selection = new LinkedHashSet<>();
      when( selectionController.getAndClearSelection() ).thenReturn( selection );
      
      systemUnderTest.applyButton().fire();
      verify( stageControls ).apply( selection );
   }//End Method
   
   @Test public void shouldCancel() {
      systemUnderTest.cancelButton().fire();
      verify( stageControls ).cancel();
   }//End Method

}//End Class
