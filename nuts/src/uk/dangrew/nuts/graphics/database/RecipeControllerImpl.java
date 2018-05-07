package uk.dangrew.nuts.graphics.database;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.meal.RecipeController;
import uk.dangrew.nuts.graphics.selection.model.FoodModel;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

public class RecipeControllerImpl extends MixedFoodTableController implements RecipeController {

   private final RecipeSummaryWindow recipeWindow;
   
   public RecipeControllerImpl( Database database, FoodModel model ) {
      this( new RecipeSummaryWindow(), database, model );
   }//End Constructor
   
   RecipeControllerImpl( RecipeSummaryWindow window, Database database, FoodModel model ) {
      super( database, model );
      this.recipeWindow = window;
   }//End Method

   @Override public void share() {
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
