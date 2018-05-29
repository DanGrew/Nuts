package uk.dangrew.nuts.graphics.main;

import uk.dangrew.nuts.persistence.fooditems.FoodSessions;
import uk.dangrew.nuts.store.Database;

public class CoreInterfaceOperations {

   private final FoodSessions sessions;
   
   public CoreInterfaceOperations( Database database ) {
      this( new FoodSessions( database ) );
   }//End Class
   
   CoreInterfaceOperations( FoodSessions sessions ) {
      this.sessions = sessions;
      this.sessions.read();
   }//End Class

   public void save() {
      sessions.write();
   }//End Method

}//End Class
