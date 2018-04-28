/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.fooditems;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayWithObjectParseHandler;
import uk.dangrew.jupa.json.parse.handle.type.DoubleParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.jupa.json.structure.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonArrayWithObjectWriteHandler;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;
import uk.dangrew.nuts.store.Database;

/**
 * {@link FoodItemPersistence} provides the architecture for reading and writing {@link uk.dangrew.nuts.food.FoodItem}s.
 */
public class FoodItemPersistence {
   
   static final String FOOD_ITEMS = "foodItems";
   static final String FOOD_ITEM = "foodItem";
   
   static final String ID = "id";
   static final String NAME = "name";
   static final String CARBOHYDRATES = "carbohydrates";
   static final String FATS = "fats";
   static final String PROTEIN = "protein";
   static final String CALORIES = "calories";
   static final String FIBER = "fiber";
   static final String LOGGED_WEIGHT = "loggedWeight";
   static final String SOLD_IN_WEIGHT = "soldInWeight";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final FoodItemParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final FoodItemWriteModel writeModel;
   
  /**
    * Constructs a new {@link FoodItemPersistence}.
    * @param database the {@link Database}.
    */
   public FoodItemPersistence( Database database ) {
      this( new FoodItemParseModel( database ), new FoodItemWriteModel( database ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link FoodItemPersistence}.
    * @param parseModel the {@link FoodItemParseModel}.
    * @param writeModel the {@link FoodItemWriteModel}.
    */
   FoodItemPersistence( FoodItemParseModel parseModel, FoodItemWriteModel writeModel ) {
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
      structure.array( FOOD_ITEMS, structure.root(), writeModel::getNumberOfFoodItems );
      structure.child( FOOD_ITEM, FOOD_ITEMS );
      structure.child( ID, FOOD_ITEM );
      structure.child( NAME, FOOD_ITEM );
      structure.child( CARBOHYDRATES, FOOD_ITEM );
      structure.child( FATS, FOOD_ITEM );
      structure.child( PROTEIN, FOOD_ITEM );
      structure.child( CALORIES, FOOD_ITEM );
      structure.optionalChild( FIBER, FOOD_ITEM );
      structure.optionalChild( LOGGED_WEIGHT, FOOD_ITEM );
      structure.optionalChild( SOLD_IN_WEIGHT, FOOD_ITEM );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for reading.
    */
   private void constructReadHandles(){
      parserWithReadHandles.when( FOOD_ITEMS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               parseModel::startFoodItem, parseModel::finishFoodItem, null, null ) 
      ) );
      
      parserWithReadHandles.when( ID, new StringParseHandle( parseModel::setId ) );
      parserWithReadHandles.when( NAME, new StringParseHandle( parseModel::setName ) );
      parserWithReadHandles.when( CARBOHYDRATES, new DoubleParseHandle( parseModel::setCarbohydrates ) );
      parserWithReadHandles.when( FATS, new DoubleParseHandle( parseModel::setFats ) );
      parserWithReadHandles.when( PROTEIN, new DoubleParseHandle( parseModel::setProtein ) );
      parserWithReadHandles.when( CALORIES, new DoubleParseHandle( parseModel::setCalories ) );
      parserWithReadHandles.when( FIBER, new DoubleParseHandle( parseModel::setFiber ) );
      parserWithReadHandles.when( LOGGED_WEIGHT, new DoubleParseHandle( parseModel::setLoggedWeight ) );
      parserWithReadHandles.when( SOLD_IN_WEIGHT, new DoubleParseHandle( parseModel::setSoldInWeight ) );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for writing.
    */
   private void constructWriteHandles(){
      parserWithWriteHandles.when( FOOD_ITEMS, new JsonWriteHandleImpl( new JsonArrayWithObjectWriteHandler( 
               writeModel::startWritingFoodItem, null, writeModel::startWritingFoodItems, null 
      ) ) );
      
      parserWithWriteHandles.when( ID, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getId ) ) );
      parserWithWriteHandles.when( NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getName ) ) );
      parserWithWriteHandles.when( CARBOHYDRATES, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getCarbohydrates ) ) );
      parserWithWriteHandles.when( FATS, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getFats ) ) );
      parserWithWriteHandles.when( PROTEIN, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getProtein ) ) );
      parserWithWriteHandles.when( CALORIES, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getCalories ) ) );
      parserWithWriteHandles.when( FIBER, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getFiber ) ) );
      parserWithWriteHandles.when( LOGGED_WEIGHT, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getLoggedWeight ) ) );
      parserWithWriteHandles.when( SOLD_IN_WEIGHT, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getSoldInWeight ) ) );
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
