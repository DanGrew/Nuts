package uk.dangrew.nuts.graphics.meal;

import uk.dangrew.nuts.meal.FoodHolder;

public interface MealTableController extends FoodHolderOperations{
   
   /**
    * Method to show the given {@link Meal} in the {@link MealTable}.
    * @param meal the {@link Meal} to show.
    */
   public void showMeal( FoodHolder meal );
   
   /**
    * Getter for the {@link Meal} being shown.
    * @return the {@link Meal} being shown.
    */
   public FoodHolder getShowingMeal();
   
}//End Interface

