package uk.dangrew.nuts.graphics.day;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlanStore;

public class UiCalendarTest {

   @Ignore
   @Test public void maunal() throws InterruptedException {
      TestApplication.launch( () -> new UiCalendar( new DayPlanStore() ) );
      
      Thread.sleep( 999999999 );
   }//End Method

}
