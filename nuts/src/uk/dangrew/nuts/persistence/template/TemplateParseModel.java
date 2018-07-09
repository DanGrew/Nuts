/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.template;


import uk.dangrew.nuts.persistence.meals.MealParseModel;
import uk.dangrew.nuts.persistence.resolution.TemplateGoalResolution;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;
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
    * @param templates {@link ConceptStore} providing the {@link Template}s.
    */
   protected TemplateParseModel( Database database, ConceptStore< FoodTypeT > templates ) {
      super( database, templates );
   }//End Constructor
   
   /**
    * Triggered when starting a new {@link Meal}.
    */
   @Override protected void startMeal() {
      super.startMeal();
      this.goalId = null;
   }//End Method
   
   /**
    * Triggered when all values of a {@link Meal} have been parsed.
    */
   @Override protected void finishMeal() {
      super.finishMeal();
      database().resolver().submitStrategy( new TemplateGoalResolution( meals(), id(), goalId ) );
   }//End Method
   
   @Override protected void setId( String value ) {
      super.setId( value );
   }//End Method
   
   @Override protected void setName( String value ) {
      super.setName( value );
   }//End Method
   
   /**
    * Sets the id of the {@link Goal} associated with the current {@link Template}.
    * @param value the parsed value.
    */
   void setGoalId( String value ) {
      this.goalId = value;
   }//End Method
   
}//End Class
