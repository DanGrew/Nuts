package uk.dangrew.nuts.graphics.main;

import uk.dangrew.kode.event.structure.AbstractEventManagerTest;
import uk.dangrew.kode.event.structure.EventManager;

public class OpenTabEventTest extends AbstractEventManagerTest< TabDefinition > {

   @Override protected EventManager< TabDefinition > constructSut() {
      return new OpenTabEvent();
   }//End Method

}//End Class
