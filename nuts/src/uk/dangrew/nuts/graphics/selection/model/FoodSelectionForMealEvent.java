package uk.dangrew.nuts.graphics.selection.model;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.ReentrantLock;

import uk.dangrew.kode.event.structure.EventManager;
import uk.dangrew.kode.event.structure.EventSubscription;

public class FoodSelectionForMealEvent extends EventManager< FoodSelectionRequest > {

   private static final Collection< EventSubscription< FoodSelectionRequest > > subscriptions = 
            new LinkedHashSet<>();
   private static final ReentrantLock lock = new ReentrantLock();
   
   public FoodSelectionForMealEvent() {
      super( subscriptions, lock );
   }//End Constructor

}//End Class
