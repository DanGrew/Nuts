package uk.dangrew.nuts.graphics.progress.weight;

import javafx.scene.layout.BorderPane;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.progress.weight.WeightProgress;

public class WeighInTableTest {

   private WeighInTable systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      systemUnderTest = new WeighInTable( new WeightProgress() );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> new BorderPane(systemUnderTest) );
      
      Thread.sleep( 99999999 );
   }//End Method

}//End Class
