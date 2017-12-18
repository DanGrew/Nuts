/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.meals;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.system.ConceptStore;

/**
 * {@link MealWriteModel} is responsible for handling the hooks for the {@link uk.dangrew.jupa.json.parse.JsonParser}
 * when writing {@link Meal}s.
 */
public class MealWriteModel< FoodTypeT extends Meal > {
   
   private final ConceptStore< FoodTypeT > meals;
   private final List< FoodTypeT > foodBuffer;
   private final List< FoodPortion > portionBuffer;
   private FoodTypeT currentFood;
   private FoodPortion currentPortion; 
   
   /**
    * Constructs a new {@link FoodItemWriteModel}.
    * @param meals the {@link ConceptStore} providing the {@link Meal}s.
    */
   protected MealWriteModel( ConceptStore< FoodTypeT > meals ) {
      this.meals = meals;
      this.foodBuffer = new ArrayList<>();
      this.portionBuffer = new ArrayList<>();
      
      foodBuffer.clear();
      portionBuffer.clear();
   }//End Constructor
   
   protected ConceptStore< FoodTypeT > meals(){
      return meals;
   }//End Method
   
   /**
    * Access to the current {@link Meal} being written.
    * @return the current.
    */
   protected FoodTypeT currentFood(){
      return currentFood;
   }//End Method
   
   protected FoodPortion currentFoodPortion(){
      return currentPortion;
   }//End Method
   
   protected List< FoodTypeT > buffer(){
      return foodBuffer;
   }//End Method
   
   /**
    * Provides the number of {@link Meal}s to write.
    * @param key the parsed key.
    * @return the number of items.
    */
   protected Integer getNumberOfMeals( String key ){
      foodBuffer.clear();
      foodBuffer.addAll( meals.objectList() );
      return foodBuffer.size();
   }//End Method
   
   /**
    * Provides the number of {@link FoodPortion}s to write for the next {@link FoodPortion}.
    * @param key the parsed key.
    * @return the number of items.
    */
   Integer getNumberOfPortions( String key ){
      return foodBuffer.remove( 0 ).portions().size();
   }//End Method
   
   /**
    * Triggered when starting to write all {@link Meal}, identifying which to write.
    * @param key the parsed key.
    */
   protected void startWritingMeals( String key ) {
      foodBuffer.clear();
      foodBuffer.addAll( meals.objectList() );
   }//End Method
   
   /**
    * Triggered when starting a new {@link Meal}.
    * @param key the parsed key.
    */
   void startWritingMeal( String key ) {
      if ( foodBuffer.isEmpty() ) {
         return;
      }
      this.currentFood = foodBuffer.remove( 0 );
      this.portionBuffer.clear();
      this.portionBuffer.addAll( currentFood.portions() );
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
    * Triggered when starting a new {@link FoodPortion}.
    * @param key the parsed key.
    */
   void startWritingPortion( String key ) {
      if ( portionBuffer.isEmpty() ) {
         return;
      }
      this.currentPortion = portionBuffer.remove( 0 );
   }//End Method
   
   /**
    * Provides the portion value in percentage for the next {@link FoodPortion}.
    * @param key the parsed key.
    * @return the value.
    */
   Double getPortionValue( String key ) {
      return currentPortion.portion().get();
   }//End Method
   
   /**
    * Provides the id of the next {@link FoodPortion}s {@link uk.dangrew.nuts.food.Food}.
    * @param key the parsed key.
    * @return the value.
    */
   String getFoodId( String key ) {
      if ( currentPortion.food().get() == null ) {
         return "";
      }
      return currentPortion.food().get().properties().id();
   }//End Method
   
}//End Class
