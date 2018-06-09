package uk.dangrew.nuts.graphics.selection.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
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
      this.applyFilter( FoodFilters.Default, this::isNotAndDoesNotContainFoodMatchingFilter );
      this.applyFilter( FoodFilters.Stock, this::stockFilter );
      this.applyFilter( FoodFilters.Labels, this::labelFilter );
      this.applyFilter( FoodFilters.NameOnly, this::doesNotMatchNameExactly );
      
      this.model.filters().addListener( new FunctionListChangeListenerImpl<>( 
               a -> applyFilters(), r -> applyFilters()
      ) );
      this.model.labels().addListener( new FunctionListChangeListenerImpl<>( 
               a -> applyFilters(), r -> applyFilters()
      ) );
      
      this.applyFilters();
   }//End Constructor
   
   protected void applyFilter( FoodFilters filter, Predicate< Food > function ) {
      this.filterFunctions.put( filter, function );
   }//End Method

   private void applyFilters() {
      model.filteredConcepts().clearFilters();
      model.filteredConcepts().applyFilter( filterFunctions.get( FoodFilters.Default ) );
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
   
   private boolean doesNotMatchNameExactly( Food food ) {
      String filter = model.filteredConcepts().filterString().get();
      if ( filter == null ) {
         return false;
      }
      String name = food.properties().nameProperty().get();
      return !name.toLowerCase().contains( filter.toLowerCase() );
   }//End Method
   
   private boolean isNotAndDoesNotContainFoodMatchingFilter( Food food ) {
      String filter = model.filteredConcepts().filterString().get();
      if ( filter == null ) {
         return false;
      }
      if ( !doesNotMatchNameExactly( food ) ) {
         return false;
      }
      if ( !( food instanceof Meal ) ) {
         return true;
      }
      
      Meal meal = ( Meal )food;
      return !containsIngredientsMatchingFilter( meal );
   }//End Method
   
   private boolean containsIngredientsMatchingFilter( Meal meal ) {
      return meal.portions().stream()
         .map( p -> p.food().get() )
         .filter( Objects::nonNull )
         .anyMatch( f -> !isNotAndDoesNotContainFoodMatchingFilter( f ) );
   }//End Method
   
}//End Class
