package uk.dangrew.nuts.graphics.main;

import uk.dangrew.kode.settings.tree.SettingsTreePane;
import uk.dangrew.kode.settings.window.SettingsWindowController;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.graphics.settings.NutsSettingsTreeItems;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialWindow;
import uk.dangrew.nuts.persistence.fooditems.DatabaseIo;
import uk.dangrew.nuts.persistence.fooditems.FoodSessions;
import uk.dangrew.nuts.store.Database;

public class CoreInterfaceOperations {

   private final DatabaseIo databaseIo;
   private final TutorialWindow tutorialWindow;
   
   public CoreInterfaceOperations( NutsSettings settings, Database database ) {
      this( 
               new TutorialWindow(), 
               new FoodSessions( database ).databaseIo(),
               new SettingsWindowController(),
               settings 
      );
   }//End Class
   
   CoreInterfaceOperations( 
            TutorialWindow tutorialWindow, 
            DatabaseIo databaseIo, 
            SettingsWindowController windowController, 
            NutsSettings settings 
   ) {
      this.tutorialWindow = tutorialWindow;
      this.databaseIo = databaseIo;
      this.databaseIo.read();
      
      SettingsTreePane settingsPane = new SettingsTreePane(
               new NutsSettingsTreeItems( settings ), 
               windowController 
      );
      windowController.associateWithSettingsTreePane( settingsPane );
   }//End Class

   public void save() {
      databaseIo.write();
   }//End Method
   
   public void showTutorial(){
      tutorialWindow.show();
   }//End Method

}//End Class
