package uk.dangrew.nuts.graphics.main;

import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialWindow;
import uk.dangrew.nuts.persistence.fooditems.FoodSessions;
import uk.dangrew.nuts.store.Database;

public class CoreInterfaceOperations {

   private final FoodSessions sessions;
   private final TutorialWindow tutorialWindow;
   
   public CoreInterfaceOperations( Database database ) {
      this( new TutorialWindow(), new FoodSessions( database ) );
   }//End Class
   
   CoreInterfaceOperations( TutorialWindow tutorialWindow, FoodSessions sessions ) {
      this.tutorialWindow = tutorialWindow;
      this.sessions = sessions;
      this.sessions.read();
   }//End Class

   public void save() {
      sessions.write();
   }//End Method
   
   public void showTutorial(){
      tutorialWindow.show();
   }//End Method

}//End Class
