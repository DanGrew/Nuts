/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.weighins;

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
 * {@link WeightRecordingPersistence} provides the architecture for reading and writing {@link uk.dangrew.nuts.progress.WeightRecording}s.
 */
public class WeightRecordingPersistence {
   
   static final String WEIGH_INS = "weighIns";
   static final String WEIGH_IN = "weighIn";
   
   static final String DATE = "date";
   static final String MORNING_WEIGHT = "morningWeight";
   static final String MORNING_BODY_FAT = "morningBodyFat";
   static final String MORNING_LEAN_MASS = "morningLeanMass";
   static final String EVENING_WEIGHT = "eveningWeight";
   static final String EVENING_BODY_FAT = "eveningBodyFat";
   static final String EVENING_LEAN_MASS = "eveningLeanMass";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final WeightRecordingParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final WeightRecordingWriteModel writeModel;
   
  /**
    * Constructs a new {@link WeightRecordingPersistence}.
    * @param database the {@link Database}.
    */
   public WeightRecordingPersistence( Database database ) {
      this( new WeightRecordingParseModel( database ), new WeightRecordingWriteModel( database ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link WeightRecordingPersistence}.
    * @param parseModel the {@link WeightRecordingParseModel}.
    * @param writeModel the {@link WeightRecordingWriteModel}.
    */
   WeightRecordingPersistence( WeightRecordingParseModel parseModel, WeightRecordingWriteModel writeModel ) {
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
      structure.array( WEIGH_INS, structure.root(), writeModel::getNumberOfWeighIns );
      structure.child( WEIGH_IN, WEIGH_INS );
      structure.child( DATE, WEIGH_IN );
      structure.child( MORNING_WEIGHT, WEIGH_IN );
      structure.child( MORNING_BODY_FAT, WEIGH_IN );
      structure.child( MORNING_LEAN_MASS, WEIGH_IN );
      structure.child( EVENING_WEIGHT, WEIGH_IN );
      structure.child( EVENING_BODY_FAT, WEIGH_IN );
      structure.child( EVENING_LEAN_MASS, WEIGH_IN );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for reading.
    */
   private void constructReadHandles(){
      parserWithReadHandles.when( WEIGH_INS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               parseModel::startWeighIn, parseModel::finishWeighIn, null, null ) 
      ) );
      
      parserWithReadHandles.when( DATE, new StringParseHandle( parseModel::setDate ) );
      parserWithReadHandles.when( MORNING_WEIGHT, new DoubleParseHandle( parseModel::setMorningWeight ) );
      parserWithReadHandles.when( MORNING_BODY_FAT, new DoubleParseHandle( parseModel::setMorningBodyFat ) );
      parserWithReadHandles.when( MORNING_LEAN_MASS, new DoubleParseHandle( parseModel::setMorningLeanMass ) );
      parserWithReadHandles.when( EVENING_WEIGHT, new DoubleParseHandle( parseModel::setEveningWeight ) );
      parserWithReadHandles.when( EVENING_BODY_FAT, new DoubleParseHandle( parseModel::setEveningBodyFat ) );
      parserWithReadHandles.when( EVENING_LEAN_MASS, new DoubleParseHandle( parseModel::setEveningLeanMass ) );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for writing.
    */
   private void constructWriteHandles(){
      parserWithWriteHandles.when( WEIGH_INS, new JsonWriteHandleImpl( new JsonArrayWithObjectWriteHandler( 
               writeModel::startWritingWeighIn, null, writeModel::startWritingWeighIns, null 
      ) ) );
      
      parserWithWriteHandles.when( DATE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getDate ) ) );
      parserWithWriteHandles.when( MORNING_WEIGHT, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getMorningWeight ) ) );
      parserWithWriteHandles.when( MORNING_BODY_FAT, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getMorningBodyFat ) ) );
      parserWithWriteHandles.when( MORNING_LEAN_MASS, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getMorningLeanMass ) ) );
      parserWithWriteHandles.when( EVENING_WEIGHT, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getEveningWeight ) ) );
      parserWithWriteHandles.when( EVENING_BODY_FAT, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getEveningBodyFat ) ) );
      parserWithWriteHandles.when( EVENING_LEAN_MASS, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getEveningLeanMass ) ) );
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
