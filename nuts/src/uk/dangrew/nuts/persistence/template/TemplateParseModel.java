/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.template;


import uk.dangrew.nuts.food.FoodStore;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.persistence.meals.MealParseModel;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

/**
 * {@link TemplateParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing {@link Template}s.
 */
public class TemplateParseModel< FoodTypeT extends Template > extends MealParseModel< FoodTypeT > {
   
   private String goalId;
   
   /**
    * Constructs a new {@link TemplateParseModel}.
    * @param database the {@link Database}.
    * @param templates {@link FoodStore} providing the {@link Template}s.
    */
   protected TemplateParseModel( Database database, FoodStore< FoodTypeT > templates ) {
      super( database, templates );
   }//End Constructor
   
   /**
    * Triggered when starting a new {@link Meal}.
    * @param key the parsed key.
    */
   @Override protected void startMeal( String key ) {
      super.startMeal( key );
      this.goalId = null;
   }//End Method
   
   /**
    * Triggered when all values of a {@link Meal} have been parsed.
    * @param key the parsed key.
    */
   @Override protected void finishMeal( String key ) {
      super.finishMeal( key );
      Template template = meals().get( id() );
      
      Goal resolvedGoal = database().goals().get( goalId );
      if ( resolvedGoal == null ) {
         System.out.println( "Cannot find goal: " + goalId );
         return;
      }
      template.goalAnalytics().goal().set( resolvedGoal );
   }//End Method
   
   /**
    * Sets the id of the {@link Goal} associated with the current {@link Template}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setGoalId( String key, String value ) {
      this.goalId = value;
   }//End Method
   
}//End Class
