package uk.dangrew.nuts.nutrients;

import java.util.Arrays;
import java.util.Collection;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;

public enum NutritionalUnit {

   Calories,
   
   Carbohydrate,
   Fat,
   Protein,
   
   Fibre,
   
   ;

   private static final Collection< NutritionalUnit > macros = Arrays.asList( Carbohydrate, Fat, Protein );
   
   public static Collection< NutritionalUnit > macros(){
      return macros;
   }//End Method
   
   public OptionalNutritionalUnit of( Food food ) {
      if ( food == null ) {
         return new OptionalNutritionalUnit();
      }
      
      return new OptionalNutritionalUnit( food.nutrition().of( this ) );
   }//End Method
   
   public OptionalNutritionalUnit of( Nutrition nutrition ) {
      if ( nutrition == null ) {
         return new OptionalNutritionalUnit();
      }
      
      return new OptionalNutritionalUnit( nutrition.of( this ) );
   }//End Method
   
}//End Enum
