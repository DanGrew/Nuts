/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.goal;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonObjectParseHandler;
import uk.dangrew.jupa.json.parse.handle.type.DoubleParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.EnumParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.jupa.json.structure.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;
import uk.dangrew.nuts.goal.Gender;
import uk.dangrew.nuts.store.Database;

/**
 * {@link GoalPersistence} provides the architecture for reading and writing the {@link uk.dangrew.nuts.goal.Goal}.
 */
public class GoalPersistence {
   
   static final String GOAL = "goal";
   
   static final String AGE = "age";
   static final String WEIGHT = "weight";
   static final String HEIGHT = "height";
   static final String GENDER = "gender";
   static final String BMR = "bmr";
   static final String PAL = "pal";
   static final String TEE = "tee";
   static final String EXERCISE = "exerciseCalories";
   static final String DEFICIT = "calorieDeficit";
   static final String PROTEIN_PER_POUND = "proteinPerPound";
   static final String FAT_PER_POUND = "fatPerPound";
   
   static final String CALORIE_GOAL = "calorieGoal";
   static final String CARB_GOAL = "carbohydratesGoal";
   static final String FAT_GOAL = "fatsGoal";
   static final String PROTEIN_GOAL = "proteinGoal";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final GoalParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final GoalWriteModel writeModel;
   
  /**
    * Constructs a new {@link GoalPersistence}.
    * @param database the {@link Database}.
    */
   public GoalPersistence( Database database ) {
      this( new GoalParseModel( database ), new GoalWriteModel( database ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link GoalPersistence}.
    * @param parseModel the {@link GoalParseModel}.
    * @param writeModel the {@link GoalWriteModel}.
    */
   GoalPersistence( GoalParseModel parseModel, GoalWriteModel writeModel ) {
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
      structure.child( GOAL, structure.root() );
      structure.child( AGE, GOAL ); 
      structure.child( WEIGHT, GOAL );
      structure.child( HEIGHT, GOAL );
      structure.child( GENDER, GOAL );
      structure.child( BMR, GOAL );
      structure.child( PAL, GOAL );
      structure.child( TEE, GOAL );
      structure.child( EXERCISE, GOAL );
      structure.child( DEFICIT, GOAL );
      structure.child( PROTEIN_PER_POUND, GOAL );
      structure.child( FAT_PER_POUND, GOAL );
      structure.child( CALORIE_GOAL, GOAL );
      structure.child( CARB_GOAL, GOAL );
      structure.child( FAT_GOAL, GOAL );
      structure.child( PROTEIN_GOAL, GOAL );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for reading.
    */
   private void constructReadHandles(){
      parserWithReadHandles.when( GOAL, new StringParseHandle( new JsonObjectParseHandler<>(  
               parseModel::startGoal, parseModel::finishGoal 
      ) ) );
      
      parserWithReadHandles.when( AGE, new DoubleParseHandle( parseModel::setAge ) );
      parserWithReadHandles.when( WEIGHT, new DoubleParseHandle( parseModel::setWeight ) );
      parserWithReadHandles.when( HEIGHT, new DoubleParseHandle( parseModel::setHeight ) );
      parserWithReadHandles.when( GENDER, new EnumParseHandle<>( Gender.class, parseModel::setGender ) );
      parserWithReadHandles.when( BMR, new DoubleParseHandle( parseModel::setBmr ) );
      parserWithReadHandles.when( PAL, new DoubleParseHandle( parseModel::setPal ) );
      parserWithReadHandles.when( TEE, new DoubleParseHandle( parseModel::setTee ) );
      parserWithReadHandles.when( EXERCISE, new DoubleParseHandle( parseModel::setExerciseCalories ) );
      parserWithReadHandles.when( DEFICIT, new DoubleParseHandle( parseModel::setCalorieDeficit ) );
      parserWithReadHandles.when( PROTEIN_PER_POUND, new DoubleParseHandle( parseModel::setProteinPerPound ) );
      parserWithReadHandles.when( FAT_PER_POUND, new DoubleParseHandle( parseModel::setFatPerPound ) );
      parserWithReadHandles.when( CALORIE_GOAL, new DoubleParseHandle( parseModel::setCalorieGoal ) );
      parserWithReadHandles.when( CARB_GOAL, new DoubleParseHandle( parseModel::setCarbohydratesGoal ) );
      parserWithReadHandles.when( FAT_GOAL, new DoubleParseHandle( parseModel::setFatsGoal ) );
      parserWithReadHandles.when( PROTEIN_GOAL, new DoubleParseHandle( parseModel::setProteinGoal ) );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for writing.
    */
   private void constructWriteHandles(){
      parserWithWriteHandles.when( AGE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getAge ) ) );
      parserWithWriteHandles.when( WEIGHT, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getWeight ) ) );
      parserWithWriteHandles.when( HEIGHT, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getHeight ) ) );
      parserWithWriteHandles.when( GENDER, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getGender ) ) );
      parserWithWriteHandles.when( BMR, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getBmr ) ) );
      parserWithWriteHandles.when( PAL, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getPal ) ) );
      parserWithWriteHandles.when( TEE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getTee ) ) );
      parserWithWriteHandles.when( EXERCISE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getExerciseCalories ) ) );
      parserWithWriteHandles.when( DEFICIT, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getCalorieDeficit ) ) );
      parserWithWriteHandles.when( PROTEIN_PER_POUND, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getProteinPerPound ) ) );
      parserWithWriteHandles.when( FAT_PER_POUND, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getFatPerPound ) ) );
      parserWithWriteHandles.when( CALORIE_GOAL, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getCalorieGoal ) ) );
      parserWithWriteHandles.when( CARB_GOAL, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getCarbohydratesGoal ) ) );
      parserWithWriteHandles.when( FAT_GOAL, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getFatsGoal ) ) );
      parserWithWriteHandles.when( PROTEIN_GOAL, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getProteinGoal ) ) );
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
