package uk.dangrew.nuts.graphics.graph.custom;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.kode.launch.TestApplication;

public class GraphSeriesSynchronizerTest {

   private static final String NAME = "modelName";
   private GraphSeriesSynchronizer systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new GraphSeriesSynchronizer( NAME );
   }//End Method

   @Test public void shouldSynchronizeChartDataWhenShowing() {
      assertDataSetsMatch();
      
      LocalDateTime now = LocalDateTime.now();
      double value = 23874.3;
      systemUnderTest.update( now, value ); 
      
      assertDataSetsMatch();
   }//End Method
   
   @Test public void shouldNotSynchronizeChartDataWhenHiding() {
      assertDataSetsMatch();
      
      LocalDateTime now = LocalDateTime.now();
      double value = 23874.3;
      systemUnderTest.update( now, value ); 
      
      assertDataSetsMatch();
      
      systemUnderTest.hide();
      assertThat( systemUnderTest.dataSeries().getData(), is( not( empty() ) ) );
      assertThat( systemUnderTest.chartSeries().getData(), is( empty() ) );
      
      systemUnderTest.update( LocalDateTime.now().plusDays( 1 ), 100.0 );
      assertThat( systemUnderTest.dataSeries().getData(), hasSize( 2 ) );
      assertThat( systemUnderTest.chartSeries().getData(), is( empty() ) );
      
      systemUnderTest.show();
      assertDataSetsMatch();
      
      systemUnderTest.show();
      assertDataSetsMatch();
   }//End Method
   
   @Test public void shouldAddPoint(){
      LocalDateTime now = LocalDateTime.now();
      double value = 23874.3;
      
      systemUnderTest.update( now, value ); 
      assertThat( systemUnderTest.dataSeries().getData(), hasSize( 1 ) );
      assertThat( systemUnderTest.dataSeries().getData().get( 0 ).getXValue(), is( convertLocalDateTime( now ) ) );
      assertThat( systemUnderTest.dataSeries().getData().get( 0 ).getYValue(), is( value ) );
   }//End Method
   
   @Test public void shouldRemovePoint(){
      LocalDateTime now = LocalDateTime.now();
      double value = 23874.3;
      
      systemUnderTest.update( now, value ); 
      assertThat( systemUnderTest.dataSeries().getData(), hasSize( 1 ) );
      
      systemUnderTest.update( now, null );
      assertThat( systemUnderTest.dataSeries().getData(), hasSize( 0 ) );
   }//End Method
   
   @Test public void shouldUpdatePoint(){
      LocalDateTime now = LocalDateTime.now();
      double value = 23874.3;
      
      systemUnderTest.update( now, value ); 
      assertThat( systemUnderTest.dataSeries().getData().get( 0 ).getXValue(), is( convertLocalDateTime( now ) ) );
      assertThat( systemUnderTest.dataSeries().getData().get( 0 ).getYValue(), is( value ) );
      
      systemUnderTest.update( now, value = 43.223 );
      assertThat( systemUnderTest.dataSeries().getData().get( 0 ).getXValue(), is( convertLocalDateTime( now ) ) );
      assertThat( systemUnderTest.dataSeries().getData().get( 0 ).getYValue(), is( value ) );
   }//End Method
   
   @Test public void shouldProvideName(){
      assertThat( systemUnderTest.chartSeries().getName(), is( NAME ) );
      assertThat( systemUnderTest.dataSeries().getName(), is( NAME ) );
   }//End Method
   
   @Test public void shouldSortDataSeriesOnAdd(){
      systemUnderTest.update( LocalDateTime.now(), 100.0 );
      systemUnderTest.update( LocalDateTime.now().minusDays( 1 ), 150.0 );
      
      assertThat( systemUnderTest.dataSeries().getData().get( 0 ).getYValue(), is( 150.0 ) );
      assertThat( systemUnderTest.chartSeries().getData().get( 0 ).getYValue(), is( 150.0 ) );
      
      assertThat( systemUnderTest.dataSeries().getData().get( 1 ).getYValue(), is( 100.0 ) );
      assertThat( systemUnderTest.chartSeries().getData().get( 1 ).getYValue(), is( 100.0 ) );
   }//End Method
   
   private double convertLocalDateTime( LocalDateTime timestamp ) {
      return timestamp.toEpochSecond( ZoneOffset.UTC );
   }//End Method
   
   private void assertDataSetsMatch() {
      if ( systemUnderTest.chartSeries().getData().isEmpty() ) {
         assertThat( systemUnderTest.dataSeries().getData(), is( empty() ) );
         return;
      }
      assertThat( systemUnderTest.chartSeries().getData(), contains( systemUnderTest.dataSeries().getData().toArray() ) );
   }//End Method

}//End Class
