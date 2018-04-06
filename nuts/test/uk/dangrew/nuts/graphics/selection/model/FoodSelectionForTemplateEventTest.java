package uk.dangrew.nuts.graphics.selection.model;

import uk.dangrew.kode.event.structure.AbstractEventManagerTest;
import uk.dangrew.kode.event.structure.EventManager;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionForTemplateEvent;
import uk.dangrew.nuts.template.Template;

public class FoodSelectionForTemplateEventTest extends AbstractEventManagerTest< Template > {

   /**
    * {@inheritDoc}
    */
   @Override protected EventManager< Template > constructSut() {
      return new FoodSelectionForTemplateEvent();
   }//End Method

}//End Class