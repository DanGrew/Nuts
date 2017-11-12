/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.day;

import java.time.LocalDate;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.template.Template;

/**
 * The {@link DayPlan} provides a specific {@link Template} for a day of the year.
 */
public class DayPlan implements Food {

   private final LocalDate date;
   private final Template template;
   
   public DayPlan( LocalDate date ) {
      this.date = date;
      this.template = new Template( date.toString() );
   }//End Constructor

   public LocalDate date() {
      return date;
   }//End Method

   public Template template() {
      return template;
   }//End Method

   @Override public FoodProperties properties() {
      return template().properties();
   }//End Method

   @Override public FoodAnalytics foodAnalytics() {
      return template().foodAnalytics();
   }//End Method

}//End Class
