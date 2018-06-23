package uk.dangrew.nuts.graphics.main;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import uk.dangrew.kode.event.structure.Event;
import uk.dangrew.kode.event.structure.EventSubscription;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.settings.window.SettingsBehaviour;
import uk.dangrew.kode.settings.window.SettingsOpenEvent;
import uk.dangrew.kode.settings.window.WindowPolicy;
import uk.dangrew.nuts.graphics.settings.NutsSettingsTreeItemType;

public class NutsMenuBarTest {

   @Captor private ArgumentCaptor< Event< SettingsBehaviour > > openEventCaptor;
   @Mock private EventSubscription< SettingsBehaviour > subscription;
   
   private NutsMenuBar systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      new SettingsOpenEvent().register( subscription );
      
      systemUnderTest = new NutsMenuBar();
   }//End Method

   @Test public void shouldHaveSettingsMenu() {
      Menu settings = systemUnderTest.getMenus().get( 0 );
      assertThat( settings.getText(), is( NutsMenuBar.MENU_SETTINGS ) );
      
      MenuItem configure = settings.getItems().get( 0 );
      assertThat( configure.getText(), is( NutsMenuBar.ITEM_CONFIGURE ) );
      configure.fire();
      
      verify( subscription ).notify( openEventCaptor.capture() );
      assertThat( openEventCaptor.getValue().getValue().getWindowPolicy(), is( WindowPolicy.Open ) );
      assertThat( openEventCaptor.getValue().getValue().getSelection(), is( NutsSettingsTreeItemType.NutritionalUnitShowing ) );
   }//End Method

}//End Class
