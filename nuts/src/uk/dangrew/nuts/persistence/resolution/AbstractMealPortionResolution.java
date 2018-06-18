package uk.dangrew.nuts.persistence.resolution;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;

public abstract class AbstractMealPortionResolution< MealTypeT extends Meal > implements ResolutionStrategy {

   private final ConceptStore< ? extends MealTypeT > store;
   private final String subjectId;
   private final String referenceId;
   private final Double portion;
   
   public AbstractMealPortionResolution( 
            ConceptStore< ? extends MealTypeT > store, 
            String subjectId, 
            String referenceId, 
            Double portion 
   ) {
      this.store = store;
      this.subjectId = subjectId;
      this.referenceId = referenceId;
      this.portion = portion;
   }//End Constructor
   
   protected String subjectId(){
      return subjectId;
   }//End Method

   @Override public void resolve( Database database ) {
      MealTypeT subject = store.get( subjectId );
      if ( subject == null ) {
         return;
      }
      
      Food food = null;
      if ( referenceId != null && !referenceId.trim().isEmpty() ) {
         food = database.foodItems().get( referenceId );
         if ( food == null ) {
            food = database.meals().get( referenceId );
         }
         
         if ( food == null ) {
            food = database.templates().get( referenceId );
         }
         
         if ( food == null ) {
            System.out.println( "Can't find food for: " + referenceId );
            return;
         }
      } 
      FoodPortion foodPortion = new FoodPortion( food, portion );
      applyPortion( subject, foodPortion, database );
   }//End Method
   
   protected void applyPortion( MealTypeT subject, FoodPortion portion, Database database ) {}
   
}//End Class
