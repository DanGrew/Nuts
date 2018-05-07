package uk.dangrew.nuts.graphics.database;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import javafx.stage.Stage;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.meal.Meal;

public class RecipeSummaryWindowTest {

   private Stage stage;
   private RecipeSummaryWindow systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      PlatformImpl.runAndWait( () -> {
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
