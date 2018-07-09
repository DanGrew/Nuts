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
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

/**
 * {@link FoodItemPersistence} provides the architecture for reading and writing {@link uk.dangrew.nuts.food.FoodItem}s.
 */
public class FoodItemPersistence implements ConceptPersistence {
   
   static final String FOOD_ITEMS = "foodItems";
   static final String FOOD_ITEM = "foodItem";
   
   static final String ID = "id";
   static final String NAME = "name";
   static final String LOGGED_WEIGHT = "loggedWeight";
   static final String SOLD_IN_WEIGHT = "soldInWeight";
   
   static final String NUTRITIONAL_UNITS = "nutritionalUnits";
   
   @Deprecated static final String CARBOHYDRATES = "carbohydrates";
   @Deprecated static final String FATS = "fats";
   @Deprecated static final String PROTEIN = "protein";
   @Deprecated static final String CALORIES = "calories";
   @Deprecated static final String FIBER = "fiber";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final FoodItemParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final FoodItemWriteModel writeModel;
   
   public FoodItemPersistence( FoodItemStore store ) {
      this( new FoodItemParseModel( store ), new FoodItemWriteModel( store ) );
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
      structure.optionalChild( LOGGED_WEIGHT, FOOD_ITEM );
      structure.optionalChild( SOLD_IN_WEIGHT, FOOD_ITEM );
      
      structure.optionalChild( NUTRITIONAL_UNITS, FOOD_ITEM );
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         structure.optionalChild( unit.name().toLowerCase(), NUTRITIONAL_UNITS );
      }
   }//End Method
   
   private void constructReadHandles(){
      //we append the deprecated handles first so that if a key is reused, it is overridden the most recent
      //for example, 'fats' will not collide with 'fat' but 'protein' will collide.
      appendDeprecatedReadHandles();
      appendCurrentReadHandles();
   }//End Method
   
   private void appendCurrentReadHandles(){
      parserWithReadHandles.when( FOOD_ITEMS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               parseModel::startFoodItem, parseModel::finishFoodItem, null, null ) 
      ) );
      
      parserWithReadHandles.when( ID, new StringParseHandle( parseModel::setId ) );
      parserWithReadHandles.when( NAME, new StringParseHandle( parseModel::setName ) );
      parserWithReadHandles.when( LOGGED_WEIGHT, new DoubleParseHandle( parseModel::setLoggedWeight ) );
      parserWithReadHandles.when( SOLD_IN_WEIGHT, new DoubleParseHandle( parseModel::setSoldInWeight ) );
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         parserWithReadHandles.when( unit.name().toLowerCase(), new DoubleParseHandle( v -> parseModel.setNutritionalUnit( unit, v ) ) );
      }
   }//End Method
   
   private void appendDeprecatedReadHandles(){
      parserWithReadHandles.when( CARBOHYDRATES, new DoubleParseHandle( v -> parseModel.setNutritionalUnit( NutritionalUnit.Carbohydrate, v ) ) );
      parserWithReadHandles.when( FATS, new DoubleParseHandle( v -> parseModel.setNutritionalUnit( NutritionalUnit.Fat, v ) ) );
      parserWithReadHandles.when( PROTEIN, new DoubleParseHandle( v -> parseModel.setNutritionalUnit( NutritionalUnit.Protein, v ) ) );
      parserWithReadHandles.when( CALORIES, new DoubleParseHandle( v -> parseModel.setNutritionalUnit( NutritionalUnit.Calories, v ) ) );
      parserWithReadHandles.when( FIBER, new DoubleParseHandle( v -> parseModel.setNutritionalUnit( NutritionalUnit.Fibre, v ) ) );
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
      parserWithWriteHandles.when( LOGGED_WEIGHT, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getLoggedWeight ) ) );
      parserWithWriteHandles.when( SOLD_IN_WEIGHT, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getSoldInWeight ) ) );
      
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         parserWithWriteHandles.when( unit.name().toLowerCase(), new JsonWriteHandleImpl( new JsonValueWriteHandler( () -> writeModel.getNutritionalUnit( unit ) ) ) );
      }
   }//End Method
   
   @Override public JsonStructure structure(){
      return structure;
   }//End Method
   
   @Override public JsonParser readHandles(){
      return parserWithReadHandles;
   }//End Method

   @Override public JsonParser writeHandles(){
      return parserWithWriteHandles;
   }//End Method
}//End Class
