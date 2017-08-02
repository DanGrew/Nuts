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
 * {@link FoodTableColumnsPopulator} provides an interface for populating a {@link FoodTable}.
 */
public interface FoodTableColumnsPopulator< FoodTypeT extends Food > {
   
   /**
    * Instructing to populate the {@link FoodTable} with the relevant {@link javafx.scene.control.TableColumn}s.
    * @param table the {@link FoodTable} to populate.
    */
   public void populateColumns( FoodTable< FoodTypeT > table );

}//End Interface

