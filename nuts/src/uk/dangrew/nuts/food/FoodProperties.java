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
import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.nuts.nutrients.MacroNutrient;
import uk.dangrew.nuts.system.Properties;

/**
 * {@link FoodProperties} the properties of a single food. The {@link FoodProperties}
 * has a size, but is not portioned and simply measured in some amount.
 */
public class FoodProperties extends Properties {

   private final ObjectProperty< Double > calories;
   private final EnumMap< MacroNutrient, ObjectProperty< Double > > macros;
   
   /**
    * Constructs a new {@link FoodProperties}.
    * @param name the name of the {@link FoodProperties}.
    */
   public FoodProperties( String name ) {
      this( UUID.randomUUID().toString(), name );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodProperties}.
    * @param id the unique id for the {@link FoodProperties}.
    * @param name the name of the {@link FoodProperties}.
    * @param analytics the {@link FoodAnalytics} supporting the properties.
    */
   public FoodProperties( String id, String name ) {
      super( id, name );
      this.calories = new SimpleObjectProperty<>( 0.0 );
      this.macros = new EnumMap<>( MacroNutrient.class );
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         this.macros.put( macro, new SimpleObjectProperty<>( 0.0 ) );
      }
   }//End Constructor
   
   /**
    * Access to the calories.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > calories() {
      return calories;
   }//End Method

   /**
    * Access to the {@link ObjectProperty} for the {@link MacroNutrient} given.
    * @param nutrient the {@link MacroNutrient} in question.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > nutritionFor( MacroNutrient nutrient ) {
      return macros.get( nutrient );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Carbohydrates} {@link ObjectProperty}.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > carbohydrates() {
      return nutritionFor( MacroNutrient.Carbohydrates );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Fats} {@link ObjectProperty}.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > fats() {
      return nutritionFor( MacroNutrient.Fats );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Protein} {@link ObjectProperty}.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > protein() {
      return nutritionFor( MacroNutrient.Protein );
   }//End Method

   /**
    * Method to set the {@link MacroNutrient} for convenience.
    * @param cGrams the {@link MacroNutrient#Carbohydrates} in grams.
    * @param fGrams the {@link MacroNutrient#Fats} in grams.
    * @param pGrams the {@link MacroNutrient#Protein} in grams.
    */
   public void setMacros( double cGrams, double fGrams, double pGrams ) {
      carbohydrates().set( cGrams );
      fats().set( fGrams );
      protein().set( pGrams );
   }//End Method

}//End Class
