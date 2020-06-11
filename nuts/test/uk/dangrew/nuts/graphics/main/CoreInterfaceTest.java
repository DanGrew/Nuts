package uk.dangrew.nuts.graphics.main;

import javafx.scene.layout.BorderPane;
import org.junit.Ignore;
import org.junit.Test;
import uk.dangrew.kode.launch.TestApplication;

public class CoreInterfaceTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> new BorderPane( new CoreInterface() ) );
      
      Thread.sleep( 900000000 );
   }//End Method

}//End Class
