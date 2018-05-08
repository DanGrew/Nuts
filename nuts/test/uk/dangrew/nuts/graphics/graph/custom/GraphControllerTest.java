package uk.dangrew.nuts.graphics.graph.custom;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import uk.dangrew.kode.datetime.DateTimeFormats;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class GraphControllerTest {

   private NumberAxis xAxis;
   private NumberAxis yAxis;
   private ProgressSeries progress1;
   private ProgressSeries progress2;
   private ObservableList< Series< Number, Number > > chartData;
   
   @Mock private DateTimeFormats formats;
   private GraphController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      TestApplication.startPlatform();
      
      progress1 = new ProgressSeries( "First" );
      progress2 = new ProgressSeries( "Second" );
      xAxis = new NumberAxis();
      yAxis = new NumberAxis();
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
      systemUnderTest.setRecordingLowerBound( 45.6 );
      assertThat( yAxis.getLowerBound(), is( 45.6 ) );
   }//End Method
   
   @Test public void shouldSetRecordingUpperBound() {
      systemUnderTest.setRecordingUpperBound( 45.6 );
      assertThat( yAxis.getUpperBound(), is( 45.6 ) );
   }//End Method
   
   @Test public void shouldSetDateLowerBound() {
      LocalDate now = LocalDate.now();
      when( formats.toDayBeginningEpochSeconds( now ) ).thenReturn( 45L );
      systemUnderTest.setDateLowerBound( now );
      assertThat( xAxis.getLowerBound(), is( 45.0 ) );
   }//End Method
   
   @Test public void shouldSetDateUpperBound() {
      LocalDate now = LocalDate.now();
      when( formats.toDayBeginningEpochSeconds( now ) ).thenReturn( 45L );
      systemUnderTest.setDateUpperBound( now );
      assertThat( xAxis.getUpperBound(), is( 45.0 ) );
   }//End Method
   
   @Test public void shouldAddProgressToChart(){
      assertThat( chartData, is( empty() ) );
      systemUnderTest.seriesVisibility().add( progress1 );
      assertThat( chartData, is( not( empty() ) ) );
   }//End Method
   
   @Test public void shouldAutoSetHorizontalBounds(){
      LocalDateTime min = LocalDateTime.now();
      LocalDateTime max = LocalDateTime.now().plusDays( 20 );
      when( formats.toDayBeginningEpochSeconds( min.toLocalDate() ) ).thenReturn( 45L );
      when( formats.toDayBeginningEpochSeconds( max.toLocalDate().plusDays( 1 ) ) ).thenReturn( 46L );
      
      progress1.record( min, 100.0 );
      progress1.record( min.plusDays( 5 ), 100.0 );
      progress2.record( max.minusDays( 1 ), 100.0 );
      progress2.record( max, 100.0 );
      
      systemUnderTest.seriesVisibility().show( progress1 );
      systemUnderTest.seriesVisibility().show( progress2 );
      systemUnderTest.autoScaleHorizontal();
      
      assertThat( xAxis.getLowerBound(), is( 45.0 ) );
      assertThat( xAxis.getUpperBound(), is( 46.0 ) );
      
      systemUnderTest.seriesVisibility().hide( progress1 );
      systemUnderTest.seriesVisibility().hide( progress2 );
      systemUnderTest.autoScaleHorizontal();
      
      assertThat( xAxis.getLowerBound(), is( 45.0 ) );
      assertThat( xAxis.getUpperBound(), is( 46.0 ) );
   }//End Method
   
   @Test public void shouldAutoSetVerticalBounds(){
      LocalDateTime min = LocalDateTime.now();
      LocalDateTime max = LocalDateTime.now().plusDays( 20 );
      when( formats.toDayBeginningEpochSeconds( min.toLocalDate() ) ).thenReturn( 45L );
      when( formats.toDayBeginningEpochSeconds( max.toLocalDate().plusDays( 1 ) ) ).thenReturn( 46L );
      
      progress1.record( min, 23.0 );
      progress1.record( min.plusDays( 5 ), 100.0 );
      progress2.record( max.minusDays( 1 ), 90.0 );
      progress2.record( max, 123.0 );
      
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

}//End Class
