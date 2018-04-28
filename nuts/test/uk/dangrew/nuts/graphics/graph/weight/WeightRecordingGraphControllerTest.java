package uk.dangrew.nuts.graphics.graph.weight;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.scene.chart.NumberAxis;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.graph.weight.WeightRecordingGraphController;
import uk.dangrew.nuts.graphics.graph.weight.WeightRecordingGraphModel;
import uk.dangrew.nuts.progress.weight.WeightProgress;
import uk.dangrew.nuts.progress.weight.WeightRecording;

public class WeightRecordingGraphControllerTest {

   private NumberAxis xAxis;
   private NumberAxis yAxis;
   private WeightRecordingGraphModel model1;
   private WeightRecordingGraphModel model2;
   private WeightRecordingGraphController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      xAxis = new NumberAxis();
      yAxis = new NumberAxis();
      model1 = spy( new WeightRecordingGraphModel( 
               "Series1",
               new WeightProgress(), 
               r -> r.date().toEpochDay() + 0.0, 
               r -> r.morningWeighIn().bodyFat(), 
               FXCollections.observableArrayList() 
      ) );
      model2 = spy( new WeightRecordingGraphModel(
               "Series2",
               new WeightProgress(), 
               r -> r.date().toEpochDay() + 0.0, 
               r -> r.morningWeighIn().bodyFat(), 
               FXCollections.observableArrayList() 
      ) );
      systemUnderTest = new WeightRecordingGraphController(
               xAxis, yAxis, model1, model2
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
   
   @Test public void shouldProvideSeriesNames(){
      assertThat( systemUnderTest.seriesByName(), is( Arrays.asList( model1.modelName(), model2.modelName() ) ) );
   }//End Method
   
   @Test public void shouldTurnSeriesOnOffByName(){
      systemUnderTest.enableSeries( model2.modelName(), true );
      verify( model2 ).show();
      systemUnderTest.enableSeries( model2.modelName(), false );
      verify( model2 ).hide();
      systemUnderTest.enableSeries( model2.modelName(), true );
      verify( model2, times( 2 ) ).show();
   }//End Method
   
   @Test public void shouldIgnoreInvalidModelName(){
      systemUnderTest.enableSeries( "anything", true );
   }//End Method

}//End Class
