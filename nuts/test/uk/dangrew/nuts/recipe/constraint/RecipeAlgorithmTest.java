package uk.dangrew.nuts.recipe.constraint;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class RecipeAlgorithmTest {

   private Object systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = null;
   }//End Method

   @Ignore
   @Test public void test() {
      fail( "Not yet implemented" );
   }//End Method

}//End Class
