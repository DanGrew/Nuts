package uk.dangrew.nuts.persistence.resolution;

import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.Food;
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
   
   @Override public void resolve( Database database ) {
      DayPlan subject = store().get( subjectId() );
      if ( subject == null ) {
         return;
      }
      
      FoodPortion foodPortion = null;
      Food food = database.dayPlanController().foodItems().get( referenceId() );
      if ( food == null ) {
         food = database.dayPlanController().meals().get( referenceId() );
      }
      
      if ( food == null ) {
         food = super.resolveFood( referenceId(), database );
         foodPortion = database.dayPlanController().add( new FoodPortion( food, portion() ), subject );
      } else {
         foodPortion = new FoodPortion( food, portion() );
         subject.portions().add( foodPortion );
      }
      
      if ( consumed ) {
         subject.consumed().add( foodPortion );
      }
   }//End Method

}//End Class
