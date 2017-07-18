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
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * {@link FoodAnalytics} provides analytical information inferred from the properties such as
 * {@link MacroNutrient}s on the {@link Food}.
 */
public class FoodAnalytics {

   private Food food;
   private final Map< MacroNutrient, ObjectProperty< Double > > macroProportions;
   
   /**
    * Constructs a new {@link FoodAnalytics}.
    */
   public FoodAnalytics() {
      this.macroProportions = new EnumMap<>( MacroNutrient.class );
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         this.macroProportions.put( macro, new SimpleObjectProperty< Double >( 0.0 ) );
      }
   }//End Constructor
   
   /**
    * Associated the {@link FoodAnalytics} with the given {@link Food}.
    * @param food the {@link Food} to associated with.
    */
   public void associate( Food food ) {
      if ( this.food != null ) {
         throw new IllegalStateException( "Allredy associated." );
      }
      
      this.food = food;
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         food.nutritionFor( macro ).gramsProperty().addListener( ( s, o, n ) -> updateRatios() );
      }
      updateRatios();
   }//End Method
   
   /**
    * Method to update the ratios of {@link MacroNutrient}s.
    */
   private void updateRatios(){
      double total = 0;
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         total += food.nutritionFor( macro ).inGrams();
      }
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         double value = food.nutritionFor( macro ).inGrams();
         double proportion = total == 0 ? 0 : value * 100 / total;
         macroProportions.get( macro ).set( proportion );
      }
   }//End Method

   /**
    * Access to the ratio for the given {@link MacroNutrient} property.
    * @param macro the {@link MacroNutrient} in question.
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Double > nutrientRatioFor( MacroNutrient macro ) {
      return macroProportions.get( macro );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Carbohydrates} ratio property.
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Double > carbohydratesRatio() {
      return nutrientRatioFor( MacroNutrient.Carbohydrates );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Fats} ratio property.
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Double > fatsRatio() {
      return nutrientRatioFor( MacroNutrient.Fats );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Protein} ratio property.
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Double > proteinRatio() {
      return nutrientRatioFor( MacroNutrient.Protein );
   }//End Method

}//End Class
