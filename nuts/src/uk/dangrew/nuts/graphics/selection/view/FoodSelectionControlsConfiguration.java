package uk.dangrew.nuts.graphics.selection.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import uk.dangrew.nuts.graphics.selection.model.FoodFilters;

public class FoodSelectionControlsConfiguration {

   private final Set< FoodFilters > filters;
   private boolean allowReverseSorting;
   
   public FoodSelectionControlsConfiguration() {
      this.filters = new LinkedHashSet<>();
      this.allowReverseSorting = true;
      this.withAllFilters();
   }//End Constructor
   
   public FoodSelectionControlsConfiguration withoutReverseSorting() {
      this.allowReverseSorting = false;
      return this;
   }//End Method
   
   public boolean allowReverseSorting(){
      return allowReverseSorting;
   }//End Method
   
   public FoodSelectionControlsConfiguration withoutFilter( FoodFilters filter ) {
      this.filters.remove( filter );
      return this;
   }//End Method

   public Collection< FoodFilters > filtersAllowed() {
      return filters;
   }//End Method

   public FoodSelectionControlsConfiguration withAllFilters() {
      this.filters.addAll( Arrays.asList( FoodFilters.values() ) );
      return this;
   }//End Method
   
}//End Class
