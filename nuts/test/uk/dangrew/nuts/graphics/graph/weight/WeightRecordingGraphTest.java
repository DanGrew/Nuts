package uk.dangrew.nuts.graphics.graph.weight;

import static org.mockito.Mockito.mock;

import org.junit.Ignore;
import org.junit.Test;

import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.graph.custom.GraphBuilder;
import uk.dangrew.nuts.graphics.graph.custom.GraphController;
import uk.dangrew.nuts.graphics.graph.weight.WeightRecordingGraph;
import uk.dangrew.nuts.graphics.graph.weight.WeightRecordingGraphSettings;
import uk.dangrew.nuts.graphics.progress.weight.WeighInTable;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.store.Database;

public class WeightRecordingGraphTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.startPlatform();
      
      Database database = new Database();
      DataLocation.loadSampleWeightRecordings( database );
      
      WeightRecordingGraph graph = new WeightRecordingGraph( 
               database.weightProgress(),
               new GraphBuilder()
                  .withXAxisTitle( "Epoch Day" )
                  .withYAxisTitle( "Weight (lbs)" )
                  .withChartTitle( "Weight Recordings" )
      );
      BorderPane pane = new BorderPane( graph );
      pane.setTop( new WeighInTable( database.weightProgress() ) );
      pane.setRight( new WeightRecordingGraphSettings( graph.controller(), mock( GraphController.class ) ) );
      
      TestApplication.launch( () -> pane );
      
      Thread.sleep( 999999999 );
   }//End Method
   
   @Test public void untested() {
      System.out.println( "WARNING: UNTESTED - " + getClass().getName() );
   }//End Method

}//End Class
