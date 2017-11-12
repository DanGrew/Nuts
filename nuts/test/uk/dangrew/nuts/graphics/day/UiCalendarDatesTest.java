package uk.dangrew.nuts.graphics.day;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlanStore;

public class UiCalendarDatesTest {

   @Ignore
   @Test public void maunal() throws InterruptedException {
      TestApplication.launch( () -> new UiCalendarDates( new DayPlanStore(), new UiCalendarController() ) );
      
      Thread.sleep( 999999999 );
   }//End Method

}//End Class
