package uk.dangrew.nuts.label;

import java.util.function.Function;

import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.nuts.store.Database;

public enum Labelables {
   
   FoodItems( Database::foodItems ),
   Meals( Database::meals ),
   CalorieGoals( Database::calorieGoals ), 
   ProportionGoals( Database::proportionGoals );
   
   private final Function< Database, ConceptStore< ? extends Concept > > conceptRedirect;
   
   private Labelables( Function< Database, ConceptStore< ? extends Concept > > conceptRedirect ) {
      this.conceptRedirect = conceptRedirect;
   }//End Constructor
   
   public ConceptStore< Concept > redirect( Database database ) {
      return ( ConceptStore< Concept > )conceptRedirect.apply( database );
   }//End Method

}//End Class
