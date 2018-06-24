/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import uk.dangrew.nuts.nutrients.Nutrition;

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
   
}//End Class
