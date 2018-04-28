package uk.dangrew.nuts.graphics.graph.weight;

import static org.mockito.Mockito.mock;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.graph.custom.GraphController;
import uk.dangrew.nuts.graphics.graph.weight.WeightRecordingGraphSettings;

public class WeightRecordingGraphSettingsTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> new WeightRecordingGraphSettings( 
               mock( GraphController.class ), 
               mock( GraphController.class ) 
      ) );
      
      Thread.sleep( 999999999 );
   }//End Method
   
   @Test public void untested() {
      System.out.println( "WARNING: UNTESTED - " + getClass().getName() );
   }//End Method

}//End Class
