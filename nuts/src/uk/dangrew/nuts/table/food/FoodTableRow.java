/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.food;

import uk.dangrew.nuts.food.Food;

/**
 * {@link FoodTableRow} provides a custom {@link javafx.scene.control.TableRow} for 
 * holding {@link Food} information.
 */
public class FoodTableRow< FoodTypeT extends Food > {

   private final FoodTypeT food;
   
   /**
    * Constructs a new {@link FoodTableRow}.
    * @param food the {@link Food}.
    */
   public FoodTableRow( FoodTypeT food ) {
      this.food = food;
   }//End Constructor
   
   /**
    * Access to the {@link Food}.
    * @return the {@link Food}.
    */
   public FoodTypeT food(){
      return food;
   }//End Method
   
}//End Class
