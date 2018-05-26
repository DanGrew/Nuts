package uk.dangrew.nuts.graphics.tutorial.database;

import javafx.scene.Parent;
import uk.dangrew.nuts.graphics.database.UiDatabaseManagerPane;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialGlass;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialSelector;

public class DatabaseTutorial {

   private final TutorialGlass glass;
   private final DatabaseComponents components;
   
   public DatabaseTutorial( UiDatabaseManagerPane pane ) {
      this.components = pane.generateComponents();
      this.glass = new TutorialGlass( pane );
      new TutorialSelector( glass, components );
   }//End Constructor
   
   public Parent window(){
      return glass;
   }//End Method
}//End Class
