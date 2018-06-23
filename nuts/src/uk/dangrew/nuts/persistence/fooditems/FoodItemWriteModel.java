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
import uk.dangrew.nuts.nutrients.NutritionalUnit;
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

   Integer getNumberOfFoodItems( String key ){
      return database.foodItems().objectList().size();
   }//End Method
   
   void startWritingFoodItems() {
      foodBuffer.clear();
      foodBuffer.addAll( database.foodItems().objectList() );
   }//End Method
   
   void startWritingFoodItem() {
      if ( foodBuffer.isEmpty() ) {
         return;
      }
      this.currentFood = foodBuffer.remove( 0 );
   }//End Method

   String getId() {
      return currentFood.properties().id();
   }//End Method

   String getName() {
      return currentFood.properties().nameProperty().get();
   }//End Method
   
   @Deprecated Double getCarbohydrates() {
      return currentFood.properties().carbohydrates().get();
   }//End Method
   
   @Deprecated Double getFats() {
      return currentFood.properties().fats().get();
   }//End Method
   
   @Deprecated Double getProtein() {
      return currentFood.properties().protein().get();
   }//End Method
   
   @Deprecated Double getCalories() {
      return currentFood.properties().calories().get();
   }//End Method
   
   @Deprecated Double getFiber() {
      return currentFood.properties().fiber().get();
   }//End Method
   
   Double getLoggedWeight() {
      return currentFood.stockProperties().loggedWeight().get();
   }//End Method
   
   Double getSoldInWeight() {
      return currentFood.stockProperties().soldInWeight().get();
   }//End Method

   public Double getNutritionalUnit( NutritionalUnit unit ) {
      return currentFood.properties().nutrition().of( unit ).get();
   }//End Method
   
}//End Class
