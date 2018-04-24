package uk.dangrew.nuts.graphics.selection.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Predicate;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.stock.Stock;

public class FoodFilterApplier {
   
   private final FoodFilterModel model;
   private final Stock stock;
   
   private final Map< FoodFilters, Predicate< Food > > filterFunctions;
   
   public FoodFilterApplier( 
            FoodFilterModel model,
            Stock stock
   ) {
      this.model = model;
      this.stock = stock;
      this.filterFunctions = new EnumMap<>( FoodFilters.class );
      this.applyFilter( FoodFilters.Stock, this::stockFilter );
      this.applyFilter( FoodFilters.Labels, this::labelFilter );
      
      this.model.filters().addListener( new FunctionListChangeListenerImpl<>( 
               a -> applyFilters(), r -> applyFilters()
      ) );
      this.model.labels().addListener( new FunctionListChangeListenerImpl<>( 
               a -> applyFilters(), r -> applyFilters()
      ) );
   }//End Constructor
   
   protected void applyFilter( FoodFilters filter, Predicate< Food > function ) {
      this.filterFunctions.put( filter, function );
   }//End Method

   private void applyFilters() {
      model.filteredConcepts().clearFilters();
      model.filters().forEach( f -> model.filteredConcepts().applyFilter( filterFunctions.get( f ) ) );
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
               .count() > 0;
   }//End Method
   
}//End Class
