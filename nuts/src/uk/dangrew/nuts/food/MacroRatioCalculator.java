/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.food;

import javafx.beans.value.ChangeListener;
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
      
      ChangeListener< Object > listener = ( s, o, n ) -> updateRatios();
      properties.calories().addListener( listener );
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         properties.nutritionFor( macro ).addListener( listener );
      }
      updateRatios();
   }//End Method
   
   /**
    * Method to update the ratios of {@link MacroNutrient}s.
    */
   private void updateRatios(){
      double total = 0;
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         total += properties.nutritionFor( macro ).get();
      }
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         double value = properties.nutritionFor( macro ).get();
         double proportion = total == 0 ? 0 : value * 100 / total;
         analytics.nutrientRatioFor( macro ).set( proportion );
      }
      if ( properties.calories().get() == 0.0 ) {
         analytics.calorieDensityProperty().set( 0.0 );
      } else {
         double calorieDensity = total * 100.0 / properties.calories().get();
         analytics.calorieDensityProperty().set( calorieDensity );
      }
   }//End Method

}//End Class
