package uk.dangrew.nuts.graphics.selection.model;

import java.util.LinkedHashSet;
import java.util.Set;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;

public class FoodSelectionManager {
   
   private final Set< Food > selectedFoods;
   private final Set< FoodPortion > selectedPortions;
   
   public FoodSelectionManager() {
      this.selectedPortions = new LinkedHashSet<>();
      this.selectedFoods = new LinkedHashSet<>();
   }//End Constructor
   
   public boolean isSelected( FoodPortion portion ) {
      return selectedPortions.contains( portion );
   }//End Method
   
   public boolean isSelected( Food food ) {
      return selectedFoods.contains( food );
   }//End Method
   
   public boolean select( FoodPortion portion ) {
      if ( selectedPortions.add( portion ) ) {
         selectedFoods.add( portion.food().get() );
         return true;
      }
      return false;
   }//End Method

   public boolean deselect( FoodPortion portion ) {
      if ( selectedPortions.remove( portion ) ) {
         selectedFoods.remove( portion.food().get() );
         return true;
      }
      return false;
   }//End Method
   
   public Set< FoodPortion > getAndClearSelection() {
      Set< FoodPortion > copy = new LinkedHashSet<>( selectedPortions );
      selectedPortions.clear();
      return copy;
   }//End Method

}//End Class
