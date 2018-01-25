package uk.dangrew.nuts.graphics.selection;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;

public class UiFoodSelectionController {

   private final Meal meal;
   
   public UiFoodSelectionController( Meal meal ) {
      this.meal = meal;
   }//End Constructor

   public void addPortion( FoodPortion portion ) {
      meal.portions().add( portion.duplicate( "" ) );
   }//End Method

}//End Class
