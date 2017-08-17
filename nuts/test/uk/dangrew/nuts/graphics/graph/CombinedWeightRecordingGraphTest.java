package uk.dangrew.nuts.graphics.graph;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.progress.WeighInTable;
import uk.dangrew.nuts.manual.data.DataLocation;
import uk.dangrew.nuts.store.Database;

public class CombinedWeightRecordingGraphTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.startPlatform();
      
      Database database = new Database();
      DataLocation.loadSampleWeightRecordings( database );
      
      CombinedWeightRecordingGraph graph = new CombinedWeightRecordingGraph( database.weightProgress() );
      
      BorderPane pane = new BorderPane( graph );
      pane.setTop( new WeighInTable( database.weightProgress() ) );
      
      TestApplication.launch( () -> pane );
      
      Thread.sleep( 999999999 );
   }//End Method
   
   @Test public void untested() {
      System.out.println( "WARNING: UNTESTED - " + getClass().getName() );
   }//End Method
   
   @Test public void shouldHaveStyleSheetsForGraphs(){
      assertThat( CombinedWeightRecordingGraph.PRIMARY_CHART_STYLE_SHEET, is( notNullValue() ));
      assertThat( CombinedWeightRecordingGraph.SECONDARY_CHART_STYLE_SHEET, is( notNullValue() ));
   }//End Method

}//End Class
