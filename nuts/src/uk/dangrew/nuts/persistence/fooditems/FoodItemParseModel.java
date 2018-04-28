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
   private Double calories;
   private Double fiber;
   private double loggedWeight;
   private double soldInWeight;
   
   /**
    * Constructs a new {@link FoodItemParseModel}.
    * @param database the {@link Database}.
    */
   FoodItemParseModel( Database database ) {
      this.database = database;
   }//End Constructor
   
   void startFoodItem() {
      this.id = null;
      this.name = null;
      this.carbohydrates = null;
      this.fats = null;
      this.protein = null;
      this.calories = null;
      this.fiber = null;
   }//End Method

   void finishFoodItem() {
      if ( fiber == null ) {
         fiber = 0.0;
      }
      
      FoodItem item = database.foodItems().get( id );
      if ( item == null ) {
         item = new FoodItem( id, name );
         database.foodItems().store( item );
      }
      item.properties().setMacros( carbohydrates, fats, protein );
      item.properties().fiber().set( fiber );
      item.properties().calories().set( calories );
      item.stockProperties().setWeighting( loggedWeight, soldInWeight );
   }//End Method
   
   void setId( String key, String value ) {
      this.id = value;
   }//End Method
   
   void setName( String key, String value ) {
      this.name = value;
   }//End Method

   void setCarbohydrates( String key, Double value ) {
      this.carbohydrates = value;
   }//End Method
   
   void setFats( String key, Double value ) {
      this.fats = value;
   }//End Method
   
   void setProtein( String key, Double value ) {
      this.protein = value;
   }//End Method
   
   void setCalories( String key, Double value ) {
      this.calories = value;
   }//End Method
   
   void setFiber( String key, Double value ) {
      this.fiber = value;
   }//End Method
   
   void setLoggedWeight( String key, Double value ) {
      this.loggedWeight = value;
   }//End Method
   
   void setSoldInWeight( String key, Double value ) {
      this.soldInWeight = value;
   }//End Method
   
}//End Class
