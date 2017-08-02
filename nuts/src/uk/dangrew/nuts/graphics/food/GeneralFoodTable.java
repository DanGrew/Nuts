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
import uk.dangrew.nuts.food.FoodStore;
import uk.dangrew.nuts.graphics.table.FoodTable;

/**
 * {@link GeneralFoodTable} provides a {@link FoodTable} with a {@link GeneralFoodTableController}.
 */
public class GeneralFoodTable< FoodTypeT extends Food > extends FoodTable< FoodTypeT >{

   /**
    * Constructs a new {@link GeneralFoodTable}.
    * @param foods the {@link FoodStore} to display.
    */
   public GeneralFoodTable( FoodStore< FoodTypeT > foods ) {
      super( 
               new FoodTableColumns<>(),
               new GeneralFoodTableController<>( foods )
      );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public GeneralFoodTableController< FoodTypeT > controller() {
      return ( GeneralFoodTableController< FoodTypeT > ) super.controller();
   }//End Method

}//End Class
