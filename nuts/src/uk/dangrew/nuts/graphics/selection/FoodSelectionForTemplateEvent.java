package uk.dangrew.nuts.graphics.selection;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.ReentrantLock;

import uk.dangrew.kode.event.structure.EventManager;
import uk.dangrew.kode.event.structure.EventSubscription;
import uk.dangrew.nuts.template.Template;

public class FoodSelectionForTemplateEvent extends EventManager< Template > {

   private static final Collection< EventSubscription< Template > > subscriptions = 
            new LinkedHashSet<>();
   private static final ReentrantLock lock = new ReentrantLock();
   
   public FoodSelectionForTemplateEvent() {
      super( subscriptions, lock );
   }//End Constructor

}//End Class
