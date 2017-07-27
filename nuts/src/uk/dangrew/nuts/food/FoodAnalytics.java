/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import java.util.EnumMap;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * {@link FoodAnalytics} provides analytical information inferred from the properties such as
 * {@link MacroNutrient}s on the {@link Food}.
 */
public class FoodAnalytics {

   private final ObjectProperty< Double > calorieDensity;
   private final Map< MacroNutrient, ObjectProperty< Double > > macroRatios;
   
   /**
    * Constructs a new {@link FoodAnalytics}.
    */
   public FoodAnalytics() {
      this.calorieDensity = new SimpleObjectProperty<>( 0.0 );
      this.macroRatios = new EnumMap<>( MacroNutrient.class );
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         this.macroRatios.put( macro, new SimpleObjectProperty< Double >( 0.0 ) );
      }
   }//End Constructor
   
   /**
    * Access to the ratio for the given {@link MacroNutrient} property.
    * @param macro the {@link MacroNutrient} in question.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > nutrientRatioFor( MacroNutrient macro ) {
      return macroRatios.get( macro );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Carbohydrates} ratio property.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > carbohydratesRatioProperty() {
      return nutrientRatioFor( MacroNutrient.Carbohydrates );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Fats} ratio property.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > fatsRatioProperty() {
      return nutrientRatioFor( MacroNutrient.Fats );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Protein} ratio property.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > proteinRatioProperty() {
      return nutrientRatioFor( MacroNutrient.Protein );
   }//End Method
   
   /**
    * Access to the calorie density {@link ObjectProperty}.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > calorieDensityProperty() {
      return calorieDensity;
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Carbohydrates} ratio.
    * @return the value.
    */
   public double carbohydratesRatio() {
      return nutrientRatioFor( MacroNutrient.Carbohydrates ).get();
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Fats} ratio.
    * @return the value.
    */
   public double fatsRatio() {
      return nutrientRatioFor( MacroNutrient.Fats ).get();
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Protein} ratio.
    * @return the value.
    */
   public double proteinRatio() {
      return nutrientRatioFor( MacroNutrient.Protein ).get();
   }//End Method

   /**
    * Access to the calorie density value.
    * @return the {@link ObjectProperty}.
    */
   public double calorieDensity() {
      return calorieDensity.get();
   }//End Method

}//End Class
