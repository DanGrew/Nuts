package uk.dangrew.nuts.persistence.resolution;

import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.system.ConceptStore;

@FunctionalInterface
public interface MealResolutionSupplier {

   public AbstractMealPortionResolution< Meal > generate(
            ConceptStore< ? extends Meal > store,
            String subjectId, 
            String referenceId, 
            Double portion  
   );
   
}//End Interface
