package uk.dangrew.nuts.progress.weight;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.nuts.progress.weight.SystemDateRange;
import uk.dangrew.nuts.progress.weight.WeightProgress;
import uk.dangrew.nuts.progress.weight.WeightRecording;

public class WeightProgressTest {

   private WeightProgress systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new WeightProgress();
   }//End Method
   
   @Ignore
   @Test public void manual(){
      systemUnderTest.records().forEach( w -> {
         System.out.println( w.date().toString() ); 
      } );
   }//End Method 

   @Test public void shouldMorningAndEveningForEveryDaySince24thApril2017() {
      LocalDate now = LocalDate.now();
      LocalDate april = SystemDateRange.START_DATE;
      final LocalDate lastEntry = systemUnderTest.records().get( systemUnderTest.records().size() - 1 ).date();
      assertThat( 
               lastEntry.isEqual( now ) ||
               lastEntry.isAfter( now ), 
      is( true ) );
      assertThat( systemUnderTest.records().get( 0 ).date(), is( april ) );
   }//End Method
   
   @Test public void shouldProvideRunningAverageForEvery7Days(){
      for ( WeightRecording recording : systemUnderTest.records() ) {
         assertThatDayHasComponents( recording );
         assertThatDayHasCorrectRunningAverageRecords( recording, systemUnderTest.records() );
      }
   }//End Method
   
   /**
    * Assert that the {@link WeightRecording} has the correct components.
    * @param recording the {@link WeightRecording} to assert.
    */
   private void assertThatDayHasComponents( WeightRecording recording ){
      assertThat( recording.morningWeighIn(), is( notNullValue() ) );
      assertThat( recording.eveningWeighIn(), is( notNullValue() ) );
      assertThat( recording.runningAverage(), is( notNullValue() ) );
   }//End Method
   
   /**
    * Assert that the running average is constructed correctly.
    * @param recording the {@link WeightRecording} to assert.
    * @param allRecordings the {@link WeightRecording}s in the {@link WeightProgress}.
    */
   private void assertThatDayHasCorrectRunningAverageRecords( WeightRecording recording, List< WeightRecording > allRecordings ) {
      int indexOf = allRecordings.indexOf( recording );
      int from = Math.max( indexOf - 6, 0 );
      int to = indexOf + 1;
      List< WeightRecording > forAverage = allRecordings.subList( from, to );
      assertThat( recording.runningAverage().weighIns(), is( forAverage ) );
   }//End Method
   
}//End Class
