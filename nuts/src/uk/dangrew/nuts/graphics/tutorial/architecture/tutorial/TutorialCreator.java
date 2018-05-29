package uk.dangrew.nuts.graphics.tutorial.architecture.tutorial;

import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseComponents;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseTableTutorial;

public interface TutorialCreator {
   
   public DatabaseTableTutorial create( 
            DatabaseComponents components, 
            TutorialGlass glass, 
            TutorialSelector selector 
   );

}//End Interface

