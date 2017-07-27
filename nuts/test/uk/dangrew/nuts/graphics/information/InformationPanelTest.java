package uk.dangrew.nuts.graphics.information;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.sd.graphics.launch.TestApplication;

public class InformationPanelTest {

   private InformationPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      systemUnderTest = new InformationPane();
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
