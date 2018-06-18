package uk.dangrew.nuts.persistence.resolution;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;

public class MealPortionResolution extends AbstractMealPortionResolution< Meal > {

   public MealPortionResolution( 
            ConceptStore< ? extends Meal > store,
            String subjectId, 
            String referenceId, 
            Double portion 
   ) {
      super( store, subjectId, referenceId, portion );
   }//End Constructor
   
   @Override protected void applyPortion( Meal subject, FoodPortion portion, Database database ) {
      subject.portions().add( portion );
   }//End Method

}//End Class
