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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.food.FoodItem;

/**
 * {@link StockChange} provides the change in stock for a particular {@link FoodItem} on a day.
 */
public class StockChange {

   private final ObjectProperty< LocalDate > date;
   private final ObjectProperty< FoodItem > foodItem;
   private final ObjectProperty< Double > stockChange;
   
   /**
    * Constructs a new {@link StockChange}.
    */
   public StockChange() {
      this.date = new SimpleObjectProperty<>();
      this.foodItem = new SimpleObjectProperty<>();
      this.stockChange = new SimpleObjectProperty<>( 0.0 );
   }//End Constructor
   
   /**
    * Access to the date of the stock change.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< LocalDate > date() {
      return date;
   }//End Method

   /**
    * Access to the {@link FoodItem} the stock change is for.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< FoodItem > foodItem() {
      return foodItem;
   }//End Method

   /**
    * Access to the percentage stock change as sold.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > stockChange() {
      return stockChange;
   }//End Method

}//End Class
