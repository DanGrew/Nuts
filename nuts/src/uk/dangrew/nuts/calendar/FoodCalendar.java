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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.nuts.food.FoodItemStore;

/**
 * {@link FoodCalendar} provides the overall calendar for managing stock.
 */
public class FoodCalendar {

   private final CalendarPeriod period;
   private final ObservableList< StockChange > stockChanges;
   
   private final DayPlans dayPlans;
   
   /**
    * Constructs a new {@link FoodCalendar}.
    * @param foodItems the {@link FoodItemStore}s to manage stock for.
    */
   public FoodCalendar( FoodItemStore foodItems ) {
      this( new CalendarPeriod(), new DayPlans(), foodItems );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodCalendar}.
    * @param period the {@link CalendarPeriod}.
    * @param dayPlans the {@link DayPlans}.
    * @param foodItems the {@link FoodItemStore}s to manage stock for.
    */
   FoodCalendar( CalendarPeriod period, DayPlans dayPlans, FoodItemStore foodItems ) {
      this.period = period;
      this.stockChanges = FXCollections.observableArrayList();
      
      this.dayPlans = dayPlans;
      this.dayPlans.associate( period, foodItems );
   }//End Class

   /**
    * Access to the period of the calendar.
    * @return the {@link ObservableList} of {@link LocalDate}s.
    */
   public ObservableList< LocalDate > period() {
      return period.days();
   }//End Method

   /**
    * Access to the {@link StockChange}s.
    * @return the {@link ObservableList}.
    */
   public ObservableList< StockChange > stockChanges() {
      return stockChanges;
   }//End Method

   /**
    * Access to the {@link DayPlans}.
    * @return the {@link DayPlans}.
    */
   public DayPlans dayPlans() {
      return dayPlans;
   }//End Method

}//End Class
