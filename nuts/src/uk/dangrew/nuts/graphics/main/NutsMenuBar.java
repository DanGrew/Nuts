package uk.dangrew.nuts.graphics.main;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import uk.dangrew.kode.event.structure.Event;
import uk.dangrew.kode.settings.window.SettingsBehaviour;
import uk.dangrew.kode.settings.window.SettingsOpenEvent;
import uk.dangrew.kode.settings.window.WindowPolicy;
import uk.dangrew.nuts.graphics.settings.NutsSettingsTreeItemType;

public class NutsMenuBar extends MenuBar {

   static final String MENU_SETTINGS = "Settings";
   static final String ITEM_CONFIGURE = "Configure";
   
   public NutsMenuBar() {
      SettingsOpenEvent settingsEvent = new SettingsOpenEvent();
      
      Menu settingsMenu = new Menu( MENU_SETTINGS );
      this.getMenus().add( settingsMenu );
      
      MenuItem configureSettings = new MenuItem( ITEM_CONFIGURE );
      configureSettings.setOnAction( e -> settingsEvent.fire( new Event<>( 
               new SettingsBehaviour( WindowPolicy.Open, NutsSettingsTreeItemType.NutritionalUnitShowing ) )
      ) );
      settingsMenu.getItems().add( configureSettings );
   }//End Constructor
   
}//End Class
