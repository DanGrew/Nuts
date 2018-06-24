/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.nutrients.MacroNutrient;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

/**
 * {@link FoodAnalytics} provides analytical information inferred from the properties such as
 * {@link MacroNutrient}s on the {@link Food}.
 */
public class FoodAnalytics {

   private final Nutrition nutrition;
   
   /**
    * Constructs a new {@link FoodAnalytics}.
    */
   public FoodAnalytics() {
      this.nutrition = new Nutrition();
   }//End Constructor
   
   public Nutrition nutrition(){
      return nutrition;
   }//End Method
   
   /**
    * Access to the ratio for the given {@link MacroNutrient} property.
    * @param macro the {@link MacroNutrient} in question.
    * @return the {@link ObjectProperty}.
    */
   @Deprecated public ObjectProperty< Double > nutrientRatioFor( MacroNutrient macro ) {
      switch ( macro ) {
         case Carbohydrates:
            return nutrition.of( NutritionalUnit.Carbohydrate );
         case Fats:
            return nutrition.of( NutritionalUnit.Fat );
         case Protein:
            return nutrition.of( NutritionalUnit.Protein );
         default:
            break;
      }
      return null;
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Carbohydrates} ratio property.
    * @return the {@link ObjectProperty}.
    */
   @Deprecated public ObjectProperty< Double > carbohydratesRatioProperty() {
      return nutrientRatioFor( MacroNutrient.Carbohydrates );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Fats} ratio property.
    * @return the {@link ObjectProperty}.
    */
   @Deprecated public ObjectProperty< Double > fatsRatioProperty() {
      return nutrientRatioFor( MacroNutrient.Fats );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Protein} ratio property.
    * @return the {@link ObjectProperty}.
    */
   @Deprecated public ObjectProperty< Double > proteinRatioProperty() {
      return nutrientRatioFor( MacroNutrient.Protein );
   }//End Method
   
   @Deprecated public ObjectProperty< Double > fiberRatioProperty() {
      return nutrition.of( NutritionalUnit.Fibre );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Carbohydrates} ratio.
    * @return the value.
    */
   @Deprecated public double carbohydratesRatio() {
      return nutrientRatioFor( MacroNutrient.Carbohydrates ).get();
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Fats} ratio.
    * @return the value.
    */
   @Deprecated public double fatsRatio() {
      return nutrientRatioFor( MacroNutrient.Fats ).get();
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Protein} ratio.
    * @return the value.
    */
   @Deprecated public double proteinRatio() {
      return nutrientRatioFor( MacroNutrient.Protein ).get();
   }//End Method

}//End Class
