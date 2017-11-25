/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.food;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.FoodTable;
import uk.dangrew.nuts.graphics.table.FoodTableController;

public class UnresponsiveFoodTableController< FoodTypeT extends Food > implements FoodTableController< FoodTypeT > {

   @Override public void associate( FoodTable< FoodTypeT > table ) {}//End Method
   
   @Override public FoodTypeT createFood() {
      return null;
   }//End Method

   @Override public void removeSelectedFood() {}//End Method
   
   @Override public void copySelectedFood() {}//End Method
   
}//End Class
