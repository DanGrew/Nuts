package uk.dangrew.nuts.persistence.resolution;

import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.nuts.meal.Meal;

@FunctionalInterface
public interface MealResolutionSupplier {

   public AbstractMealPortionResolution< Meal > generate(
            ConceptStore< ? extends Meal > store,
            String subjectId, 
            String referenceId, 
            Double portion  
   );
   
}//End Interface
