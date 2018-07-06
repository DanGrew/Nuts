package uk.dangrew.nuts.persistence.resolution;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;

public class DayPlanMealPortionResolution extends AbstractMealPortionResolution< Meal > {

   public DayPlanMealPortionResolution( 
            ConceptStore< ? extends Meal > store,
            String subjectId, 
            String referenceId, 
            Double portion 
   ) {
      super( 
               store, 
               subjectId, 
               referenceId, 
               portion 
      );
   }//End Constructor
   
   @Override protected Food resolveFood( String referenceId, Database database ) {
      Food food = database.dayPlanController().foodItems().get( referenceId );
      if ( food == null ) {
         food = database.dayPlanController().meals().get( referenceId );
      }
      return food;
   }//End Method
   
   @Override protected void applyPortion( Meal subject, FoodPortion portion, Database database ) {
      subject.portions().add( portion );
   }//End Method

}//End Class
