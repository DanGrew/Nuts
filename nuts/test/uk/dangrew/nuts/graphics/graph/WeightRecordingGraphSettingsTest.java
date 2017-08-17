package uk.dangrew.nuts.graphics.graph;

import static org.mockito.Mockito.mock;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;

public class WeightRecordingGraphSettingsTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> new WeightRecordingGraphSettings( 
               mock( WeightRecordingGraphController.class ), 
               mock( WeightRecordingGraphController.class ) 
      ) );
      
      Thread.sleep( 999999999 );
   }//End Method
   
   @Test public void untested() {
      System.out.println( "WARNING: UNTESTED - " + getClass().getName() );
   }//End Method

}//End Class
