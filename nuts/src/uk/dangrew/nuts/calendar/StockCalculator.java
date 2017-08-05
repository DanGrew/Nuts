/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.calendar;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;

/**
 * {@link StockCalculator} is responsible for calculating how much of each {@link FoodItem}
 * is in stock for each {@link LocalDate}.
 */
public class StockCalculator {
   
   private DayPlans plans;
   private ObservableList< LocalDate > period;

   /**
    * Associates with the given.
    * @param dayPlans the {@link DayPlans}.
    * @param period the {@link ObservableList} of {@link LocalDate}s.
    */
   void associate( DayPlans dayPlans, ObservableList< LocalDate > period ) {
      if ( this.plans != null || this.period != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      
      this.plans = dayPlans;
      this.period = period;
   }//End Method

   /**
    * Method to recalculate for the given {@link FoodItem} across all days.
    * @param item the {@link FoodItem} to calculate for.
    */
   void recalculate( FoodItem item ) {
      double currentStock = 0.0;
      
      for ( LocalDate date : period ) {
         DayPlan plan = plans.dayPlanFor( date );
         Meal meal = plan.plan().get();
         double usedStock = meal == null ? 0.0 : meal.stockUsage().stockPortionUsed().get( item );
         currentStock -= usedStock;
         plan.stockFor( item ).set( currentStock );
      }
   }//End Method
   
}//End Class
