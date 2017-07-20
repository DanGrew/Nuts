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
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * The {@link FoodPortion} wraps a {@link Food} item to portion it linearly, as a percentage.
 */
public class FoodPortion implements Food {
   
   private final ChangeListener< Double > macroUpdater;
   private final FoodProperties properties;
   
   private final ObjectProperty< Food > food;
   private final ObjectProperty< Double > portion;
   
   /**
    * Constructs a new {@link FoodPortion}.
    * @param food the {@link Food} immediately applied.
    * @param portion the portion immediately applied.
    */
   public FoodPortion( Food food, double portion ) {
      this();
      setFood( food );
      setPortion( portion );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodPortion}.
    */
   public FoodPortion() {
      this.food = new SimpleObjectProperty<>();
      this.portion = new SimpleObjectProperty<>( 100.0 );
      this.properties = new FoodProperties( "Portion" );
      this.macroUpdater = ( s, o, n ) -> updateMacros();
      this.portion.addListener( macroUpdater );
   }//End Constructor
   
   /**
    * Method to update the {@link MacroNutrient} values according to the portion.
    */
   private void updateMacros(){
      if ( food.get() == null ) {
         for ( MacroNutrient macro : MacroNutrient.values() ) {
            properties.nutritionFor( macro ).setGrams( 0.0 );
         }
         return;
      }
      
      double proportion = portion.get() / 100.0;
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         properties.nutritionFor( macro ).setGrams( food.get().properties().nutritionFor( macro ).inGrams() * proportion );
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
      return properties.nutritionFor( macro ).gramsProperty();
   }//End Method
   
   /**
    * Access to the ratio of the given {@link MacroNutrient}.
    * @param macro the {@link MacroNutrient} in question.
    * @return the {@link ObjectProperty}.
    */
   public ReadOnlyObjectProperty< Double > nutritionRatioFor( MacroNutrient macro ) {
      return properties.analytics().nutrientRatioFor( macro );
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

   /**
    * {@inheritDoc}
    */
   @Override public FoodProperties properties() {
      return properties;
   }//End Method

}//End Class
