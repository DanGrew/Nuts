package uk.dangrew.nuts.graphics.graph.custom;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Series;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class GraphSeriesVisibilityTest {

   private ProgressSeries progress;
   
   private ObservableList< Series< Number, Number > > chartData;
   private GraphSeriesVisibility systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      progress = new ProgressSeries( "anything" );
      
      chartData = FXCollections.observableArrayList();
      systemUnderTest = new GraphSeriesVisibility( chartData );
   }//End Method

   @Test public void shouldProvideSeries() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideObject( GraphSeriesVisibility::availableSeries )
         .shouldProvideObject( GraphSeriesVisibility::visibleSeries );
   }//End Method
   
   @Test public void shouldAddSeriesToChart(){
      systemUnderTest.add( progress );
      assertThat( systemUnderTest.dataFor( progress ), is( notNullValue() ) );
      assertThat( chartData.contains( systemUnderTest.dataFor( progress ) ), is( true ) );
   }//End Method
   
   @Test public void shouldNotAddSeriesToChartMultipleTimes(){
      systemUnderTest.add( progress );
      systemUnderTest.add( progress );
      assertThat( chartData, hasSize( 1 ) );
   }//End Method
   
   @Test public void shouldChangeVisibilityOfSeries() {
      systemUnderTest.add( progress );
      assertThat( chartData.contains( systemUnderTest.dataFor( progress ) ), is( true ) );
      assertThat( systemUnderTest.visibleSeries().contains( progress ), is( true ) );
      systemUnderTest.hide( progress );
      assertThat( chartData.contains( systemUnderTest.dataFor( progress ) ), is( false ) );
      assertThat( systemUnderTest.visibleSeries().contains( progress ), is( false ) );
      systemUnderTest.show( progress );
      assertThat( chartData.contains( systemUnderTest.dataFor( progress ) ), is( true ) );
      assertThat( systemUnderTest.visibleSeries().contains( progress ), is( true ) );
   }//End Method
   
   @Test public void shouldAddThenShow(){
      systemUnderTest.show( progress );
      assertThat( chartData.contains( systemUnderTest.dataFor( progress ) ), is( true ) );
      assertThat( systemUnderTest.visibleSeries().contains( progress ), is( true ) );
   }//End Method
   
}//End Class
