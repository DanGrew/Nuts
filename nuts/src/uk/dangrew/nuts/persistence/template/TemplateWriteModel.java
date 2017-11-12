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
import uk.dangrew.nuts.template.Template;
import uk.dangrew.nuts.template.TemplateStore;

/**
 * {@link TemplateWriteModel} is responsible for handling the hooks for the {@link uk.dangrew.jupa.json.parse.JsonParser}
 * when writing {@link Template}s.
 */
class TemplateWriteModel extends MealWriteModel< Template > {
   
   /**
    * Constructs a new {@link TemplateWriteModel}.
    * @param templates the {@link FoodStore} providing the {@link Template}s.
    */
   TemplateWriteModel( TemplateStore templates ) {
      super( templates );
   }//End Constructor
   
   /**
    * Provides the next {@link uk.dangrew.nuts.food.FoodProperties#id()}.
    * @param key the parsed key.
    * @return the value.
    */
   String getGoalId( String key ) {
      Goal goal = current().goalAnalytics().goal().get();
      if ( goal == null ) {
         return null;
      }
      return goal.properties().id();
   }//End Method

}//End Class
