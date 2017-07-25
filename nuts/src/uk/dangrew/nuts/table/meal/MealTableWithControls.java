/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.table.meal;

import javafx.scene.layout.BorderPane;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.table.food.FoodControls;

/**
 * {@link MealTableWithControls} provides a {@link MealTable} with {@link FoodControls}.
 */
public class MealTableWithControls extends BorderPane {

   private final MealTable mealTable;
   
   /**
    * Constructs a new {@link MealTableWithControls}.
    * @param database the {@link Database}.
    */
   public MealTableWithControls( Database database ) {
      setCenter( mealTable = new MealTable( database ) );
      setRight( new FoodControls( mealTable.controller() ) );
   }//End Constructor

   /**
    * Access to the {@link MealTable}.
    * @return the {@link MealTable}.
    */
   public MealTable mealTable() {
      return mealTable;
   }//End Method

}//End Class
