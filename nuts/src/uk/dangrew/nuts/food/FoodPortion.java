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
import javafx.beans.value.ChangeListener;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * The {@link FoodPortion} wraps a {@link Food} item to portion it linearly, as a percentage.
 */
public class FoodPortion {
   
   private final ChangeListener< Double > macroUpdater;
   
   private final ObjectProperty< Food > food;
   private final ObjectProperty< Double > portion;
   private final Map< MacroNutrient, ObjectProperty< Double > > macros;
   private final Map< MacroNutrient, ObjectProperty< Double > > macroRatios;
   
   /**
    * Constructs a new {@link FoodPortion}.
    */
   public FoodPortion() {
      this.food = new SimpleObjectProperty<>();
      this.portion = new SimpleObjectProperty<>( 100.0 );
      this.macroUpdater = ( s, o, n ) -> updateMacros();
      
      this.macros = new EnumMap<>( MacroNutrient.class );
      this.macroRatios = new EnumMap<>( MacroNutrient.class );
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         this.macros.put( macro, new SimpleObjectProperty<>( 0.0 ) );
         this.macroRatios.put( macro, new SimpleObjectProperty<>( 0.0  ) );
      }
      
      this.portion.addListener( macroUpdater );
   }//End Constructor
   
   /**
    * Method to update the {@link MacroNutrient} values according to the portion.
    */
   private void updateMacros(){
      if ( food.get() == null ) {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            this.macros.get( macro ).set( 0.0 );
            this.macroRatios.get( macro ).set( 0.0 );
         }
         return;
      }
      
      double proportion = portion.get() / 100.0;
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         this.macros.get( macro ).set( food.get().properties().nutritionFor( macro ).inGrams() * proportion );
         this.macroRatios.get( macro ).set( food.get().properties().analytics().nutrientRatioFor( macro ).get() );
      }
   }//End Method
   
   /**
    * Setter for the {@link Food} in the {@link FoodPortion}.
    * @param food the {@link Food} to portion.
    */
   public void setFood( Food food ) {
      detachFromFood();
      attachToFood( food );
      updateMacros();
   }//End Method

   /**
    * Access to the {@link Food} property
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Food > food() {
      return food;
   }//End Method

   /**
    * Access to the portion property
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Double > portion() {
      return portion;
   }//End Method

   /**
    * Setter for the portion, lower bound of 0.
    * @param portion the portion.
    */
   public void setPortion( double portion ) {
      if ( portion < 0 ) {
         portion = 0;
      }
      
      this.portion.set( portion );
   }//End Method

   /**
    * Access to the portioned {@link MacroNutrient} property.
    * @param macro the {@link MacroNutrient} in question.
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Double > nutritionFor( MacroNutrient macro ) {
      return macros.get( macro );
   }//End Method
   
   /**
    * Access to the ratio of the given {@link MacroNutrient}.
    * @param macro the {@link MacroNutrient} in question.
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Double > nutritionRatioFor( MacroNutrient macro ) {
      return macroRatios.get( macro );
   }//End Method
   
   /**
    * Method to detach listeners from the current {@link Food}.
    */
   private void detachFromFood(){
      if ( food.get() == null ) {
         return;
      }
      
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         this.food.get().properties().nutritionFor( macro ).gramsProperty().removeListener( macroUpdater );
      }
   }//End Method
   
   /**
    * Method to attach listeners to the given {@link Food}.
    * @param food the {@link Food} to attach to.
    */
   private void attachToFood( Food food ){
      this.food.set( food );
      
      if ( food == null ) {
         return;
      }
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         food.properties().nutritionFor( macro ).gramsProperty().addListener( macroUpdater );
      }
   }//End Method

}//End Class
