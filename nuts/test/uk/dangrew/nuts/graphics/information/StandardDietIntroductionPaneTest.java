package uk.dangrew.nuts.graphics.information;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.dangrew.kode.launch.TestApplication;

public class StandardDietIntroductionPaneTest {

   private StandardDietIntroductionPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      systemUnderTest = new StandardDietIntroductionPane();
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 10000000 );
   }//End Method
   
   @Test public void untested() {
      System.out.println( "WARNING: UNTESTED - " + getClass().getName() );
   }//End Method

}//End Class
