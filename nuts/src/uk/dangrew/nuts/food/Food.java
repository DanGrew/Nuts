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
import uk.dangrew.nuts.measurement.NutrientValue;
import uk.dangrew.nuts.nutrients.MacroNutrient;

/**
 * {@link Food} represents a single item of food and the properties of that food. The {@link Food}
 * has a size, but is not portioned and simply measured in some amount.
 */
public class Food {

   private final String id;
   private final ObjectProperty< String > nameProperty;
   private final EnumMap< MacroNutrient, NutrientValue > macros;
   private final FoodAnalytics analytics;
   
   /**
    * Constructs a new {@link Food}.
    * @param name the name of the {@link Food}.
    */
   public Food( String name ) {
      this( UUID.randomUUID().toString(), name, new FoodAnalytics() );
   }//End Constructor
   
   /**
    * Constructs a new {@link Food}.
    * @param id the unique id for the {@link Food}.
    * @param name the name of the {@link Food}.
    * @param analytics the {@link FoodAnalytics} supporting the properties.
    */
   public Food( String id, String name, FoodAnalytics analytics ) {
      this.id = id;
      this.nameProperty = new SimpleObjectProperty<>( name );
      this.macros = new EnumMap<>( MacroNutrient.class );
      for ( MacroNutrient macro : MacroNutrient.values() ) {
         this.macros.put( macro, new NutrientValue( 0 ) );
      }
      this.analytics = analytics;
      this.analytics.associate( this );
   }//End Constructor
   
   /**
    * Access to the unique id.
    * @return the id.
    */
   public String id(){
      return id;
   }//End Method

   /**
    * Access to the name property.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< String > nameProperty() {
      return nameProperty;
   }//End Method

   /**
    * Access to the {@link NutrientValue} for the {@link MacroNutrient} given.
    * @param nutrient the {@link MacroNutrient} in question.
    * @return the {@link NutrientValue}.
    */
   public NutrientValue nutritionFor( MacroNutrient nutrient ) {
      return macros.get( nutrient );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Carbohydrates} {@link NutrientValue}.
    * @return the {@link NutrientValue}.
    */
   public NutrientValue carbohydrates() {
      return nutritionFor( MacroNutrient.Carbohydrates );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Fats} {@link NutrientValue}.
    * @return the {@link NutrientValue}.
    */
   public NutrientValue fats() {
      return nutritionFor( MacroNutrient.Fats );
   }//End Method
   
   /**
    * Access to the {@link MacroNutrient#Protein} {@link NutrientValue}.
    * @return the {@link NutrientValue}.
    */
   public NutrientValue protein() {
      return nutritionFor( MacroNutrient.Protein );
   }//End Method

   /**
    * Access to the {@link FoodAnalytics} to support the information given in the properties.
    * @return the {@link FoodAnalytics}.
    */
   public FoodAnalytics analytics() {
      return analytics;
   }//End Method

}//End Class
