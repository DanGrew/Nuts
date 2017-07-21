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
 * {@link MealViewWithControls} provides a {@link MealView} with {@link FoodControls}.
 */
public class MealViewWithControls extends BorderPane {

   private final MealView mealView;
   
   /**
    * Constructs a new {@link MealViewWithControls}.
    * @param database the {@link Database}.
    */
   public MealViewWithControls( Database database ) {
      setCenter( mealView = new MealView( database ) );
      setRight( new FoodControls( mealView.table().controller() ) );
   }//End Constructor

   /**
    * Access to the {@link MealView}.
    * @return the {@link MealView}.
    */
   public MealView mealView() {
      return mealView;
   }//End Method

}//End Class
