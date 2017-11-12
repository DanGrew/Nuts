package uk.dangrew.nuts.day;

import static org.hamcrest.CoreMatchers.notNullValue;
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
      systemUnderTest = new DayPlan( date );
   }//End Method

   @Test public void shouldProvideDate() {
      assertThat( systemUnderTest.date(), is( date ) );
   }//End Method
   
   @Test public void shouldProvideTemplate() {
      assertThat( systemUnderTest.template(), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldBeAFood(){
      assertThat( systemUnderTest.properties(), is( systemUnderTest.template().properties() ) );
      assertThat( systemUnderTest.foodAnalytics(), is( systemUnderTest.template().foodAnalytics() ) );
   }//End Method

}//End Class
