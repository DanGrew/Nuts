/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.table;

import uk.dangrew.nuts.food.Food;

/**
 * {@link FoodTableController} provides a controller interface for a {@link FoodTable}.
 */
public interface FoodTableController< FoodTypeT extends Food > {

   /**
    * Associated the {@link FoodTableController} with the {@link FoodTable}.
    * @param table the {@link FoodTable} to control.
    */
   public void associate( FoodTable< FoodTypeT > table );
   
   /**
    * Instruction to create a new {@link Food} of the associated type.
    * @return the created {@link Food}.
    */
   public FoodTypeT createFood();

   /**
    * Instruction to remove the currently selected {@link Food}.
    */
   public void removeSelectedFood();
   
}//End Interface

