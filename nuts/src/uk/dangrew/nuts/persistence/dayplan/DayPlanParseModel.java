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
   private double consumedCalories;
   private double allowedCalories;
   private double calorieBalance;
   private boolean isBalanceReset;
   private boolean consumed;
   
   private Set< FoodPortion > consumedPortions;
   
   DayPlanParseModel( Database database, DayPlanStore templates ) {
      super( database, templates );
      this.consumedPortions = new HashSet<>();
   }//End Constructor
   
   /**
    * Triggered when starting a new {@link Meal}.
    */
   @Override protected void startMeal() {
      super.startMeal();
      this.dateString = null;
      this.consumedCalories = 0;
      this.allowedCalories = 0;
      this.calorieBalance = 0;
      this.isBalanceReset = false;
      this.consumedPortions.clear();
   }//End Method
   
   /**
    * Triggered when all values of a {@link Meal} have been parsed.
    */
   @Override protected void finishMeal() {
      super.finishMeal();
      DayPlan template = meals().get( id() );
      
      try {
         LocalDate date = LocalDate.parse( dateString );
         template.setDate( date );
         template.consumedCalories().set( consumedCalories );
         template.allowedCalories().set( allowedCalories );
         template.calorieBalance().set( calorieBalance );
         template.isBalanceReset().set( isBalanceReset );
         template.consumed().addAll( consumedPortions );
      } catch ( NullPointerException | DateTimeParseException e ) {
         meals().removeConcept( template );
         System.out.println( "Removed day plan with invalid date: " + dateString );
         return;
      }
   }//End Method
   
   @Override protected void startPortion() {
      super.startPortion();
      this.consumed = false;
   }//End Method
   
   @Override protected FoodPortion finishPortion() {
      FoodPortion portion = super.finishPortion();
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
   
   void setConsumedCalories( String key, Double value ) {
      this.consumedCalories = value;
   }//End Method
   
   void setAllowedCalories( String key, Double value ) {
      this.allowedCalories = value;
   }//End Method
   
   void setCalorieBalance( String key, Double value ) {
      this.calorieBalance = value;
   }//End Method
   
   void setIsBalanceReset( String key, Boolean value ) {
      this.isBalanceReset = value;
   }//End Method
   
   void setConsumed( String key, Boolean value ) {
      this.consumed = value;
   }//End Method
   
}//End Class
