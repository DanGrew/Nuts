package uk.dangrew.nuts.nutrients;

import java.util.Arrays;
import java.util.Collection;

import uk.dangrew.nuts.food.Food;

public enum NutritionalUnit {

   Calories,
   
   Carbohydrate,
   Fat,
   Protein,
   
   Fibre,
   
   Potassium,
   Magnesium,
   Sodium,
   Calcium,
   
   SaturatedFat( "Saturated Fat" ),
   CarbohydrateSugars( "Carbohydrate Sugars" ),
   Salt,
   Thiamin( "Thiamin (B1)" ),
   Riboflavin( "Riboflavin (B2)" ),
   Niacin( "Niacin (B3)" ),
   FolicAcid( "Folic Acid (B9)" ),
   Iron,
   Omega3
   
   ;

   private static final Collection< NutritionalUnit > macros = Arrays.asList( Carbohydrate, Fat, Protein );
   
   public static Collection< NutritionalUnit > macros(){
      return macros;
   }//End Method
   
   private final String displayName;
   
   private NutritionalUnit() {
      this.displayName = name();
   }//End Constructor
   
   private NutritionalUnit( String displayName ) {
      this.displayName = displayName;
   }//End Constructor
   
   public String displayName(){
      return displayName;
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
