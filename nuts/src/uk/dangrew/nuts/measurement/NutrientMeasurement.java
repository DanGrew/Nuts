/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.measurement;

/**
 * {@link NutrientMeasurement} represents the different measurements of a nutrient.
 */
public enum NutrientMeasurement {
   
   Grams;

   /**
    * Method to convert the given value in the current {@link NutrientMeasurement} to grams.
    * @param value the value to convert.
    * @return the value in grams.
    */
   public double toSiGrams( double value ) {
      return value;
   }//End Method

}//End Enum
