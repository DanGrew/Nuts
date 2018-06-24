package uk.dangrew.nuts.nutrients;

import java.util.Arrays;
import java.util.Collection;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;

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
   
   public OptionalNutritionalUnit of( FoodProperties properties ) {
      if ( properties == null ) {
         return new OptionalNutritionalUnit();
      }
      
      return new OptionalNutritionalUnit( properties.nutrition().of( this ) );
   }//End Method
   
   public OptionalNutritionalUnit of( FoodAnalytics analytics ) {
      if ( analytics == null ) {
         return new OptionalNutritionalUnit();
      }
      
      return new OptionalNutritionalUnit( analytics.nutrition().of( this ) );
   }//End Method
   
   public OptionalNutritionalUnit of( Food food ) {
      if ( food == null ) {
         return new OptionalNutritionalUnit();
      }
      
      return of( food.properties() );
   }//End Method
   
}//End Enum
