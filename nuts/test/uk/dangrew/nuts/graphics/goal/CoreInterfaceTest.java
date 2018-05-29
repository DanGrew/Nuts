package uk.dangrew.nuts.graphics.goal;

import org.junit.Ignore;
import org.junit.Test;

import javafx.scene.layout.BorderPane;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class CoreInterfaceTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> new BorderPane( new CoreInterface() ) );
      
      Thread.sleep( 900000000 );
   }//End Method

}//End Class
