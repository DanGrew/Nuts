/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.measurement;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * {@link NutrientValue} represents the value associated with a nutrient. This captures to coversion
 * between measurements.
 */
public class NutrientValue {

   private final ObjectProperty< Double > siGrams;
   
   /**
    * Constructsa new {@link NutrientValue} with the value in grams.
    * @param grams the grams of nutrient.
    */
   public NutrientValue( double grams ) {
      this.siGrams = new SimpleObjectProperty<>( grams );
   }//End Class

   /**
    * Access to the value in grams.
    * @return the value.
    */
   public double inGrams() {
      return siGrams.get();
   }//End Method

   /**
    * Access to the value in grams property.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > gramsProperty() {
      return siGrams;
   }//End Method

   /**
    * Setter for the value given a particular {@link NutrientMeasurement}.
    * @param measurement the {@link NutrientMeasurement}.
    * @param value the value in the given {@link NutrientMeasurement}.
    */
   public void setValue( NutrientMeasurement measurement, double value ) {
      siGrams.set( measurement.toSiGrams( value ) );
   }//End Method
   
   /**
    * Setter for the value in grams.
    * @param value the value.
    */
   public void setGrams( double value ) {
      siGrams.set( value );
   }//End Method

}//End Class
