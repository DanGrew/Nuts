package uk.dangrew.nuts.graphics.day;

import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.store.Database;

public class UiCalendarPaneTest {

   @Ignore
   @Test public void maunal() throws InterruptedException {
      TestApplication.launch( () -> new UiCalendarPane( new NutsSettings(), new Database() ) );
      
      Thread.sleep( 999999999 );
   }//End Method
   
   @Test public void untested() {
      System.out.println( "WARNING: UNTESTED - " + getClass().getName() );
   }//End Method

}
