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
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

/**
 * {@link FoodItemWriteModel} is responsible for handling the hooks for the {@link uk.dangrew.jupa.json.parse.JsonParser}
 * when writing {@link FoodItem}s.
 */
class FoodItemWriteModel {
   
   private final FoodItemStore store;
   private final List< FoodItem > foodBuffer;
   private FoodItem currentFood; 
   
   FoodItemWriteModel( FoodItemStore store ) {
      this.store = store;
      this.foodBuffer = new ArrayList<>();
   }//End Constructor

   Integer getNumberOfFoodItems( String key ){
      return store.objectList().size();
   }//End Method
   
   void startWritingFoodItems() {
      foodBuffer.clear();
      foodBuffer.addAll( store.objectList() );
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
      return NutritionalUnit.Carbohydrate.of( currentFood ).get();
   }//End Method
   
   @Deprecated Double getFats() {
      return NutritionalUnit.Fat.of( currentFood ).get();
   }//End Method
   
   @Deprecated Double getProtein() {
      return NutritionalUnit.Protein.of( currentFood ).get();
   }//End Method
   
   @Deprecated Double getCalories() {
      return NutritionalUnit.Calories.of( currentFood ).get();
   }//End Method
   
   @Deprecated Double getFiber() {
      return NutritionalUnit.Fibre.of( currentFood ).get();
   }//End Method
   
   Double getLoggedWeight() {
      return currentFood.stockProperties().loggedWeight().get();
   }//End Method
   
   Double getSoldInWeight() {
      return currentFood.stockProperties().soldInWeight().get();
   }//End Method

   public Double getNutritionalUnit( NutritionalUnit unit ) {
      return currentFood.nutrition().of( unit ).get();
   }//End Method
   
}//End Class
