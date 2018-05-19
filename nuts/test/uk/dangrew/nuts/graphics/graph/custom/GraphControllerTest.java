package uk.dangrew.nuts.graphics.graph.custom;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import uk.dangrew.kode.datetime.TimestampFormat;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class GraphControllerTest {
   
   private static final LocalDateTime NOW = LocalDateTime.of( 2018, 3, 6, 14, 45 );

   private NumberAxis xAxis;
   private NumberAxis yAxis;
   private ProgressSeries progress1;
   private ProgressSeries progress2;
   private ObservableList< Series< Number, Number > > chartData;
   
   private TimestampFormat formats;
   private GraphController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      TestApplication.startPlatform();
      
      progress1 = new ProgressSeries( "First" );
      progress2 = new ProgressSeries( "Second" );
      xAxis = new NumberAxis();
      yAxis = new NumberAxis();
      formats = new TimestampFormat();
      systemUnderTest = new GraphController(
               formats, 
               chartData = FXCollections.observableArrayList(),
               xAxis, yAxis
      );
   }//End Method
   
   @Test public void shouldStopAutoRangin(){
      assertThat( xAxis.isAutoRanging(), is( false ) );
      assertThat( yAxis.isAutoRanging(), is( false ) );
   }//End Method
   
   @Test public void shouldSetRecordingLowerBound() {
      systemUnderTest.yAxisLowerBoundProperty().set( 45.6 );
      assertThat( yAxis.getLowerBound(), is( 45.6 ) );
   }//End Method
   
   @Test public void shouldSetRecordingUpperBound() {
      systemUnderTest.yAxisUpperBoundProperty().set( 45.6 );
      assertThat( yAxis.getUpperBound(), is( 45.6 ) );
   }//End Method
   
   @Test public void shouldSetDateLowerBound() {
      systemUnderTest.xAxisLowerBoundProperty().set( NOW );
      assertThat( xAxis.getLowerBound(), is( ( double )formats.toEpochSeconds( NOW ) ) );
   }//End Method
   
   @Test public void shouldSetDateUpperBound() {
      systemUnderTest.xAxisUpperBoundProperty().set( NOW );
      assertThat( xAxis.getUpperBound(), is( ( double )formats.toEpochSeconds( NOW ) ) );
   }//End Method
   
   @Test public void shouldAddProgressToChart(){
      assertThat( chartData, is( empty() ) );
      systemUnderTest.seriesVisibility().add( progress1 );
      assertThat( chartData, is( not( empty() ) ) );
   }//End Method
   
   @Test public void shouldAutoSetHorizontalBounds(){
      LocalDateTime min = NOW;
      LocalDateTime max = NOW.plusDays( 100 );
      
      progress1.values().record( min, 100.0 );
      progress1.values().record( min.plusDays( 5 ), 100.0 );
      progress1.notes().record( min.plusDays( 18 ), "anything" );
      progress2.values().record( max.minusDays( 1 ), 100.0 );
      progress2.values().record( max, 100.0 );
      
      systemUnderTest.seriesVisibility().show( progress1 );
      systemUnderTest.seriesVisibility().show( progress2 );
      systemUnderTest.autoScaleHorizontal();
      
      assertThat( xAxis.getLowerBound(), is( ( double )formats.toEpochSeconds( min ) ) );
      assertThat( xAxis.getUpperBound(), is( ( double )formats.toEpochSeconds( max ) ) );
      
      systemUnderTest.seriesVisibility().hide( progress1 );
      systemUnderTest.seriesVisibility().hide( progress2 );
      systemUnderTest.autoScaleHorizontal();
      
      assertThat( xAxis.getLowerBound(), is( ( double )formats.toEpochSeconds( min ) ) );
      assertThat( xAxis.getUpperBound(), is( ( double )formats.toEpochSeconds( max ) ) );
   }//End Method
   
   @Test public void shouldAutoSetVerticalBounds(){
      LocalDateTime min = NOW;
      LocalDateTime max = NOW.plusDays( 20 );
      
      progress1.values().record( min, 23.0 );
      progress1.values().record( min.plusDays( 5 ), 100.0 );
      progress1.notes().record( min.plusDays( 100 ), "anything" );
      progress2.values().record( max.minusDays( 1 ), 90.0 );
      progress2.values().record( max, 123.0 );
      
      systemUnderTest.seriesVisibility().show( progress1 );
      systemUnderTest.seriesVisibility().show( progress2 );
      systemUnderTest.autoScaleVertical();
      
      assertThat( yAxis.getLowerBound(), is( 23.0 ) );
      assertThat( yAxis.getUpperBound(), is( 123.0 ) );
      
      systemUnderTest.seriesVisibility().hide( progress1 );
      systemUnderTest.seriesVisibility().hide( progress2 );
      systemUnderTest.autoScaleVertical();
      
      assertThat( yAxis.getLowerBound(), is( 23.0 ) );
      assertThat( yAxis.getUpperBound(), is( 123.0 ) );
   }//End Method
   
   @Test public void shouldFocusOnTimestamp(){
      LocalDateTime now = NOW;
      systemUnderTest.focusHorizontalAxisOn( now, TimestampPeriod.SixMonths );
      assertThat( xAxis.getLowerBound(), is( ( double )formats.toEpochSeconds( TimestampPeriod.SixMonths.lowerBound( NOW ) ) ) );
      assertThat( xAxis.getUpperBound(), is( ( double )formats.toEpochSeconds( TimestampPeriod.SixMonths.upperBound( NOW ) ) ) );
   }//End Method
   
}//End Class
