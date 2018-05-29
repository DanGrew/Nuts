package uk.dangrew.nuts.graphics.tutorial.database;

import java.util.function.Function;

import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.ConceptStore;

public class DatabaseManipulator {

   private final Database database;
   
   public DatabaseManipulator( Database datbase ) {
      this.database = datbase;
   }//End Constructor
   
   public < ConceptT extends Concept > ConceptT find( 
            String name, 
            Function< Database, ConceptStore< ConceptT > > storeRedirect 
   ) {
      return storeRedirect.apply( database ).objectList().stream()
               .filter( c -> c.properties().nameProperty().get().equals( name ) )
               .findFirst()
               .orElse( null );
   }//End Method
   
}//End Class
