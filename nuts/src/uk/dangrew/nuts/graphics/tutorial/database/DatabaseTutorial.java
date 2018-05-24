package uk.dangrew.nuts.graphics.tutorial.database;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Parent;
import uk.dangrew.nuts.graphics.database.UiDatabaseManagerPane;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialGlass;

public class DatabaseTutorial {

   private final TutorialGlass glass;
   private final DatabaseComponents components;
   private final List< DatabaseTableTutorial > tutorials;
   
   public DatabaseTutorial( UiDatabaseManagerPane pane ) {
      this.components = pane.generateComponents();
      this.glass = new TutorialGlass( pane );
//      this.glass.addEventFilter( MouseEvent.MOUSE_MOVED, e -> {
//         if ( components.mainTable().contains( e.getSceneX(), e.getSceneY() ) ) {
//            glass.tutorUser( new TutorMessageBuilder()
//                     .bordering( components.mainTable() )
//                     .withRespectTo( components.parent() ), 
//            null );
//         } else {
//            glass.removeTutorHighlight();
//         }
//      } );
      this.tutorials = Arrays.asList(
               new DttIntroduceTable( components, glass ) 
      );
   }//End Constructor
   
   public void runTutorial(){
      for ( DatabaseTableTutorial tutorial : tutorials ) {
         tutorial.run();
      }
   }//End Method
   
   public Parent window(){
      return glass;
   }//End Method
}//End Class
