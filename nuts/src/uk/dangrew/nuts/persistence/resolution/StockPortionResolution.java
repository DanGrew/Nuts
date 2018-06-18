package uk.dangrew.nuts.persistence.resolution;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.stock.Stock;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;

public class StockPortionResolution extends AbstractMealPortionResolution< Stock > {

   public StockPortionResolution( 
            ConceptStore< Stock > store,
            String subjectId, 
            String referenceId, 
            Double portion
   ) {
      super( store, subjectId, referenceId, portion );
   }//End Constructor

   @Override protected void applyPortion( Stock subject, FoodPortion portion, Database database ) {
      FoodPortion existing = subject.portionFor( portion.food().get() );
      if ( existing == null ) {
         subject.portions().add( portion );
      } else {
         existing.setPortion( portion.portion().get() );
      }
   }//End Method
   
}//End Class
