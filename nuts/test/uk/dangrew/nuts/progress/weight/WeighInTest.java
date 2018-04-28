package uk.dangrew.nuts.progress.weight;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.progress.weight.DayTime;
import uk.dangrew.nuts.progress.weight.RecordingType;
import uk.dangrew.nuts.progress.weight.WeighIn;

public class WeighInTest {

   private DayTime dayTime;
   private WeighIn systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      dayTime = DayTime.Evening;
      systemUnderTest = new WeighIn( dayTime );
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
   
   @Test public void shouldProvideDayTime() {
      assertThat( systemUnderTest.dayTime(), is( dayTime ) );
   }//End Method
   
   @Test public void shouldProvideRecordingType() {
      assertThat( systemUnderTest.recordingType(), is( RecordingType.WeighIn ) );
   }//End Method

}//End Class
