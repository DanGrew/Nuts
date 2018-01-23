package uk.dangrew.nuts.graphics.day.balance;

import java.time.LocalDate;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.CalorieBalance;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanStore;

public class UiBalanceSummaryTest {

   @Ignore
   @Test public void manual() throws InterruptedException {
      DayPlanStore plans = new DayPlanStore();
      for ( int i = 0; i < 40; i++ ) {
         DayPlan plan = new DayPlan( LocalDate.now().plusDays( i - 10 ) );
         plans.store( plan );
      }
      
      new CalorieBalance( plans );
      
      TestApplication.launch( () -> new UiBalanceSummary( plans ) );
      
      Thread.sleep( 9999999 );
   }//End Method

}//End Class
