package uk.dangrew.nuts.graphics.tutorial.architecture.tutorial;

import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseTutorial;

public class TutorialWindow {
   
   private final Stage stage;
   
   public TutorialWindow() {
      this( new Stage() );
   }//End Constructor
   
   TutorialWindow( Stage stage ) {
      this.stage = stage;
      this.stage.setAlwaysOnTop( true );
      this.stage.setScene( new Scene( new DatabaseTutorial().window() ) );
      this.stage.hide();
   }//End Constructor
   
   public void show(){
      JavaFxThreading.runAndWait( stage::showAndWait );
   }//End Method
   
}//End Class
