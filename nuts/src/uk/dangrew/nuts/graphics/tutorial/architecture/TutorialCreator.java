package uk.dangrew.nuts.graphics.tutorial.architecture;

import uk.dangrew.nuts.graphics.tutorial.database.DatabaseComponents;
import uk.dangrew.nuts.graphics.tutorial.database.DatabaseTableTutorial;

public interface TutorialCreator {
   
   public DatabaseTableTutorial create( 
            DatabaseComponents components, 
            TutorialGlass glass, 
            TutorialSelector selector 
   );

}//End Interface

