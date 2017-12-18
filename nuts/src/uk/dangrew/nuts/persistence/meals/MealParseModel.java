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
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.system.ConceptStore;

/**
 * {@link MealParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing {@link Meal}s.
 */
public class MealParseModel< FoodTypeT extends Meal > {
   
   private final Database database;
   private final ConceptStore< FoodTypeT > meals;
   
   private String id;
   private String name;
   private final List< FoodPortion > portions;
   
   private String foodId;
   private Double portionValue;
   
   /**
    * Constructs a new {@link MealParseModel}.
    * @param database the {@link Database}.
    * @param meals {@link ConceptStore} providing the {@link Meal}s.
    */
   protected MealParseModel( Database database, ConceptStore< FoodTypeT > meals ) {
      this.database = database;
      this.meals = meals;
      this.portions = new ArrayList<>();
   }//End Constructor
   
   /**
    * Access to the {@link Database}.
    * @return the {@link Database}.
    */
   protected Database database(){
      return database;
   }//End Method
   
   /**
    * Access to the {@link ConceptStore}.
    * @return the {@link ConceptStore}.
    */
   protected ConceptStore< FoodTypeT > meals(){
      return meals;
   }//End Method
   
   /**
    * Access to the currently parsed id.
    * @return the id.
    */
   protected String id(){
      return id;
   }//End Method
   
   /**
    * Triggered when starting a new {@link Meal}.
    * @param key the parsed key.
    */
   protected void startMeal( String key ) {
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
   protected void finishMeal( String key ) {
      FoodTypeT meal = meals.get( id );
      if ( meal == null ) {
         meal = meals.createConcept( id, name );
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
   protected void startPortion( String key ) {
      this.foodId = null;
      this.portionValue = null;
   }//End Method
   
   /**
    * Triggered when finished to parsing a new {@link FoodPortion}.
    * @param key the parsed key.
    * @return the finished {@link FoodPortion}.
    */
   protected FoodPortion finishPortion( String key ){
      Food food = null;
      if ( !foodId.isEmpty() ) {
         food = database.foodItems().get( foodId );
         if ( food == null ) {
            food = database.meals().get( foodId );
         }
         
         if ( food == null ) {
            food = database.templates().get( foodId );
         }
         
         if ( food == null ) {
            System.out.println( "Can't find food for: " + foodId );
            return null;
         }
      }
      FoodPortion portion = new FoodPortion( food, portionValue );
      portions.add( portion );
      return portion;
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
