package uk.dangrew.nuts.progress;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class WeightProgressTest {

   private WeightProgress systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new WeightProgress();
   }//End Method
   
   @Ignore
   @Test public void manual(){
      systemUnderTest.records().forEach( w -> {
         System.out.println( w.date().toString() + ": " + w.dayTime().name() + ", " + w.recordingType().name() ); 
      } );
   }//End Method 

   @Test public void shouldMorningAndEveningForEveryDaySince24thApril2017() {
      LocalDate now = LocalDate.now();
      LocalDate april = WeightProgress.START_DATE;
      final LocalDate lastEntry = systemUnderTest.records().get( systemUnderTest.records().size() - 1 ).date();
      assertThat( 
               lastEntry.isEqual( now ) ||
               lastEntry.isAfter( now ), 
      is( true ) );
      assertThat( systemUnderTest.records().get( 0 ).date(), is( april ) );
   }//End Method
   
   @Test public void shouldProvideAverageEvery7Days(){
      DayTime expected = DayTime.Morning;
      for ( WeightRecording recording : systemUnderTest.records() ) {
         if ( recording.dayTime() == DayTime.Period ) {
            continue;
         }
         assertThat( recording.dayTime(), is( expected ) );
         if ( expected == DayTime.Morning ) {
            expected = DayTime.Evening;
         } else {
            expected = DayTime.Morning;
         }
      }
   }//End Method
   
   @Test public void shouldProvideAverages(){
      int counter = 0;
      List< WeightRecording > weighIns = new ArrayList<>();
      for ( WeightRecording recording : systemUnderTest.records() ) {
         if ( counter == 14 ) {
            assertThat( recording, is( instanceOf( WeighInAverage.class ) ) );
            WeighInAverage average = ( WeighInAverage ) recording;
            assertThat( average.weighIns(), is( weighIns ) );
            counter = 0;
            weighIns.clear();
         } else {
            weighIns.add( recording );
            counter++;
         }
      }
   }//End Method
   
}//End Class
