package uk.dangrew.nuts.graphics.database;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.meal.FoodHolder;

public class RecipeShareControllerImpl {

   private final RecipeSummaryWindow recipeWindow;
   
   public RecipeShareControllerImpl() {
      this( new RecipeSummaryWindow() );
   }//End Constructor
   
   RecipeShareControllerImpl( RecipeSummaryWindow window ) {
      this.recipeWindow = window;
   }//End Method

   public void share( Food selection ) {
      if ( selection == null ) {
         return;
      }
      
      if ( selection instanceof FoodHolder ) {
         recipeWindow.show( ( FoodHolder )selection );
      }
   }//End Method

}//End Class
