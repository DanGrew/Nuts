package uk.dangrew.nuts.persistence.resolution;

import java.util.Arrays;
import java.util.List;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.database.FoodTypes;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;

public abstract class AbstractMealPortionResolution< MealTypeT extends Meal > implements ResolutionStrategy {

   private final ConceptStore< ? extends MealTypeT > store;
   private final List< FoodTypes > resolutionStoreTypes;
   private final String subjectId;
   private final String referenceId;
   private final Double portion;
   
   public AbstractMealPortionResolution( 
            ConceptStore< ? extends MealTypeT > store,
            String subjectId, 
            String referenceId, 
            Double portion 
   ) {
      this( 
               store,
               Arrays.asList( FoodTypes.FoodItems, FoodTypes.Meals, FoodTypes.Templates ),
               subjectId, 
               referenceId,
               portion
      );
   }//End Constructor
   
   public AbstractMealPortionResolution( 
            ConceptStore< ? extends MealTypeT > store, 
            List< FoodTypes > resolutionStoreTypes, 
            String subjectId, 
            String referenceId, 
            Double portion 
   ) {
      this.store = store;
      this.resolutionStoreTypes = resolutionStoreTypes;
      this.subjectId = subjectId;
      this.referenceId = referenceId;
      this.portion = portion;
   }//End Constructor
   
   protected String subjectId(){
      return subjectId;
   }//End Method
   
   protected String referenceId(){
      return referenceId;
   }//End Method
   
   protected ConceptStore< ? extends MealTypeT > store(){
      return store;
   }//End Method
   
   protected double portion(){
      return portion;
   }//End Method

   @Override public void resolve( Database database ) {
      MealTypeT subject = store.get( subjectId );
      if ( subject == null ) {
         return;
      }
      
      Food food = null;
      if ( referenceId != null && !referenceId.trim().isEmpty() ) {
         food = resolveFood( referenceId, database );
         
         if ( food == null ) {
            System.out.println( "Can't find food for: " + referenceId );
            return;
         }
      } 
      FoodPortion foodPortion = new FoodPortion( food, portion );
      applyPortion( subject, foodPortion, database );
   }//End Method
   
   protected Food resolveFood( String referenceId, Database database ) {
      Food food = null;
      for ( FoodTypes resultionType : resolutionStoreTypes ) {
         food = resultionType.redirect( database ).get( referenceId );
         if ( food != null ) {
            return food;
         }
      }
      
      return food;
   }//End Method
   
   protected void applyPortion( MealTypeT subject, FoodPortion portion, Database database ) {}
   
}//End Class
