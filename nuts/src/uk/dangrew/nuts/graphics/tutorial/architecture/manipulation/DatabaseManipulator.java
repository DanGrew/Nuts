package uk.dangrew.nuts.graphics.tutorial.architecture.manipulation;

import java.util.function.Function;

import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.nuts.store.Database;

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
