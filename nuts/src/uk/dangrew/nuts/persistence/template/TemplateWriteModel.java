/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.template;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.persistence.meals.MealWriteModel;
import uk.dangrew.nuts.system.ConceptStore;
import uk.dangrew.nuts.template.Template;

/**
 * {@link TemplateWriteModel} is responsible for handling the hooks for the {@link uk.dangrew.jupa.json.parse.JsonParser}
 * when writing {@link Template}s.
 */
public class TemplateWriteModel< FoodTypeT extends Template > extends MealWriteModel< FoodTypeT > {
   
   /**
    * Constructs a new {@link TemplateWriteModel}.
    * @param templates the {@link ConceptStore} providing the {@link Template}s.
    */
   protected TemplateWriteModel( ConceptStore< FoodTypeT > templates ) {
      super( templates );
   }//End Constructor
   
   /**
    * Provides the next {@link uk.dangrew.nuts.food.FoodProperties#id()}.
    * @param key the parsed key.
    * @return the value.
    */
   String getGoalId( String key ) {
      Goal goal = currentFood().goalAnalytics().goal().get();
      if ( goal == null ) {
         return null;
      }
      return goal.properties().id();
   }//End Method

}//End Class
