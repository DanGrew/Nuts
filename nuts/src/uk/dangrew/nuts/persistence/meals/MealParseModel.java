/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.meals;


import uk.dangrew.kode.concept.ConceptStore;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.persistence.resolution.MealPortionResolution;
import uk.dangrew.nuts.persistence.resolution.MealResolutionSupplier;
import uk.dangrew.nuts.persistence.resolution.Resolver;
import uk.dangrew.nuts.store.Database;

/**
 * {@link MealParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing {@link Meal}s.
 */
public class MealParseModel< FoodTypeT extends Meal > {
   
   private final Resolver resolver;
   private final MealResolutionSupplier resolutionSupplier;
   private final Database database;
   private final ConceptStore< FoodTypeT > meals;
   
   protected String id;
   private String name;
   
   protected String foodId;
   protected Double portionValue;
   
   /**
    * Constructs a new {@link MealParseModel}.
    * @param database the {@link Database}.
    * @param meals {@link ConceptStore} providing the {@link Meal}s.
    */
   protected MealParseModel( Database database, ConceptStore< FoodTypeT > meals ) {
      this( database, meals, MealPortionResolution::new );
   }//End Constructor
   
   protected MealParseModel( Database database, ConceptStore< FoodTypeT > meals, MealResolutionSupplier resolutionSupplier ) {
      this.database = database;
      this.resolver = database.resolver();
      this.resolutionSupplier = resolutionSupplier;
      this.meals = meals;
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
    */
   protected void startMeal() {
      this.id = null;
      this.name = null;
      this.foodId = null;
      this.portionValue = null;
   }//End Method
   
   /**
    * Triggered when all values of a {@link Meal} have been parsed.
    */
   protected void finishMeal() {
      FoodTypeT meal = meals.get( id );
      if ( meal == null ) {
         meals.createConcept( id, name );
      }
   }//End Method
   
   /**
    * Sets the {@link uk.dangrew.nuts.food.FoodProperties#id()} for the current {@link Meal}.
    * @param value the parsed value.
    */
   protected void setId( String value ) {
      this.id = value;
   }//End Method
   
   /**
    * Sets the {@link uk.dangrew.nuts.food.FoodProperties#nameProperty()} for the current {@link Meal}.
    * @param value the parsed value.
    */
   protected void setName( String value ) {
      this.name = value;
   }//End Method
   
   /**
    * Triggered when starting to parse a new {@link FoodPortion}.
    */
   protected void startPortion() {
      this.foodId = null;
      this.portionValue = null;
   }//End Method
   
   /**
    * Triggered when finished to parsing a new {@link FoodPortion}.
    * @return the finished {@link FoodPortion}.
    */
   protected void finishPortion(){
      resolver.submitStrategy( resolutionSupplier.generate(  
               meals, id, foodId, portionValue 
      ) );
   }//End Method
   
   /**
    * Sets the id of the {@link Food} associated with the current {@link FoodPortion}.
    * @param value the parsed value.
    */
   protected void setFoodId( String value ) {
      this.foodId = value;
   }//End Method
   
   /**
    * Sets the portion value as a percentage for the current {@link FoodPortion}. 
    * @param value the parsed value.
    */
   protected void setPortionValue( Double value ) {
      this.portionValue = value;
   }//End Method
   
}//End Class
