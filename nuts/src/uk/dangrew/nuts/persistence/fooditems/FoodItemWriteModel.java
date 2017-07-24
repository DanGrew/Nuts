/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.fooditems;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.store.Database;

/**
 * {@link FoodItemWriteModel} is responsible for handling the hooks for the {@link uk.dangrew.jupa.json.parse.JsonParser}
 * when writing {@link FoodItem}s.
 */
class FoodItemWriteModel {
   
   private final Database database;
   private final List< FoodItem > foodBuffer;
   private FoodItem currentFood; 
   
   /**
    * Constructs a new {@link FoodItemWriteModel}.
    * @param database the {@link Database}.
    */
   FoodItemWriteModel( Database database ) {
      this.database = database;
      this.foodBuffer = new ArrayList<>();
   }//End Constructor
   
   /**
    * Provides the number of {@link FoodItem}s to write.
    * @param key the parsed key.
    * @return the number of items.
    */
   Integer getNumberOfFoodItems( String key ){
      return database.foodItems().objectList().size();
   }//End Method
   
   /**
    * Triggered when the writing of the {@link FoodItem}s begins, initialising the items to write.
    * @param key the parsed key.
    */
   void startWritingFoodItems( String key ) {
      foodBuffer.clear();
      foodBuffer.addAll( database.foodItems().objectList() );
   }//End Method
   
   /**
    * Triggered when starting a new {@link FoodItem}.
    * @param key the parsed key.
    */
   void startWritingFoodItem( String key ) {
      if ( foodBuffer.isEmpty() ) {
         return;
      }
      this.currentFood = foodBuffer.remove( 0 );
   }//End Method

   /**
    * Provides the next {@link uk.dangrew.nuts.food.FoodProperties#id()}.
    * @param key the parsed key.
    * @return the value.
    */
   String getId( String key ) {
      return currentFood.properties().id();
   }//End Method

   /**
    * Provides the next {@link uk.dangrew.nuts.food.FoodProperties#nameProperty()}.
    * @param key the parsed key.
    * @return the value.
    */
   String getName( String key ) {
      return currentFood.properties().nameProperty().get();
   }//End Method
   
   /**
    * Provides the next {@link uk.dangrew.nuts.food.FoodProperties#carbohydrates()}.
    * @param key the parsed key.
    * @return the value.
    */
   Double getCarbohydrates( String key ) {
      return currentFood.properties().carbohydrates().get();
   }//End Method
   
   /**
    * Provides the next {@link uk.dangrew.nuts.food.FoodProperties#fats()}.
    * @param key the parsed key.
    * @return the value.
    */
   Double getFats( String key ) {
      return currentFood.properties().fats().get();
   }//End Method
   
   /**
    * Provides the next {@link uk.dangrew.nuts.food.FoodProperties#protein()}.
    * @param key the parsed key.
    * @return the value.
    */
   Double getProtein( String key ) {
      return currentFood.properties().protein().get();
   }//End Method
   
   /**
    * Provides the next {@link uk.dangrew.nuts.food.FoodProperties#calories()}.
    * @param key the parsed key.
    * @return the value.
    */
   Double getCalories( String key ) {
      return currentFood.properties().calories().get();
   }//End Method
   
}//End Class
