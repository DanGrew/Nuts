package uk.dangrew.nuts.graphics.recipe.creator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;

public class UiRecipeCreatorPaneTest {

   private UiRecipeCreatorPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      PlatformImpl.runAndWait( () -> systemUnderTest = new UiRecipeCreatorPane() );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 9999999 );
   }//End Method

}//End Class
