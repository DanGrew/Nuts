/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.tools.dryweight;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * {@link DryWeightProperties} provides properties related to the calculations for the dry weight
 * cooked weight problem.
 */
public class DryWeightProperties {

   private final ObjectProperty< Double > portionDryWeight;
   private final ObjectProperty< Double > portionCookedWeight;
   private final ObjectProperty< Double > portionCalories;
   private final ObjectProperty< Double > cookedToDryScaleFactor;
   private final ObjectProperty< Double > dryToCookedScaleFactor;
   private final ObjectProperty< Double > desiredCookedCalories;
   private final ObjectProperty< Double > desiredCookedWeight;
   private final ObjectProperty< Double > calculatedDryWeightForDesiredCalories;
   private final ObjectProperty< Double > calculatedDryWeightForDesiredCookedWeight;
   
   /**
    * Constructs a new {@link DryWeightProperties}.
    */
   public DryWeightProperties() {
      this.portionDryWeight = new SimpleObjectProperty<>( 0.0 );
      this.portionCookedWeight = new SimpleObjectProperty<>( 0.0 );
      this.cookedToDryScaleFactor = new SimpleObjectProperty<>( 0.0 );
      this.dryToCookedScaleFactor = new SimpleObjectProperty<>( 0.0 );
      this.portionCalories = new SimpleObjectProperty<>( 0.0 );
      this.desiredCookedCalories = new SimpleObjectProperty<>( 0.0 );
      this.desiredCookedWeight = new SimpleObjectProperty<>( 0.0 );
      this.calculatedDryWeightForDesiredCalories = new SimpleObjectProperty<>( 0.0 );
      this.calculatedDryWeightForDesiredCookedWeight = new SimpleObjectProperty<>( 0.0 );
   }//End Constructor
   
   /**
    * Access to the dry weight for the portion given in the nutritional values.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > portionDryWeight() {
      return portionDryWeight;
   }//End Method

   /**
    * Access to the cooked weight for the portion given in the nutritional values.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > portionCookedWeight() {
      return portionCookedWeight;
   }//End Method

   /**
    * Access to the calories for the portion given in the nutritional values.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > portionCalories() {
      return portionCalories;
   }//End Method
   
   /**
    * Access to the scale factor from dry weight to cooked weight for the portion given in the nutritional values.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > dryToCookedScaleFactor() {
      return dryToCookedScaleFactor;
   }//End Method

   /**
    * Access to the scale factor from cooked weight to dry weight for the portion given in the nutritional values.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > cookedToDryScaleFactor() {
      return cookedToDryScaleFactor;
   }//End Method

   /**
    * Access to the desired calories input.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > desiredCalories() {
      return desiredCookedCalories;
   }//End Method
   
   /**
    * Access to the calculated dry weight for input desired calories.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > calculatedDryWeightForDesiredCalories() {
      return calculatedDryWeightForDesiredCalories;
   }//End Method

   /**
    * Access to the desired cooked weight input.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > desiredCookedWeight() {
      return desiredCookedWeight;
   }//End Method
   
   /**
    * Access to the calculated dry weight for input desired cooked weight.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > calculatedDryWeightForDesiredCookedWeight() {
      return calculatedDryWeightForDesiredCookedWeight;
   }//End Method

}//End Class
