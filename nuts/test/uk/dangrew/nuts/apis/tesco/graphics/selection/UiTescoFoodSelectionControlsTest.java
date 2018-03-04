package uk.dangrew.nuts.apis.tesco.graphics.selection;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.event.ActionEvent;
import uk.dangrew.kode.launch.TestApplication;

public class UiTescoFoodSelectionControlsTest {

   @Mock private UiTescoPortionOptionsController controller;
   private UiTescoFoodSelectionControls systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new UiTescoFoodSelectionControls( controller );
   }//End Method

   @Test public void shouldSearchWhenButtonPressed() {
      systemUnderTest.searchTextField().setText( "search-criteria" );
      systemUnderTest.searchButton().getOnAction().handle( new ActionEvent() );
      verify( controller ).search( "search-criteria" );
   }//End Method

}//End Class
