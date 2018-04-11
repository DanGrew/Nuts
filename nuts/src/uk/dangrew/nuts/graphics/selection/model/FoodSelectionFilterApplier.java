package uk.dangrew.nuts.graphics.selection.model;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.stock.Stock;

public class FoodSelectionFilterApplier extends FoodFilterApplier {
   
   private final FoodSelectionManager selectionManager;
   
   public FoodSelectionFilterApplier( 
            FoodSelectionManager selectionManager,
            FoodFilterModel model,
            Stock stock
   ) {
      super( model, stock );
      this.selectionManager = selectionManager;
      this.applyFilter( FoodFilters.Selection, this::selectionFilter );
   }//End Constructor

   private boolean selectionFilter( Food food ) {
      return !selectionManager.isSelected( food );
   }//End Method
   
}//End Class
