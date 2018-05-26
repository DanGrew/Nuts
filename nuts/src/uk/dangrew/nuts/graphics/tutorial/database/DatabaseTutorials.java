package uk.dangrew.nuts.graphics.tutorial.database;

import java.util.function.BiFunction;

import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialGlass;

public enum DatabaseTutorials {

   AddingFoodItemToTable( 
            DttAddingFoodItemToTable::new,
            "Adding Food Items in the Database"
   );
   
   private final BiFunction< DatabaseComponents, TutorialGlass, DatabaseTableTutorial > generator;
   private final String description;
   
   private DatabaseTutorials(
            BiFunction< DatabaseComponents, TutorialGlass, DatabaseTableTutorial > generator,
            String tutorialDescription
   ){
      this.generator = generator;
      this.description = tutorialDescription;
   }//End Constructor
   
   public DatabaseTableTutorial generate( DatabaseComponents components, TutorialGlass glass ) {
      return generator.apply( components, glass );
   }//End Method
   
   public String description(){
      return description;
   }//End Method
   
}//End Enum
