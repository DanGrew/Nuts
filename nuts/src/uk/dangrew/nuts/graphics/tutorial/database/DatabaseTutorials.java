package uk.dangrew.nuts.graphics.tutorial.database;

import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialCreator;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialGlass;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialSelector;

public enum DatabaseTutorials {

   AddingFoodItemToTable( 
            DttAddingFoodItemToTable::new,
            "Adding Food Items in the Database"
   );
   
   private final TutorialCreator generator;
   private final String description;
   
   private DatabaseTutorials(
            TutorialCreator generator,
            String tutorialDescription
   ){
      this.generator = generator;
      this.description = tutorialDescription;
   }//End Constructor
   
   public DatabaseTableTutorial generate( DatabaseComponents components, TutorialGlass glass, TutorialSelector selector ) {
      return generator.create( components, glass, selector );
   }//End Method
   
   public String description(){
      return description;
   }//End Method
   
}//End Enum
