package uk.dangrew.nuts.graphics.tools.dryweight;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.sd.graphics.launch.TestApplication;

public class DryWeightToolPaneTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> new DryWeightToolPane() );
      Thread.sleep( 10000000 );
   }//End Method

}//End Class
