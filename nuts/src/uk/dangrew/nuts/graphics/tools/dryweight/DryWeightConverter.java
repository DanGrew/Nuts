/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.tools.dryweight;

import javafx.beans.value.ChangeListener;

/**
 * {@link DryWeightConverter} provides a tool for calculating the calories and weight when the nutritional
 * information on the packet is not clear - a common problem! such as on rice packets.
 */
public class DryWeightConverter {
   
   private DryWeightProperties properties;
   
   /**
    * Method to associate with the given {@link DryWeightProperties}, so they can be manipulated.
    * @param properties the {@link DryWeightProperties} to manipulate.
    */
   public void associate( DryWeightProperties properties ) {
      if ( this.properties != null ) {
         throw new IllegalStateException( "Already associated." );
      }
      this.properties = properties;
      
      ChangeListener< Double > scaleFactorTrigger = ( s, o, n ) -> calculateScaleFactors();
      this.properties.portionCookedWeight().addListener( scaleFactorTrigger );
      this.properties.portionDryWeight().addListener( scaleFactorTrigger );
      
      ChangeListener< Double > calculateDesiredDryWeightForCookedWeightTrigger = ( s, o, n ) -> calculateDryWeightForDesiredCookedWeight();
      this.properties.cookedToDryScaleFactor().addListener( calculateDesiredDryWeightForCookedWeightTrigger );
      this.properties.desiredCookedWeight().addListener( calculateDesiredDryWeightForCookedWeightTrigger );
      
      ChangeListener< Double > calculateDesiredDryWeightForCaloriesTrigger = ( s, o, n ) -> calculateDryWeightForDesiredCalories();
      this.properties.portionDryWeight().addListener( calculateDesiredDryWeightForCaloriesTrigger );
      this.properties.portionCalories().addListener( calculateDesiredDryWeightForCaloriesTrigger );
      this.properties.desiredCalories().addListener( calculateDesiredDryWeightForCaloriesTrigger );
   }//End Method
   
   /**
    * Calculation for the scale factors between dry and cooked weight.
    */
   private void calculateScaleFactors(){
      double cookedWeight = properties.portionCookedWeight().get();
      double dryWeight = properties.portionDryWeight().get();
      
      if ( cookedWeight == 0.0 || dryWeight == 0.0 ) {
         properties.cookedToDryScaleFactor().set( 0.0 );
         properties.dryToCookedScaleFactor().set( 0.0 );
         return;
      }
      
      double cookedToDry = dryWeight / cookedWeight;
      double dryToCooked = cookedWeight / dryWeight;
      
      properties.cookedToDryScaleFactor().set( cookedToDry );
      properties.dryToCookedScaleFactor().set( dryToCooked );
   }//End Method
   
   /**
    * Calculation for the required dry weight for the desired calories.
    */
   private void calculateDryWeightForDesiredCalories(){
      double portionCalories = properties.portionCalories().get();
      double dryWeight = properties.portionDryWeight().get();
      double desiredCookedCalories = properties.desiredCalories().get();
      
      if ( portionCalories == 0.0 ) {
         properties.calculatedDryWeightForDesiredCalories().set( 0.0 );
         return;
      }
      double caloriesToDesiredCalories = desiredCookedCalories / portionCalories;
      double desiredDryWeight = dryWeight * caloriesToDesiredCalories; 
      properties.calculatedDryWeightForDesiredCalories().set( desiredDryWeight );
   }//End Method
   
   /**
    * Calculation for the dry weight for the desired cooked weight.
    */
   private void calculateDryWeightForDesiredCookedWeight(){
      double cookedToDry = properties.cookedToDryScaleFactor().get();
      double desiredCookedWeight = properties.desiredCookedWeight().get();
      
      properties.calculatedDryWeightForDesiredCookedWeight().set( desiredCookedWeight * cookedToDry );
   }//End Method

}//End Class
