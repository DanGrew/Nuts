package uk.dangrew.nuts.graphics.main;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.ReentrantLock;

import uk.dangrew.kode.event.structure.EventManager;
import uk.dangrew.kode.event.structure.EventSubscription;

public class OpenTabEvent extends EventManager< TabDefinition > {

   private static final Collection< EventSubscription< TabDefinition > > subscriptions = 
            new LinkedHashSet<>();
   private static final ReentrantLock lock = new ReentrantLock();
   
   public OpenTabEvent() {
      super( subscriptions, lock );
   }//End Constructor

}//End Class
