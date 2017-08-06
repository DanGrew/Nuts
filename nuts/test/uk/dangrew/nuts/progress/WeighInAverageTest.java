package uk.dangrew.nuts.progress;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.dangrew.kode.TestCommon.precision;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class WeighInAverageTest {

   private LocalDate date;
   
   private WeighIn first;
   private WeighIn second;
   private WeighIn third;
   
   private WeighInAverage systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      first = new WeighIn( LocalDate.now(), DayTime.Morning, 197.0, 18.7, 168.0 );
      second = new WeighIn( LocalDate.now(), DayTime.Morning, 201.0, 18.5, 168.5 );
      third = new WeighIn( LocalDate.now(), DayTime.Morning, 202.0, 18.0, 171.5 );
      
      systemUnderTest = new WeighInAverage( date, first, second, third );
   }//End Method

   @Test public void shouldProvideDate() {
      assertThat( systemUnderTest.date(), is( date ) );
   }//End Method
   
   @Test public void shouldProvideDayTime(){
      assertThat( systemUnderTest.dayTime(), is( DayTime.Period ) );
   }//End Method
   
   @Test public void shouldProvideAverageWeight(){
      assertThat( systemUnderTest.weight().get(), is( 200.0 ) );
   }//End Method
   
   @Test public void shouldProvideAverageBodyFat(){
      assertThat( systemUnderTest.bodyFat().get(), is( closeTo( 18.4, precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideAverageLeanMass(){
      assertThat( systemUnderTest.leanMass().get(), is( closeTo( 169.333, precision() ) ) );
   }//End Method
   
   @Test public void shouldRespondToWeighInWeightChanges(){
      first.weight().set( 100.0 );
      assertThat( systemUnderTest.weight().get(), is( closeTo( 167.666, precision() ) ) );
      first.weight().set( 250.0 );
      assertThat( systemUnderTest.weight().get(), is( closeTo( 217.666, precision() ) ) );
      first.weight().set( 1.0 );
      assertThat( systemUnderTest.weight().get(), is( closeTo( 134.666, precision() ) ) );
   }//End Method
   
   @Test public void shouldRespondToWeighInBodyFatChanges(){
      first.bodyFat().set( 100.0 );
      assertThat( systemUnderTest.bodyFat().get(), is( closeTo( 45.5, precision() ) ) );
      first.bodyFat().set( 250.0 );
      assertThat( systemUnderTest.bodyFat().get(), is( closeTo( 95.5, precision() ) ) );
      first.bodyFat().set( 1.0 );
      assertThat( systemUnderTest.bodyFat().get(), is( closeTo( 12.5, precision() ) ) );
   }//End Method
   
   @Test public void shouldRespondToWeighInLeanMassChanges(){
      first.leanMass().set( 100.0 );
      assertThat( systemUnderTest.leanMass().get(), is( closeTo( 146.666, precision() ) ) );
      first.leanMass().set( 250.0 );
      assertThat( systemUnderTest.leanMass().get(), is( closeTo( 196.666, precision() ) ) );
      first.leanMass().set( 1.0 );
      assertThat( systemUnderTest.leanMass().get(), is( closeTo( 113.666, precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideRecordingType(){
      assertThat( systemUnderTest.recordingType(), is( RecordingType.CalculatedAverage ) );
   }//End Method
   
   @Test public void shouldProvideWeighIns(){
      assertThat( systemUnderTest.weighIns(), is( Arrays.asList( first, second, third ) ) );
   }//End Method

}//End Class
