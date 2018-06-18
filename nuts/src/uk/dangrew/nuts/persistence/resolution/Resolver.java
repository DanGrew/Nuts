package uk.dangrew.nuts.persistence.resolution;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.store.Database;

public class Resolver {

   private final Database database;
   private final List< ResolutionStrategy > resolutions;
   
   public Resolver( Database database ) {
      this.database = database;
      this.resolutions = new ArrayList<>();
   }//End Constructor

   public void submitStrategy( ResolutionStrategy strategy ) {
      resolutions.add( strategy );
   }//End Method
   
   public void resolve() {
      while( !resolutions.isEmpty() ) {
         resolutions.remove( 0 ).resolve( database );
      }
   }//End Method
   
   public boolean isFullyResolved(){
      return resolutions.isEmpty();
   }//End Method

}//End Class
