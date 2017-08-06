package uk.dangrew.nuts.progress;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Test;

public class DayTimeTest {

   @Test public void shouldProvideInformation() {
      assertThat( 
               DayTime.Morning.disaplyInformationFor( LocalDate.of( 2017, 8, 29 ) ), 
               is( "Tue, Aug 29 2017: Morning" ) 
      );
      assertThat( 
               DayTime.Evening.disaplyInformationFor( LocalDate.of( 2017, 8, 29 ) ), 
               is( "Tue, Aug 29 2017: Evening" ) 
      );
      assertThat( 
               DayTime.Period.disaplyInformationFor( LocalDate.of( 2017, 8, 29 ) ), 
               is( "Average for w/c Tue, Aug 29 2017" ) 
      );
   }//End Method

}//End Class
