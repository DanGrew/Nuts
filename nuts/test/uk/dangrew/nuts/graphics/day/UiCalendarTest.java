package uk.dangrew.nuts.graphics.day;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.store.Database;

public class UiCalendarTest {

   @Ignore
   @Test public void maunal() throws InterruptedException {
      TestApplication.launch( () -> new UiCalendar( new Database() ) );
      
      Thread.sleep( 999999999 );
   }//End Method

}
