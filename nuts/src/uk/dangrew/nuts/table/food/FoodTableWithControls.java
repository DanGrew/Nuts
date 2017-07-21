/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.food;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodStore;

/**
 * {@link FoodTableWithControls} wraps a {@link FoodTable} with some {@link FoodControls}.
 */
public class FoodTableWithControls< FoodTypeT extends Food > extends BorderPane {
   
   private final FoodTable< FoodTypeT > table;
   private final FoodControls controls;
   
   /**
    * Constructs a new {@link FoodTableWithControls}.
    * @param foods the {@link FoodStore} of data.
    */
   public FoodTableWithControls( FoodStore< FoodTypeT > foods ) {
      setCenter( table = new FoodTable<>( foods ) );
      setRight( controls = new FoodControls( table.controller() ) );
   }//End Constructor

   public FoodTable< FoodTypeT > table() {
      return table;
   }//End Method
   
   FoodControls controls(){
      return controls;
   }//End Method

}//End Class
