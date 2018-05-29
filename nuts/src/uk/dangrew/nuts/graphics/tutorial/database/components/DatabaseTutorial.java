package uk.dangrew.nuts.graphics.tutorial.database.components;

import javafx.scene.Parent;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialGlass;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialSelector;

public class DatabaseTutorial {

   private final TutorialGlass glass;
   private final DatabaseComponents components;
   
   public DatabaseTutorial() {
      this.components = new DatabaseComponents();
      this.glass = new TutorialGlass();
      new TutorialSelector( glass, components );
   }//End Constructor
   
   public Parent window(){
      return glass;
   }//End Method
}//End Class
