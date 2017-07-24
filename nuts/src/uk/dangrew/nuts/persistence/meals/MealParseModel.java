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

import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.meal.MealStore;
import uk.dangrew.nuts.store.Database;

/**
 * {@link MealParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing {@link Meal}s.
 */
class MealParseModel {
   
   private final Database database;
   private final MealStore meals;
   
   private String id;
   private String name;
   private final List< FoodPortion > portions;
   
   private String foodId;
   private Double portionValue;
   
   /**
    * Constructs a new {@link MealParseModel}.
    * @param database the {@link Database}.
    * @param meals {@link MealStore}.
    */
   MealParseModel( Database database, MealStore meals ) {
      this.database = database;
      this.meals = meals;
      this.portions = new ArrayList<>();
   }//End Constructor
   
   /**
    * Triggered when starting a new {@link Meal}.
    * @param key the parsed key.
    */
   void startMeal( String key ) {
      this.id = null;
      this.name = null;
      this.portions.clear();;
      this.foodId = null;
      this.portionValue = null;
   }//End Method
   
   /**
    * Triggered when all values of a {@link Meal} have been parsed.
    * @param key the parsed key.
    */
   void finishMeal( String key ) {
      Meal meal = meals.get( id );
      if ( meal == null ) {
         meal = new Meal( id, name );
         meals.store( meal );
      }
      meal.portions().clear();
      meal.portions().addAll( portions );
   }//End Method
   
   /**
    * Sets the {@link uk.dangrew.nuts.food.FoodProperties#id()} for the current {@link Meal}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setId( String key, String value ) {
      this.id = value;
   }//End Method
   
   /**
    * Sets the {@link uk.dangrew.nuts.food.FoodProperties#nameProperty()} for the current {@link Meal}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setName( String key, String value ) {
      this.name = value;
   }//End Method
   
   /**
    * Triggered when starting to parse a new {@link FoodPortion}.
    * @param key the parsed key.
    */
   void startPortion( String key ) {
      this.foodId = null;
      this.portionValue = null;
   }//End Method
   
   /**
    * Triggered when finished to parsing a new {@link FoodPortion}.
    * @param key the parsed key.
    */
   void finishPortion( String key ){
      Food food = null;
      if ( !foodId.isEmpty() ) {
         food = database.foodItems().get( foodId );
         if ( food == null ) {
            food = database.meals().get( foodId );
         }
         
         if ( food == null ) {
            System.out.println( "Can't find food for: " + foodId );
            return;
         }
      }
      FoodPortion portion = new FoodPortion( food, portionValue );
      portions.add( portion );
   }//End Method
   
   /**
    * Sets the id of the {@link Food} associated with the current {@link FoodPortion}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setFoodId( String key, String value ) {
      this.foodId = value;
   }//End Method
   
   /**
    * Sets the portion value as a percentage for the current {@link FoodPortion}. 
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setPortionValue( String key, Double value ) {
      this.portionValue = value;
   }//End Method
   
}//End Class
