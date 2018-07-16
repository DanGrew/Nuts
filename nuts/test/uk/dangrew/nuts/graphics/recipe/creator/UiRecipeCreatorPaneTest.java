package uk.dangrew.nuts.graphics.recipe.creator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.selection.view.FoodSelectionWindow;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class UiRecipeCreatorPaneTest {

   private Database database;
   private UiRecipeCreatorPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      DataLocation.loadSampleFoodData( database );
      PlatformImpl.runAndWait( () -> systemUnderTest = new UiRecipeCreatorPane( database, new Meal( "Recipe" ) ) );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      PlatformImpl.runAndWait( () -> new FoodSelectionWindow( database ) );
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 9999999 );
   }//End Method

}//End Class
