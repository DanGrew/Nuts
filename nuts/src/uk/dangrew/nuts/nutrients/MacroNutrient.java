/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.nutrients;

/**
 * Enum representing the different types of {@link MacroNutrient}.
 */
public enum MacroNutrient {
   
   Carbohydrates,
   Fats,
   Protein;
   
   public NutritionalUnit toUnit() {
      switch ( this ) {
         case Carbohydrates:
            return NutritionalUnit.Carbohydrate;
         case Fats:
            return NutritionalUnit.Fat;
         case Protein:
            return NutritionalUnit.Protein;
      }
      return null;
   }//End Method

}//End Enum
