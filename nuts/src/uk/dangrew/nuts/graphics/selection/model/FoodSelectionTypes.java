package uk.dangrew.nuts.graphics.selection.model;

import java.util.Comparator;

import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.nuts.food.Food;

public enum FoodSelectionTypes {
   
   All( 
            Comparators.stringExtractionComparater( f -> f.properties().nameProperty().get() ) 
   ),
   Carbohydrates( 
            Comparators.reverseComparator(
                     Comparators.doubleExtractionComparater( f -> f.foodAnalytics().carbohydratesRatioProperty().get() ) 
            )
   ),
   Fats( 
            Comparators.reverseComparator(
                     Comparators.doubleExtractionComparater( f -> f.foodAnalytics().fatsRatioProperty().get() ) 
            )
   ), 
   Protein( 
            Comparators.reverseComparator(
                     Comparators.doubleExtractionComparater( f -> f.foodAnalytics().proteinRatioProperty().get() ) 
            )
   );
   
   private final Comparator< Food > comparator;
   
   private FoodSelectionTypes( Comparator< Food > comparator ) {
      this.comparator = comparator;
   }//End Constructor
   
   public Comparator< Food > comparator(){
      return comparator;
   }//End Method

}//End Enum
