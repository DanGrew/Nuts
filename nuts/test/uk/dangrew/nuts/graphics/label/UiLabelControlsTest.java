package uk.dangrew.nuts.graphics.label;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class UiLabelControlsTest {

   @Mock private UiLabelController controller;
   private UiLabelControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new UiLabelControls( controller );
   }//End Method

   @Test public void shouldRemoveFromLabel() {
      systemUnderTest.removeButton().fire();
      verify( controller ).removeFromLabel();
   }//End Method
   
   @Test public void shouldAddToLabel() {
      systemUnderTest.addButton().fire();
      verify( controller ).addToLabel();
   }//End Method

}//End Class
