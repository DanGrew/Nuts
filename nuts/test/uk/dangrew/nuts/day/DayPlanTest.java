package uk.dangrew.nuts.day;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class DayPlanTest {

   private LocalDate date;
   private DayPlan systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      date = LocalDate.now();
      systemUnderTest = new DayPlan( "Plan" );
   }//End Method

   @Test public void shouldProvideDate() {
      assertThat( systemUnderTest.date(), is( nullValue() ) );
      systemUnderTest.setDate( date );
      assertThat( systemUnderTest.date(), is( date ) );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldSetDateOnlyOnce(){
      systemUnderTest.setDate( date );
      systemUnderTest.setDate( date );
   }//End Method
   
}//End Class
