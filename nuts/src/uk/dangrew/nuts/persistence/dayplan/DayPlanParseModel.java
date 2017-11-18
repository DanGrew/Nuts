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
import java.util.HashSet;
import java.util.Set;

import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanStore;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.persistence.template.TemplateParseModel;
import uk.dangrew.nuts.store.Database;

/**
 * {@link DayPlanParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing {@link DayPlan}s.
 */
class DayPlanParseModel extends TemplateParseModel< DayPlan > {
   
   private String dateString;
   private boolean consumed;
   
   private Set< FoodPortion > consumedPortions;
   
   DayPlanParseModel( Database database, DayPlanStore templates ) {
      super( database, templates );
      this.consumedPortions = new HashSet<>();
   }//End Constructor
   
   /**
    * Triggered when starting a new {@link Meal}.
    * @param key the parsed key.
    */
   @Override protected void startMeal( String key ) {
      super.startMeal( key );
      this.dateString = null;
      this.consumedPortions.clear();
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
         template.consumed().addAll( consumedPortions );
      } catch ( NullPointerException | DateTimeParseException e ) {
         meals().removeFood( template );
         System.out.println( "Removed day plan with invalid date: " + dateString );
         return;
      }
   }//End Method
   
   @Override protected void startPortion( String key ) {
      super.startPortion( key );
      this.consumed = false;
   }//End Method
   
   @Override protected FoodPortion finishPortion( String key ) {
      FoodPortion portion = super.finishPortion( key );
      if ( portion == null ) {
         return null;
      }
      
      if ( consumed ) {
         consumedPortions.add( portion );
      }
      
      return portion;
   }//End Method
   
   void setDateString( String key, String value ) {
      this.dateString = value;
   }//End Method
   
   void setConsumed( String key, Boolean value ) {
      this.consumed = value;
   }//End Method
   
}//End Class
