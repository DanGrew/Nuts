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
 * {@link FoodControlsInterface} provides an interface for {@link FoodControls} to push
 * requests to.
 */
public interface FoodControlsInterface< FoodTypeT extends Food > {
   
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

