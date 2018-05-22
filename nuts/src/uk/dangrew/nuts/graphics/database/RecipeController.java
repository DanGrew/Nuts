package uk.dangrew.nuts.graphics.database;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.selection.model.FoodModel;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class RecipeController extends MixedFoodTableController {

   private final RecipeSummaryWindow recipeWindow;
   
   public RecipeController( Database database, FoodModel model ) {
      this( new RecipeSummaryWindow(), database, model );
   }//End Constructor
   
   RecipeController( RecipeSummaryWindow window, Database database, FoodModel model ) {
      super( database, model );
      this.recipeWindow = window;
   }//End Method

   public void share() {
      ConceptTableRow< Food > selectedRow = table().getSelectionModel().getSelectedItem();
      if ( selectedRow == null ) {
         return;
      }
      
      Food selection = selectedRow.concept();
      if ( selection == null ) {
         return;
      }
      
      if ( selection instanceof Meal ) {
         recipeWindow.show( ( Meal )selection );
      }
   }//End Method

}//End Class
