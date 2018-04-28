package uk.dangrew.nuts.progress.weight;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.dangrew.kode.TestCommon.precision;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.progress.weight.DayTime;
import uk.dangrew.nuts.progress.weight.RecordingType;
import uk.dangrew.nuts.progress.weight.WeighIn;
import uk.dangrew.nuts.progress.weight.WeighInAverage;
import uk.dangrew.nuts.progress.weight.WeightRecording;

public class WeighInAverageTest {

   private WeightRecording first;
   private WeightRecording second;
   private WeightRecording third;
   
   private WeighInAverage systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      first = new WeightRecording(
               LocalDate.now(),
               new WeighIn( DayTime.Morning, 197.0, 18.7, 168.0 ),
               new WeighIn( DayTime.Evening, 198.0, 18.5, 169.0 ),
               new ArrayList<>()
      );
      second = new WeightRecording(
               LocalDate.now(),
               new WeighIn( DayTime.Morning, 201.0, 18.5, 168.5 ),
               new WeighIn( DayTime.Evening, 202.0, 18.2, 169.5 ),
               new ArrayList<>()
      );
      third = new WeightRecording(
               LocalDate.now(),
               new WeighIn( DayTime.Morning, 204.0, 18.0, 171.5 ),
               new WeighIn( DayTime.Evening, 202.0, 19.0, 170.5 ),
               new ArrayList<>()
      );
      
      systemUnderTest = new WeighInAverage( first, second, third );
   }//End Method

   @Test public void shouldProvideDayTime(){
      assertThat( systemUnderTest.dayTime(), is( DayTime.Period ) );
   }//End Method
   
   @Test public void shouldProvideAverageWeight(){
      assertThat( systemUnderTest.weight().get(), is( closeTo( 200.666, precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideAverageBodyFat(){
      assertThat( systemUnderTest.bodyFat().get(), is( closeTo( 18.483, precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideAverageLeanMass(){
      assertThat( systemUnderTest.leanMass().get(), is( closeTo( 169.5, precision() ) ) );
   }//End Method
   
   @Test public void shouldRespondToWeighInWeightChanges(){
      first.morningWeighIn().weight().set( 100.0 );
      assertThat( systemUnderTest.weight().get(), is( closeTo( 184.5, precision() ) ) );
      first.morningWeighIn().weight().set( 250.0 );
      assertThat( systemUnderTest.weight().get(), is( closeTo( 209.5, precision() ) ) );
      first.morningWeighIn().weight().set( 1.0 );
      assertThat( systemUnderTest.weight().get(), is( closeTo( 168, precision() ) ) );
      
      first.eveningWeighIn().weight().set( 200.0 );
      assertThat( systemUnderTest.weight().get(), is( closeTo( 168.333, precision() ) ) );
      first.eveningWeighIn().weight().set( 450.0 );
      assertThat( systemUnderTest.weight().get(), is( closeTo( 210.0, precision() ) ) );
      first.eveningWeighIn().weight().set( 3.0 );
      assertThat( systemUnderTest.weight().get(), is( closeTo( 135.5, precision() ) ) );
   }//End Method
   
   @Test public void shouldRespondToWeighInBodyFatChanges(){
      first.morningWeighIn().bodyFat().set( 100.0 );
      assertThat( systemUnderTest.bodyFat().get(), is( closeTo( 32.033, precision() ) ) );
      first.morningWeighIn().bodyFat().set( 250.0 );
      assertThat( systemUnderTest.bodyFat().get(), is( closeTo( 57.033, precision() ) ) );
      first.morningWeighIn().bodyFat().set( 1.0 );
      assertThat( systemUnderTest.bodyFat().get(), is( closeTo( 15.533, precision() ) ) );
      
      first.eveningWeighIn().bodyFat().set( 200.0 );
      assertThat( systemUnderTest.bodyFat().get(), is( closeTo( 45.783, precision() ) ) );
      first.eveningWeighIn().bodyFat().set( 450.0 );
      assertThat( systemUnderTest.bodyFat().get(), is( closeTo( 87.45, precision() ) ) );
      first.eveningWeighIn().bodyFat().set( 3.0 );
      assertThat( systemUnderTest.bodyFat().get(), is( closeTo( 12.95, precision() ) ) );
   }//End Method
   
   @Test public void shouldRespondToWeighInLeanMassChanges(){
      first.morningWeighIn().leanMass().set( 100.0 );
      assertThat( systemUnderTest.leanMass().get(), is( closeTo( 158.166, precision() ) ) );
      first.morningWeighIn().leanMass().set( 250.0 );
      assertThat( systemUnderTest.leanMass().get(), is( closeTo( 183.166, precision() ) ) );
      first.morningWeighIn().leanMass().set( 1.0 );
      assertThat( systemUnderTest.leanMass().get(), is( closeTo( 141.666, precision() ) ) );
      
      first.eveningWeighIn().leanMass().set( 200.0 );
      assertThat( systemUnderTest.leanMass().get(), is( closeTo( 146.833, precision() ) ) );
      first.eveningWeighIn().leanMass().set( 450.0 );
      assertThat( systemUnderTest.leanMass().get(), is( closeTo( 188.5, precision() ) ) );
      first.eveningWeighIn().leanMass().set( 3.0 );
      assertThat( systemUnderTest.leanMass().get(), is( closeTo( 114.0, precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideRecordingType(){
      assertThat( systemUnderTest.recordingType(), is( RecordingType.CalculatedAverage ) );
   }//End Method
   
   @Test public void shouldProvideWeighIns(){
      assertThat( systemUnderTest.weighIns(), is( Arrays.asList( first, second, third ) ) );
   }//End Method
   
   @Test public void shouldNotIgnoreEveningValueIfMorningNotPresent(){
      systemUnderTest = new WeighInAverage( new WeightRecording( 
               LocalDate.now(), 
               new WeighIn( DayTime.Morning, 10.0, 11.0, 12.0 ), 
               new WeighIn( DayTime.Evening ), 
               new ArrayList<>()
      ) );
      assertThat( systemUnderTest.weight().get(), is( 10.0 ) );
      assertThat( systemUnderTest.bodyFat().get(), is( 11.0 ) );
      assertThat( systemUnderTest.leanMass().get(), is( 12.0 ) );

      systemUnderTest = new WeighInAverage( new WeightRecording( 
               LocalDate.now(), 
               new WeighIn( DayTime.Morning ), 
               new WeighIn( DayTime.Evening, 10.0, 11.0, 12.0 ), 
               new ArrayList<>() 
      ) );
      assertThat( systemUnderTest.weight().get(), is( 10.0 ) );
      assertThat( systemUnderTest.bodyFat().get(), is( 11.0 ) );
      assertThat( systemUnderTest.leanMass().get(), is( 12.0 ) );
   }//End Method

}//End Class
