package uk.dangrew.nuts.progress.weight;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.progress.weight.DayTime;
import uk.dangrew.nuts.progress.weight.WeighIn;
import uk.dangrew.nuts.progress.weight.WeightRecording;

public class WeightRecordingTest {

   private LocalDate date;
   private WeighIn morning;
   private WeighIn evening;
   private List< WeightRecording > previousRecordings;
   private WeightRecording systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      date = LocalDate.now();
      previousRecordings = Arrays.asList( new WeightRecording( LocalDate.now(), new ArrayList<>() ), new WeightRecording( LocalDate.now(), new ArrayList<>() ) );
      systemUnderTest = new WeightRecording( 
               date, 
               morning = new WeighIn( DayTime.Evening ), 
               evening = new WeighIn( DayTime.Evening ), 
               previousRecordings 
      );
   }//End Method

   @Test public void shouldProvideDate() {
      assertThat( systemUnderTest.date(), is( date ) );
   }//End Method
   
   @Test public void shouldProvideMorningWeighIn() {
      assertThat( systemUnderTest.morningWeighIn(), is( morning ) );
   }//End Method
   
   @Test public void shouldProvideEveningWeighIn() {
      assertThat( systemUnderTest.eveningWeighIn(), is( evening ) );
   }//End Method
   
   @Test public void shouldProvideAverageWeighIn() {
      assertThat( systemUnderTest.runningAverage().weighIns(), contains( previousRecordings.get( 0 ), previousRecordings.get( 1 ), systemUnderTest ) );
   }//End Method

}//End Class
