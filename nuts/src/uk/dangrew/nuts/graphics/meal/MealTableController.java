package uk.dangrew.nuts.graphics.meal;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.ConceptTableController;
import uk.dangrew.nuts.meal.Meal;

public interface MealTableController extends ConceptTableController< FoodPortion >{
   
   /**
    * Method to show the given {@link Meal} in the {@link MealTable}.
    * @param meal the {@link Meal} to show.
    */
   public void showMeal( Meal meal );
   
   /**
    * Getter for the {@link Meal} being shown.
    * @return the {@link Meal} being shown.
    */
   public Meal getShowingMeal();
   
   public void moveUp();

   public void moveDown();
   
   public void openTab();

}//End Interface

