package uk.dangrew.nuts.graphics.selection.model;

import java.util.Comparator;

import uk.dangrew.kode.comparator.Comparators;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public enum FoodSelectionTypes {
   
   All( 
            Comparators.stringExtractionComparater( f -> f.properties().nameProperty().get() ) 
   ),
   Carbohydrates( 
            Comparators.reverseComparator(
                     Comparators.doubleExtractionComparater( f -> f.foodAnalytics().nutrition().of( NutritionalUnit.Carbohydrate ).get() ) 
            )
   ),
   Fats( 
            Comparators.reverseComparator(
                     Comparators.doubleExtractionComparater( f -> f.foodAnalytics().nutrition().of( NutritionalUnit.Fat ).get() ) 
            )
   ), 
   Protein( 
            Comparators.reverseComparator(
                     Comparators.doubleExtractionComparater( f -> f.foodAnalytics().nutrition().of( NutritionalUnit.Protein ).get() ) 
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
