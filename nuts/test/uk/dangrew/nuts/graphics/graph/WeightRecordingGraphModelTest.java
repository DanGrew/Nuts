package uk.dangrew.nuts.graphics.graph;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import uk.dangrew.nuts.progress.WeightProgress;
import uk.dangrew.nuts.progress.WeightRecording;

public class WeightRecordingGraphModelTest {

   private ObservableList< Data< Number, Number > > series;
   private WeightProgress progress;
   private Function< WeightRecording, Double > dateRetriever;
   private Function< WeightRecording, ObjectProperty< Double > > propertyRetriever;
   private WeightRecordingGraphModel systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      series = FXCollections.observableArrayList();
      progress = new WeightProgress();
      
      dateRetriever = r -> r.date().toEpochDay() + 0.0;
      propertyRetriever = r -> r.morningWeighIn().bodyFat();
      systemUnderTest = new WeightRecordingGraphModel(
               progress, 
               dateRetriever, 
               propertyRetriever, 
               series 
      );
   }//End Method

   @Test public void shouldProvideDataForEachDate() {
      for ( WeightRecording record : progress.records() ) {
         assertThat( systemUnderTest.dataFor( record ), is( notNullValue() ) );
         assertThat( systemUnderTest.dataFor( record ).getXValue(), is( record.date().toEpochDay() + 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldRemoveDataWhenSetToNullOrZero() {
      WeightRecording first = progress.records().get( 20 );
      first.morningWeighIn().bodyFat().set( 100.0 );
      WeightRecording second = progress.records().get( 10 );
      second.morningWeighIn().bodyFat().set( 101.0 );
      
      assertThat( series.get( 0 ), is( systemUnderTest.dataFor( second ) ) );
      assertThat( series.get( 1 ), is( systemUnderTest.dataFor( first ) ) );
      
      second.morningWeighIn().bodyFat().set( null );
      assertThat( series.get( 0 ), is( systemUnderTest.dataFor( first ) ) );
      
      first.morningWeighIn().bodyFat().set( 0.0 );
      assertThat( series, is( empty() ) );
   }//End Method
   
   @Test public void shouldAddDataWhenSetToNonNullNonZero() {
      assertThat( series, is( empty() ) );
      
      WeightRecording record = progress.records().get( 20 );
      record.morningWeighIn().bodyFat().set( 100.0 );
      
      assertThat( series, contains( systemUnderTest.dataFor( record ) ) );
      assertThat( systemUnderTest.dataFor( record ).getYValue(), is( 100.0 ) );
   }//End Method
   
   @Test public void shouldSortDataWhenAdded() {
      WeightRecording first = progress.records().get( 20 );
      first.morningWeighIn().bodyFat().set( 100.0 );
      WeightRecording second = progress.records().get( 10 );
      second.morningWeighIn().bodyFat().set( 101.0 );
      
      assertThat( series.get( 0 ), is( systemUnderTest.dataFor( second ) ) );
      assertThat( series.get( 1 ), is( systemUnderTest.dataFor( first ) ) );
   }//End Method

}//End Class
