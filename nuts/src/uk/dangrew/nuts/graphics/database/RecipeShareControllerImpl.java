package uk.dangrew.nuts.graphics.database;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableRow;
import uk.dangrew.nuts.meal.Meal;

public class RecipeShareControllerImpl {

   private final RecipeSummaryWindow recipeWindow;
   
   public RecipeShareControllerImpl() {
      this( new RecipeSummaryWindow() );
   }//End Constructor
   
   RecipeShareControllerImpl( RecipeSummaryWindow window ) {
      this.recipeWindow = window;
   }//End Method

   public void share( ConceptTable< Food > table ) {
      ConceptTableRow< Food > selectedRow = table.getSelectionModel().getSelectedItem();
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
