package uk.dangrew.nuts.graphics.selection.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Predicate;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.stock.Stock;

public class FoodSelectionFilterApplier {
   
   private final FoodSelectionManager selectionManager;
   private final FoodSelectionModel model;
   private final Stock stock;
   
   private final Map< FoodSelectionFilters, Predicate< Food > > filterFunctions;
   
   public FoodSelectionFilterApplier( 
            FoodSelectionManager selectionManager,
            FoodSelectionModel model,
            Stock stock
   ) {
      this.selectionManager = selectionManager;
      this.model = model;
      this.stock = stock;
      this.filterFunctions = new EnumMap<>( FoodSelectionFilters.class );
      this.filterFunctions.put( FoodSelectionFilters.Selection, this::selectionFilter );
      this.filterFunctions.put( FoodSelectionFilters.Stock, this::stockFilter );
      this.filterFunctions.put( FoodSelectionFilters.Labels, this::labelFilter );
      
      this.model.filters().addListener( new FunctionListChangeListenerImpl<>( 
               a -> applyFilters(), r -> applyFilters()
      ) );
      this.model.labels().addListener( new FunctionListChangeListenerImpl<>( 
               a -> applyFilters(), r -> applyFilters()
      ) );
   }//End Constructor

   private void applyFilters() {
      model.filteredConcepts().clearFilters();
      model.filters().forEach( f -> model.filteredConcepts().applyFilter( filterFunctions.get( f ) ) );
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
   
   private boolean labelFilter( Food food ) {
      if ( model.labels().isEmpty() ) {
         return false;
      }
      return model.labels().stream()
               .filter( l -> l.concepts().contains( food ) )
               .count() == 0;
   }//End Method
   
}//End Class
