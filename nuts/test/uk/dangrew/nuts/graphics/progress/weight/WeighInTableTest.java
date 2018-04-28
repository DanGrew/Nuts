package uk.dangrew.nuts.graphics.progress.weight;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.nuts.graphics.progress.weight.WeighInTable;
import uk.dangrew.nuts.progress.weight.WeightProgress;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class WeighInTableTest {

   private WeighInTable systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      systemUnderTest = new WeighInTable( new WeightProgress() );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 99999999 );
   }//End Method

}//End Class
