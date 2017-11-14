/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.dayplan;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanStore;
import uk.dangrew.nuts.persistence.template.TemplateParseModel;
import uk.dangrew.nuts.store.Database;

/**
 * {@link DayPlanParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing {@link DayPlan}s.
 */
class DayPlanParseModel extends TemplateParseModel< DayPlan > {
   
   private String dateString;
   
   DayPlanParseModel( Database database, DayPlanStore templates ) {
      super( database, templates );
   }//End Constructor
   
   /**
    * Triggered when starting a new {@link Meal}.
    * @param key the parsed key.
    */
   @Override protected void startMeal( String key ) {
      super.startMeal( key );
      this.dateString = null;
   }//End Method
   
   /**
    * Triggered when all values of a {@link Meal} have been parsed.
    * @param key the parsed key.
    */
   @Override protected void finishMeal( String key ) {
      super.finishMeal( key );
      DayPlan template = meals().get( id() );
      
      try {
         LocalDate date = LocalDate.parse( dateString );
         template.setDate( date );
      } catch ( NullPointerException | DateTimeParseException e ) {
         meals().removeFood( template );
         System.out.println( "Removed day plan with invalid date: " + dateString );
         return;
      }
   }//End Method
   
   void setDateString( String key, String value ) {
      this.dateString = value;
   }//End Method
   
}//End Class
