package uk.dangrew.nuts.graphics.main;

import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialWindow;
import uk.dangrew.nuts.persistence.fooditems.DatabaseIo;
import uk.dangrew.nuts.persistence.fooditems.FoodSessions;
import uk.dangrew.nuts.store.Database;

public class CoreInterfaceOperations {

   private final DatabaseIo databaseIo;
   private final TutorialWindow tutorialWindow;
   
   public CoreInterfaceOperations( Database database ) {
      this( new TutorialWindow(), new FoodSessions( database ).databaseIo() );
   }//End Class
   
   CoreInterfaceOperations( TutorialWindow tutorialWindow, DatabaseIo databaseIo ) {
      this.tutorialWindow = tutorialWindow;
      this.databaseIo = databaseIo;
      this.databaseIo.read();
   }//End Class

   public void save() {
      databaseIo.write();
   }//End Method
   
   public void showTutorial(){
      tutorialWindow.show();
   }//End Method

}//End Class
