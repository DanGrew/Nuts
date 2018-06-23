/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.fooditems;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.store.Database;

/**
 * {@link FoodItemParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing {@link FoodItem}s.
 */
class FoodItemParseModel {
   
   private final Database database;
   
   private String id;
   private String name;
   private double loggedWeight;
   private double soldInWeight;
   private final Map< NutritionalUnit, Double > nutritionalUnitValues;
   
   /**
    * Constructs a new {@link FoodItemParseModel}.
    * @param database the {@link Database}.
    */
   FoodItemParseModel( Database database ) {
      this.database = database;
      this.nutritionalUnitValues = new EnumMap<>( NutritionalUnit.class );
   }//End Constructor
   
   void startFoodItem() {
      this.id = null;
      this.name = null;
      this.nutritionalUnitValues.clear();
   }//End Method

   void finishFoodItem() {
      FoodItem item = database.foodItems().get( id );
      if ( item == null ) {
         item = new FoodItem( id, name );
         database.foodItems().store( item );
      }
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         item.properties().nutrition().of( unit ).set( 
                  Optional.ofNullable( nutritionalUnitValues.get( unit ) ).orElse( 0.0 ) 
         );
      }
      item.stockProperties().setWeighting( loggedWeight, soldInWeight );
   }//End Method
   
   void setId( String key, String value ) {
      this.id = value;
   }//End Method
   
   void setName( String key, String value ) {
      this.name = value;
   }//End Method

   @Deprecated void setCarbohydrates( String key, Double value ) {
      setNutritionalUnit( NutritionalUnit.Carbohydrate, value );
   }//End Method
   
   @Deprecated void setFats( String key, Double value ) {
      setNutritionalUnit( NutritionalUnit.Fat, value );
   }//End Method
   
   @Deprecated void setProtein( String key, Double value ) {
      setNutritionalUnit( NutritionalUnit.Protein, value );
   }//End Method
   
   @Deprecated void setCalories( String key, Double value ) {
      setNutritionalUnit( NutritionalUnit.Calories, value );
   }//End Method
   
   @Deprecated void setFiber( String key, Double value ) {
      setNutritionalUnit( NutritionalUnit.Fibre, value );
   }//End Method
   
   void setLoggedWeight( String key, Double value ) {
      this.loggedWeight = value;
   }//End Method
   
   void setSoldInWeight( String key, Double value ) {
      this.soldInWeight = value;
   }//End Method

   void setNutritionalUnit( NutritionalUnit unit, Double value ) {
      this.nutritionalUnitValues.put( unit, value );
   }//End Method
   
}//End Class
