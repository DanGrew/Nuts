package uk.dangrew.nuts.label;

import java.util.function.Function;

import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.ConceptStore;

public enum Labelables {
   
   FoodItems( Database::foodItems ),
   Meals( Database::meals ),
   Goals( Database::goals );
   
   private final Function< Database, ConceptStore< ? extends Concept > > conceptRedirect;
   
   private Labelables( Function< Database, ConceptStore< ? extends Concept > > conceptRedirect ) {
      this.conceptRedirect = conceptRedirect;
   }//End Constructor
   
   public ConceptStore< Concept > redirect( Database database ) {
      return ( ConceptStore< Concept > )conceptRedirect.apply( database );
   }//End Method

}//End Class
