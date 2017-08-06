package uk.dangrew.nuts.progress;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class WeighInTest {

   private LocalDate date;
   private DayTime dayTime;
   private WeighIn systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      date = LocalDate.now();
      dayTime = DayTime.Evening;
      systemUnderTest = new WeighIn( date, dayTime );
   }//End Method

   @Test public void shouldProvideWeight() {
      assertThat( systemUnderTest.weight(), is( notNullValue() ) );
      assertThat( systemUnderTest.weight().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideBodyFat() {
      assertThat( systemUnderTest.bodyFat(), is( notNullValue() ) );
      assertThat( systemUnderTest.bodyFat().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideLeanMass() {
      assertThat( systemUnderTest.leanMass(), is( notNullValue() ) );
      assertThat( systemUnderTest.leanMass().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideDate() {
      assertThat( systemUnderTest.date(), is( date ) );
   }//End Method
   
   @Test public void shouldProvideDayTime() {
      assertThat( systemUnderTest.dayTime(), is( dayTime ) );
   }//End Method
   
   @Test public void shouldProvideRecordingType() {
      assertThat( systemUnderTest.recordingType(), is( RecordingType.WeighIn ) );
   }//End Method

}//End Class
