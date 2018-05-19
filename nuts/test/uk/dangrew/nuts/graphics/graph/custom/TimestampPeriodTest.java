package uk.dangrew.nuts.graphics.graph.custom;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;

public class TimestampPeriodTest {

   private static final LocalDateTime NOW = LocalDateTime.now();
   
   @Test public void shouldProvideUpperBound() {
      for ( TimestampPeriod period : TimestampPeriod.values() ) {
         assertThat( period.upperBound( NOW ), is( notNullValue() ) );
         assertThat( period.upperBound( NOW ).isAfter( NOW ), is( ( true ) ) );
      }
   }//End Method
   
   @Test public void shouldProvideLowerBound() {
      for ( TimestampPeriod period : TimestampPeriod.values() ) {
         assertThat( period.lowerBound( NOW ), is( notNullValue() ) );
         assertThat( period.lowerBound( NOW ).isBefore( NOW ), is( ( true ) ) );
      }
   }//End Method

}//End Class
