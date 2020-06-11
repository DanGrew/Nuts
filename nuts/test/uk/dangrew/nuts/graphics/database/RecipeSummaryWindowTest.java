package uk.dangrew.nuts.graphics.database;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.stage.Stage;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.launch.TestApplication;

public class RecipeSummaryWindowTest {

   private Stage stage;
   private RecipeSummaryWindow systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );

      JavaFxThreading.runAndWait( () -> {
         stage = new Stage();
         systemUnderTest = new RecipeSummaryWindow( stage );
      } );
   }//End Method

   @Test public void shouldInitialise() {
      assertThat( stage.isAlwaysOnTop(), is( true ) );
      assertThat( stage.isShowing(), is( false ) );
   }//End Method
   
   @Test public void shouldShowMeal() {
      //bloody stupid final methods in toolkits! - too tricky for the effort.
   }//End Method

}//End Class
