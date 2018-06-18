package uk.dangrew.nuts.persistence.resolution;

import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;

public class DayPlanConsumedResolution extends AbstractMealPortionResolution< DayPlan > {

   private final boolean consumed;
   
   public DayPlanConsumedResolution( 
            ConceptStore< DayPlan > store,
            String subjectId, 
            String referenceId, 
            Double portion,
            boolean consumed
   ) {
      super( store, subjectId, referenceId, portion );
      this.consumed = consumed;
   }//End Constructor

   @Override protected void applyPortion( DayPlan subject, FoodPortion portion, Database database ) {
      subject.portions().add( portion );
      if ( consumed ) {
         subject.consumed().add( portion );
      }
   }//End Method
   
}//End Class
