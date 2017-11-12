/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.meals;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayWithObjectParseHandler;
import uk.dangrew.jupa.json.parse.handle.type.DoubleParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.jupa.json.structure.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonArrayWithObjectWriteHandler;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;
import uk.dangrew.nuts.food.FoodStore;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;

/**
 * {@link MealPersistence} provides the architecture for reading and writing {@link uk.dangrew.nuts.meal.Meal}s.
 */
public class MealPersistence< FoodTypeT extends Meal > {
   
   static final String MEALS = "meals";
   public static final String MEAL = "meal";
   
   static final String ID = "id";
   static final String NAME = "name";
   
   static final String PORTIONS = "portions";
   static final String PORTION = "portion";
   static final String PORTION_VALUE = "portionValue";
   static final String FOOD_ID = "foodId";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final MealParseModel< FoodTypeT > parseModel;
   private final JsonParser parserWithWriteHandles;
   private final MealWriteModel< FoodTypeT > writeModel;
   
  /**
    * Constructs a new {@link MealPersistence}.
    * @param database the {@link Database}.
    * @param meals the {@link FoodStore} providing the {@link Meal}s.
    */
   public MealPersistence( Database database, FoodStore< FoodTypeT > meals ) {
      this( new MealParseModel<>( database, meals ), new MealWriteModel<>( meals ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link MealPersistence}.
    * @param parseModel the {@link MealParseModel}.
    * @param writeModel the {@link MealWriteModel}.
    */
   public MealPersistence( MealParseModel< FoodTypeT > parseModel, MealWriteModel< FoodTypeT > writeModel ) {
      this.structure = new JsonStructure();
      this.parseModel = parseModel;
      this.parserWithReadHandles = new JsonParser();
      this.writeModel = writeModel;
      this.parserWithWriteHandles = new JsonParser();
      
      constructStructure();
      constructReadHandles();
      constructWriteHandles();
   }//End Constructor
   
   /**
    * Method to construct the {@link JsonStructure}.
    */
   private void constructStructure(){
      structure.array( MEALS, structure.root(), writeModel::getNumberOfMeals );
      structure.child( MEAL, MEALS );
      structure.child( ID, MEAL );
      structure.child( NAME, MEAL );
      structure.array( PORTIONS, MEAL, writeModel::getNumberOfPortions );
      structure.child( PORTION, PORTIONS );
      structure.child( PORTION_VALUE, PORTION );
      structure.child( FOOD_ID, PORTION );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for reading.
    */
   private void constructReadHandles(){
      parserWithReadHandles.when( MEALS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               parseModel::startMeal, parseModel::finishMeal, null, null ) 
      ) );
      
      parserWithReadHandles.when( ID, new StringParseHandle( parseModel::setId ) );
      parserWithReadHandles.when( NAME, new StringParseHandle( parseModel::setName ) );
      
      parserWithReadHandles.when( PORTIONS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               parseModel::startPortion, parseModel::finishPortion, null, null ) 
      ) );
      parserWithReadHandles.when( PORTION_VALUE, new DoubleParseHandle( parseModel::setPortionValue ) );
      parserWithReadHandles.when( FOOD_ID, new StringParseHandle( parseModel::setFoodId ) );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for writing.
    */
   private void constructWriteHandles(){
      parserWithWriteHandles.when( MEALS, new JsonWriteHandleImpl( new JsonArrayWithObjectWriteHandler( 
               writeModel::startWritingMeal, null, writeModel::startWritingMeals, null 
      ) ) );
      
      parserWithWriteHandles.when( ID, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getId ) ) );
      parserWithWriteHandles.when( NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getName ) ) );
      
      parserWithWriteHandles.when( PORTIONS, new JsonWriteHandleImpl( new JsonArrayWithObjectWriteHandler( 
               writeModel::startWritingPortion, null, null, null 
      ) ) );
      parserWithWriteHandles.when( PORTION_VALUE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getPortionValue ) ) );
      parserWithWriteHandles.when( FOOD_ID, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getFoodId ) ) );
   }//End Method
   
   /**
    * Access to the {@link JsonStructure}.
    * @return the {@link JsonStructure}.
    */
   public JsonStructure structure(){
      return structure;
   }//End Method
   
   /**
    * Access to the {@link JsonParser} reader.
    * @return the {@link JsonParser}.
    */
   public JsonParser readHandles(){
      return parserWithReadHandles;
   }//End Method

   /**
    * Access to the {@link JsonParser} writer.
    * @return the {@link JsonParser}.
    */
   public JsonParser writeHandles(){
      return parserWithWriteHandles;
   }//End Method
}//End Class
