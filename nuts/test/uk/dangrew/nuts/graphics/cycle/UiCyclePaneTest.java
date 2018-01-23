package uk.dangrew.nuts.graphics.cycle;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;

public class UiCyclePaneTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> new UiCyclePane() );
      
      Thread.sleep( 9999999 );
   }//End Method

}//End Class
