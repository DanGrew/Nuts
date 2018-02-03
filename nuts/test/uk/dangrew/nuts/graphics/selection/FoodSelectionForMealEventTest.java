package uk.dangrew.nuts.graphics.selection;

import uk.dangrew.kode.event.structure.AbstractEventManagerTest;
import uk.dangrew.kode.event.structure.EventManager;
import uk.dangrew.nuts.meal.Meal;

public class FoodSelectionForMealEventTest extends AbstractEventManagerTest< Meal > {

   /**
    * {@inheritDoc}
    */
   @Override protected EventManager< Meal > constructSut() {
      return new FoodSelectionForMealEvent();
   }//End Method

}//End Class