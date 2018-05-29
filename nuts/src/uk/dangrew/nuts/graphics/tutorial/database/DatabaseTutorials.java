package uk.dangrew.nuts.graphics.tutorial.database;

import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialCreator;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialGlass;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialSelector;

public enum DatabaseTutorials {

   AddingFoodItemToTable( 
            DttAddingFoodItemToTable::new,
            "Databases 101a",
            "Adding Food Items in the Database"
   ),
   MakingMeals( 
            DttMakingMeals::new,
            "Databases 101b",
            "Making Meals and Recipes"
   );
   
   private final TutorialCreator generator;
   private final String tutorialId;
   private final String description;
   
   private DatabaseTutorials(
            TutorialCreator generator,
            String tutorialId,
            String tutorialDescription
   ){
      this.generator = generator;
      this.tutorialId = tutorialId;
      this.description = tutorialId + ": " + tutorialDescription;
   }//End Constructor
   
   public DatabaseTableTutorial generate( DatabaseComponents components, TutorialGlass glass, TutorialSelector selector ) {
      return generator.create( components, glass, selector );
   }//End Method
   
   public String tutorialId(){
      return tutorialId;
   }//End Method   
   
   public String description(){
      return description;
   }//End Method
   
}//End Enum
