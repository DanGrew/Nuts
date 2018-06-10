package uk.dangrew.nuts.nutrients;

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodProperties;

public enum NutritionalUnit {

   Calories,
   
   Carbohydrate,
   Fat,
   Protein,
   
   Fibre,
   
   ;

   public OptionalNutritionalUnit of( FoodProperties properties ) {
      if ( properties == null ) {
         return new OptionalNutritionalUnit();
      }
      
      return new OptionalNutritionalUnit( properties.nutrition().of( this ) );
   }//End Method
   
   public OptionalNutritionalUnit of( Food food ) {
      if ( food == null ) {
         return new OptionalNutritionalUnit();
      }
      
      return of( food.properties() );
   }//End Method
   
}//End Enum
