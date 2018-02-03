package uk.dangrew.nuts.graphics.selection;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.ReentrantLock;

import uk.dangrew.kode.event.structure.EventManager;
import uk.dangrew.kode.event.structure.EventSubscription;
import uk.dangrew.nuts.meal.Meal;

public class FoodSelectionForMealEvent extends EventManager< Meal > {

   private static final Collection< EventSubscription< Meal > > subscriptions = 
            new LinkedHashSet<>();
   private static final ReentrantLock lock = new ReentrantLock();
   
   public FoodSelectionForMealEvent() {
      super( subscriptions, lock );
   }//End Constructor

}//End Class
