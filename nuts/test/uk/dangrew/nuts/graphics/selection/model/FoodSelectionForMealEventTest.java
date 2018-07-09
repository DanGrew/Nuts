package uk.dangrew.nuts.graphics.selection.model;

import uk.dangrew.kode.event.structure.AbstractEventManagerTest;
import uk.dangrew.kode.event.structure.EventManager;

public class FoodSelectionForMealEventTest extends AbstractEventManagerTest< FoodSelectionRequest > {

   @Override protected EventManager< FoodSelectionRequest > constructSut() {
      return new FoodSelectionForMealEvent();
   }//End Method

}//End Class