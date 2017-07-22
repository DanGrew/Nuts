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
class FoodItemPersistence {
   
   static final String FOOD_ITEMS = "foodItems";
   static final String FOOD_ITEM = "foodItem";
   
   static final String ID = "id";
   static final String NAME = "Name";
   static final String CARBOHYDRATES = "Carbohydrates";
   static final String FATS = "Fats";
   static final String PROTEIN = "Protein";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final FoodItemParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final FoodItemWriteModel writeModel;
   
  /**
    * Constructs a new {@link FoodItemPersistence}.
    * @param database the {@link Database}.
    */
   FoodItemPersistence( Database database ) {
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
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for writing.
    */
   private void constructWriteHandles(){
      parserWithWriteHandles.when( FOOD_ITEMS, new JsonWriteHandleImpl( new JsonArrayWithObjectWriteHandler( 
               writeModel::startWritingFoodItem, null, null, null 
      ) ) );
      
      parserWithWriteHandles.when( ID, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getId ) ) );
      parserWithWriteHandles.when( NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getName ) ) );
      parserWithWriteHandles.when( CARBOHYDRATES, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getCarbohydrates ) ) );
      parserWithWriteHandles.when( FATS, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getFats ) ) );
      parserWithWriteHandles.when( PROTEIN, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getProtein ) ) );
   }//End Method
   
   JsonStructure structure(){
      return structure;
   }//End Method
   
   JsonParser readHandles(){
      return parserWithReadHandles;
   }//End Method

   JsonParser writeHandles(){
      return parserWithWriteHandles;
   }//End Method
}//End Class
