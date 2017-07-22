/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * The {@link MacroRatioCalculator} calculates the ratio of {@link MacroNutrient}s in {@link FoodProperties}
 * of the total weight within those {@link FoodProperties}.
 */
public class MacroRatioCalculator {

   private FoodProperties properties;
   private FoodAnalytics analytics;
   
   /**
    * Associate the {@link MacroRatioCalculator} with the given {@link FoodProperties} and {@link FoodAnalytics}.
    * @param properties the {@link FoodProperties}.
    * @param analytics the {@link FoodAnalytics}.
    */
   public void associate( FoodProperties properties, FoodAnalytics analytics ) {
      if ( this.properties != null || this.analytics != null ) {
         throw new IllegalStateException( "Allredy associated." );
      }
      
      this.properties = properties;
      this.analytics = analytics;
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         properties.nutritionFor( macro ).gramsProperty().addListener( ( s, o, n ) -> updateRatios() );
      }
      updateRatios();
   }//End Method
   
   /**
    * Method to update the ratios of {@link MacroNutrient}s.
    */
   private void updateRatios(){
      double total = 0;
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         total += properties.nutritionFor( macro ).inGrams();
      }
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         double value = properties.nutritionFor( macro ).inGrams();
         double proportion = total == 0 ? 0 : value * 100 / total;
         analytics.nutrientRatioFor( macro ).set( proportion );
      }
   }//End Method

}//End Class
