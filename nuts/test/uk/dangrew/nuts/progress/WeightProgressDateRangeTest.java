package uk.dangrew.nuts.progress;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class WeightProgressDateRangeTest {

   private WeightProgressDateRange systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new WeightProgressDateRange();
   }//End Method

   @Test public void shouldMorningAndEveningForEveryDaySince24thApril2017() {
      LocalDate now = LocalDate.now();
      LocalDate april = WeightProgressDateRange.START_DATE;
      final LocalDate lastEntry = systemUnderTest.get().get( systemUnderTest.get().size() - 1 );
      assertThat( 
               lastEntry.isEqual( now ) ||
               lastEntry.isAfter( now ), 
      is( true ) );
      assertThat( systemUnderTest.get().get( 0 ), is( april ) );
   }//End Method

}//End Class
