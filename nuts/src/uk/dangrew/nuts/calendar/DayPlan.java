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
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.meal.Meal;

/**
 * {@link DayPlan} provides a plan and a stock list for the entire {@link FoodItem} database for a given day.
 */
public class DayPlan {

   private final LocalDate date;
   private final ObjectProperty< Meal > plan;
   private final Map< FoodItem, ObjectProperty< Double > > stock;
   
   /**
    * Constructs a new {@link DayPlan}.
    * @param planDate the {@link LocalDate} of the plan.
    */
   public DayPlan( LocalDate planDate ) {
      this.date = planDate;
      this.plan = new SimpleObjectProperty<>();
      this.stock = new HashMap<>();
   }//End Constructor

   /**
    * Access to the {@link LocalDate}.
    * @return the {@link LocalDate}.
    */
   public LocalDate date() {
      return date;
   }//End Method

   /**
    * Access to the plan.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Meal > plan() {
      return plan;
   }//End Method

   /**
    * Access to the stock size, as a percentage of sold in weight for the given {@link FoodItem}.
    * @param food the {@link FoodItem} in question.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > stockFor( FoodItem food ) {
      return stock.get( food );
   }//End Method

   /**
    * Method to stock the {@link FoodItem}, providing an {@link ObjectProperty}.
    * @param food the {@link FoodItem} to record stock for.
    */
   public void stock( FoodItem food ) {
      ObjectProperty< Double > existingStock = stockFor( food ); 
      if ( existingStock != null ) {
         return;
      }
      stock.put( food, new SimpleObjectProperty<>( 0.0 ) );
   }//End Method

   /**
    * Method to remove the {@link FoodItem} from the stock.
    * @param food the {@link FoodItem}.
    */
   public void remove( FoodItem food ) {
      stock.remove( food );
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public String toString() {
      return date.toString();
   }//End Method

}//End Class
