package uk.dangrew.nuts.graphics.graph.custom;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.graph.weight.WeightRecordingGraphModel;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.progress.weight.WeightProgress;

public class GraphControllerTest {

   private NumberAxis xAxis;
   private NumberAxis yAxis;
   private ProgressSeries progress1;
   private ProgressSeries progress2;
   private ObservableList< Series< Number, Number > > chartData;
   private GraphController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      progress1 = new ProgressSeries( "First" );
      progress2 = new ProgressSeries( "Second" );
      xAxis = new NumberAxis();
      yAxis = new NumberAxis();
      systemUnderTest = new GraphController(
               chartData = FXCollections.observableArrayList(),
               xAxis, yAxis
      );
   }//End Method
   
   @Test public void shouldStopAutoRangin(){
      assertThat( xAxis.isAutoRanging(), is( false ) );
      assertThat( yAxis.isAutoRanging(), is( false ) );
   }//End Method
   
   @Test public void shouldSetRecordingLowerBound() {
      systemUnderTest.setRecordingLowerBound( 45.6 );
      assertThat( yAxis.getLowerBound(), is( 45.6 ) );
   }//End Method
   
   @Test public void shouldSetRecordingUpperBound() {
      systemUnderTest.setRecordingUpperBound( 45.6 );
      assertThat( yAxis.getUpperBound(), is( 45.6 ) );
   }//End Method
   
   @Test public void shouldSetDateLowerBound() {
      systemUnderTest.setDateLowerBound( 45.6 );
      assertThat( xAxis.getLowerBound(), is( 45.6 ) );
   }//End Method
   
   @Test public void shouldSetDateUpperBound() {
      systemUnderTest.setDateUpperBound( 45.6 );
      assertThat( xAxis.getUpperBound(), is( 45.6 ) );
   }//End Method
   
   @Test public void shouldAddProgressToChart(){
      assertThat( chartData, is( empty() ) );
      systemUnderTest.seriesVisibility().add( progress1 );
      assertThat( chartData, is( not( empty() ) ) );
   }//End Method

}//End Class
