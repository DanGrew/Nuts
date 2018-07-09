package uk.dangrew.nuts.store;

import java.util.function.Function;

import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.ConceptStore;

public enum DatabaseType {
   
   FoodItems( Database::foodItems ),
   Meals( Database::meals ),
   Templates( Database::templates ),
   CalorieGoals( Database::calorieGoals ), 
   ProportionGoals( Database::proportionGoals );
   
   private final Function< Database, ConceptStore< ? extends Concept > > conceptRedirect;
   
   private DatabaseType( Function< Database, ConceptStore< ? extends Concept > > conceptRedirect ) {
      this.conceptRedirect = conceptRedirect;
   }//End Constructor
   
   @SuppressWarnings("unchecked") //responsibility of caller to test and verify 
   public < ConceptT > ConceptStore< ConceptT > redirect( Database database ) {
      return ( ConceptStore< ConceptT > )conceptRedirect.apply( database );
   }//End Method

}//End Class
