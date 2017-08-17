package uk.dangrew.nuts.graphics.graph;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.chart.NumberAxis;
import uk.dangrew.kode.launch.TestApplication;

public class WeightRecordingGraphControllerTest {

   private NumberAxis xAxis;
   private NumberAxis yAxis;
   private WeightRecordingGraphController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      xAxis = new NumberAxis();
      yAxis = new NumberAxis();
      systemUnderTest = new WeightRecordingGraphController(
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

}//End Class
