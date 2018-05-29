package uk.dangrew.nuts.graphics.tutorial.database.components;

import java.util.Optional;

import javafx.scene.Node;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialSelector;

public class DatabaseTutorialOptionBuilder {

   private final UiDatabaseTutorialOptionGrid mainTableTutorials;
   private final UiDatabaseTutorialOptionGrid mainTableAddButtonTutorials;
   private final UiDatabaseTutorialOptionGrid mealTableTutorials;
   private final UiDatabaseTutorialOptionGrid mealTableAddButtonTutorials;
   private final UiDatabaseTutorialOptionGrid mealTableRemoveButtonTutorials;
   private final UiDatabaseTutorialOptionGrid mealTableCopyButtonTutorials;
   private final UiDatabaseTutorialOptionGrid mealTableUpButtonTutorials;
   private final UiDatabaseTutorialOptionGrid mealTableDownButtonTutorials;
   
   public DatabaseTutorialOptionBuilder( TutorialSelector selector ) {
      this.mainTableTutorials = new UiDatabaseTutorialOptionGrid( 
               selector, 
               DatabaseTutorials.AddingFoodItemToTable,
               DatabaseTutorials.MakingMeals
      );
      this.mainTableAddButtonTutorials = new UiDatabaseTutorialOptionGrid(
               selector, 
               DatabaseTutorials.AddingFoodItemToTable 
      );
      this.mealTableTutorials = new UiDatabaseTutorialOptionGrid(
               selector, 
               DatabaseTutorials.MakingMeals
      );
      this.mealTableAddButtonTutorials = mealTableTutorials;
      this.mealTableRemoveButtonTutorials = mealTableTutorials;
      this.mealTableCopyButtonTutorials = mealTableTutorials;
      this.mealTableUpButtonTutorials = mealTableTutorials;
      this.mealTableDownButtonTutorials = mealTableTutorials;
   }//End Constructor
   
   public Optional< UiDatabaseTutorialOptionGrid > optionsFor( DatabaseComponents components, Node component ) {
      if ( components.mainTable() == component ) {
         return Optional.of( mainTableTutorials );
      } else if ( components.mainTableAddButton() == component ) {
         return Optional.of( mainTableAddButtonTutorials );
      } else if ( components.mealTable() == component ) {
         return Optional.of( mealTableTutorials );
      } else if ( components.mealTableAddButton() == component ) {
         return Optional.of( mealTableAddButtonTutorials );
      } else if ( components.mealTableRemoveButton() == component ) {
         return Optional.of( mealTableRemoveButtonTutorials );
      } else if ( components.mealTableCopyButton() == component ) {
         return Optional.of( mealTableCopyButtonTutorials );
      } else if ( components.mealTableUpButton() == component ) {
         return Optional.of( mealTableUpButtonTutorials );
      } else if ( components.mealTableDownButton() == component ) {
         return Optional.of( mealTableDownButtonTutorials );
      } else {
         return Optional.empty();
      }
   }//End Method

}//End Class
