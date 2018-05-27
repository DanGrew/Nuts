package uk.dangrew.nuts.graphics.tutorial.database;

import java.util.Optional;

import javafx.scene.Node;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialSelector;

public class DatabaseTutorialOptionBuilder {

   private final UiDatabaseTutorialOptionGrid mainTableTutorials;
   private final UiDatabaseTutorialOptionGrid mainTableAddButtonTutorials;
   
   public DatabaseTutorialOptionBuilder( TutorialSelector selector ) {
      this.mainTableTutorials = new UiDatabaseTutorialOptionGrid( 
               selector, 
               DatabaseTutorials.AddingFoodItemToTable 
      );
      
      this.mainTableAddButtonTutorials = new UiDatabaseTutorialOptionGrid(
               selector, 
               DatabaseTutorials.AddingFoodItemToTable 
      );
   }//End Constructor
   
   public Optional< UiDatabaseTutorialOptionGrid > optionsFor( DatabaseComponents components, Node component ) {
      if ( components.mainTable() == component ) {
         return Optional.of( mainTableTutorials );
      } else if ( components.mainTableAddButton() == component ) {
         return Optional.of( mainTableAddButtonTutorials );
      } else {
         return Optional.empty();
      }
   }//End Method

}//End Class
