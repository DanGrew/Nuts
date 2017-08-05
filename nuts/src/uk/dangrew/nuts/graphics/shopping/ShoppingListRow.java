/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.shopping;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.food.FoodItem;

/**
 * {@link ShoppingListRow} provides the data for a row in the {@link ShoppingListTable}.
 */
public class ShoppingListRow {
   
   private final FoodItem food;
   private final ObjectProperty< Double > requiredWeight;
   private final ObjectProperty< Double > toBuy;
   
   /**
    * Constructs a new {@link ShoppingListRow}.
    * @param food the {@link FoodItem} to display.
    * @param requiredWeight the {@link ObjectProperty} providing the required weight.
    * @param toBuy the {@link ObjectProperty} providing the number to buy.
    */
   public ShoppingListRow( 
            FoodItem food,
            ObjectProperty< Double > requiredWeight,
            ObjectProperty< Double > toBuy
   ) {
      this.food = food;
      this.requiredWeight = requiredWeight;
      this.toBuy = toBuy;
   }//End Constructor

   /**
    * Access to the {@link FoodItem}.
    * @return the {@link FoodItem}.
    */
   public FoodItem food(){
      return food;
   }//End Method
   
   /**
    * Access to the required weight.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > requiredWeight(){
      return requiredWeight;
   }//End Method
   
   /**
    * Access to the number to buy.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > toBuy(){
      return toBuy;
   }//End Method
   
}//End Class
