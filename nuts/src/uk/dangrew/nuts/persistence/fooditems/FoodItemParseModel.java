/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.fooditems;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.store.Database;

/**
 * {@link FoodItemParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing {@link FoodItem}s.
 */
class FoodItemParseModel {
   
   private final Database database;
   
   private String id;
   private String name;
   private Double carbohydrates;
   private Double fats;
   private Double protein;
   
   /**
    * Constructs a new {@link FoodItemParseModel}.
    * @param database the {@link Database}.
    */
   FoodItemParseModel( Database database ) {
      this.database = database;
   }//End Constructor
   
   /**
    * Triggered when starting a new {@link FoodItem}.
    * @param key the parsed key.
    */
   void startFoodItem( String key ) {
      this.id = null;
      this.name = null;
      this.carbohydrates = null;
      this.fats = null;
      this.protein = null;
   }//End Method
   
   /**
    * Triggered when all values of a {@link FoodItem} have been parsed.
    * @param key the parsed key.
    */
   void finishFoodItem( String key ) {
      FoodItem item = new FoodItem( id, name );
      item.properties().setMacros( carbohydrates, fats, protein );
      database.foodItems().store( item );
   }//End Method
   
   /**
    * Sets the {@link uk.dangrew.nuts.food.FoodProperties#id()} for the current {@link FoodItem}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setId( String key, String value ) {
      this.id = value;
   }//End Method
   
   /**
    * Sets the {@link uk.dangrew.nuts.food.FoodProperties#nameProperty()} for the current {@link FoodItem}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setName( String key, String value ) {
      this.name = value;
   }//End Method

   /**
    * Sets the {@link uk.dangrew.nuts.food.FoodProperties#carbohydrates()} for the current {@link FoodItem}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setCarbohydrates( String key, Double value ) {
      this.carbohydrates = value;
   }//End Method
   
   /**
    * Sets the {@link uk.dangrew.nuts.food.FoodProperties#fats()} for the current {@link FoodItem}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setFats( String key, Double value ) {
      this.fats = value;
   }//End Method
   
   /**
    * Sets the {@link uk.dangrew.nuts.food.FoodProperties#protein()} for the current {@link FoodItem}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setProtein( String key, Double value ) {
      this.protein = value;
   }//End Method
   
}//End Class