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
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.nutrients.OptionalNutritionalUnit;

/**
 * The {@link MacroRatioCalculator} calculates the ratio of {@link MacroNutrient}s in {@link FoodProperties}
 * of the total weight within those {@link Nutrition}.
 */
public class MacroRatioCalculator {

   private Nutrition properties;
   private FoodAnalytics analytics;
   
   public void associate( Nutrition properties, FoodAnalytics analytics ) {
      if ( this.properties != null || this.analytics != null ) {
         throw new IllegalStateException( "Allredy associated." );
      }
      
      this.properties = properties;
      this.analytics = analytics;
      
      ChangeListener< Object > listener = ( s, o, n ) -> updateRatios();
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         unit.of( properties ).property().addListener( listener );
      }
      updateRatios();
   }//End Method
   
   /**
    * Method to update the ratios of {@link MacroNutrient}s.
    */
   private void updateRatios(){
      double total = 0;
      for ( NutritionalUnit unit : NutritionalUnit.macros() ) {
         total += unit.of( properties ).get();
      }
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         OptionalNutritionalUnit unitValue = unit.of( properties );
         double proportion = total == 0 ? 0 : unitValue.get() * 100 / total;
         unit.of( analytics ).set( proportion );
      }
   }//End Method

}//End Class
