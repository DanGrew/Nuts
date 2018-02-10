package uk.dangrew.nuts.graphics.selection;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Predicate;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.graphics.table.FilteredConceptOptions;
import uk.dangrew.nuts.stock.Stock;

public class FoodSelectionFilterApplier {
   
   private final FoodSelectionManager selectionManager;
   private final FilteredConceptOptions< Food > filteredOptions;
   private final Stock stock;
   
   private final Map< FoodSelectionFilters, Predicate< Food > > filterFunctions;
   
   public FoodSelectionFilterApplier( 
            FoodSelectionManager selectionManager, 
            FilteredConceptOptions< Food > filteredOptions,
            Stock stock
   ) {
      this.selectionManager = selectionManager;
      this.filteredOptions = filteredOptions;
      this.stock = stock;
      this.filterFunctions = new EnumMap<>( FoodSelectionFilters.class );
      this.filterFunctions.put( FoodSelectionFilters.Selection, this::selectionFilter );
      this.filterFunctions.put( FoodSelectionFilters.Stock, this::stockFilter );
   }//End Constructor

   public void applyFilters( FoodSelectionFilters... filters ) {
      applyFilters( Arrays.asList( filters ) );
   }//End Method
   
   public void applyFilters( Collection< FoodSelectionFilters > filters ) {
      filteredOptions.clearFilters();
      filters.forEach( f -> filteredOptions.applyFilter( filterFunctions.get( f ) ) );
   }//End Method
   
   private boolean selectionFilter( Food food ) {
      return !selectionManager.isSelected( food );
   }//End Method
   
   private boolean stockFilter( Food food ) {
      FoodPortion portion = stock.portionFor( food );
      if ( portion == null ) {
         return true;
      }
      
      return portion.portion().get() <= 0.0;
   }//End Method
   
}//End Class
