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
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

/**
 * {@link FoodItemParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing {@link FoodItem}s.
 */
class FoodItemParseModel {
   
   private final FoodItemStore store;
   
   private String id;
   private String name;
   private double loggedWeight;
   private double soldInWeight;
   private final Map< NutritionalUnit, Double > nutritionalUnitValues;
   
   FoodItemParseModel( FoodItemStore store ) {
      this.store = store;
      this.nutritionalUnitValues = new EnumMap<>( NutritionalUnit.class );
   }//End Constructor
   
   void startFoodItem() {
      this.id = null;
      this.name = null;
      this.nutritionalUnitValues.clear();
   }//End Method

   void finishFoodItem() {
      FoodItem item = store.get( id );
      if ( item == null ) {
         item = new FoodItem( id, name );
         store.store( item );
      }
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         item.nutrition().of( unit ).set( 
                  Optional.ofNullable( nutritionalUnitValues.get( unit ) ).orElse( 0.0 ) 
         );
      }
      item.stockProperties().setWeighting( loggedWeight, soldInWeight );
   }//End Method
   
   void setId( String value ) {
      this.id = value;
   }//End Method
   
   void setName( String value ) {
      this.name = value;
   }//End Method
   
   void setLoggedWeight( Double value ) {
      this.loggedWeight = value;
   }//End Method
   
   void setSoldInWeight( Double value ) {
      this.soldInWeight = value;
   }//End Method

   void setNutritionalUnit( NutritionalUnit unit, Double value ) {
      this.nutritionalUnitValues.put( unit, value );
   }//End Method
   
}//End Class
