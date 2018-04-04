/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.goal.proportion;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayWithObjectParseHandler;
import uk.dangrew.jupa.json.parse.handle.type.DoubleParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.EnumParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.jupa.json.structure.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonArrayWithObjectWriteHandler;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;
import uk.dangrew.nuts.goal.proportion.ProportionGoalStore;
import uk.dangrew.nuts.goal.proportion.ProportionType;

public class ProportionGoalPersistence {
   
   static final String GOALS = "goals";
   static final String GOAL = "goal";
   
   static final String ID = "id";
   static final String NAME = "name";
   static final String CARB_PROPORTION_TYPE = "carbohydrateProportionType";
   static final String FAT_PROPORTION_TYPE = "fatProportionType";
   static final String PROTEIN_PROPORTION_TYPE = "proteinProportionType";
   static final String CARB_PROPORTION_VALUE = "carbohydrateProportionValue";
   static final String FAT_PROPORTION_VALUE = "fatProportionValue";
   static final String PROTEIN_PROPORTION_VALUE = "proteinProportionValue";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final ProportionGoalParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final ProportionGoalWriteModel writeModel;
   
   public ProportionGoalPersistence( ProportionGoalStore goals ) {
      this( new ProportionGoalParseModel( goals ), new ProportionGoalWriteModel( goals ) );
   }//End Constructor
   
   ProportionGoalPersistence( ProportionGoalParseModel parseModel, ProportionGoalWriteModel writeModel ) {
      this.structure = new JsonStructure();
      this.parseModel = parseModel;
      this.parserWithReadHandles = new JsonParser();
      this.writeModel = writeModel;
      this.parserWithWriteHandles = new JsonParser();
      
      constructStructure();
      constructReadHandles();
      constructWriteHandles();
   }//End Constructor
   
   private void constructStructure(){
      structure.array( GOALS, structure.root(), writeModel::getNumberOfGoals );
      structure.child( GOALS, structure.root() );
      structure.child( GOAL, GOALS );
      structure.child( ID, GOAL );
      structure.child( NAME, GOAL );
      structure.child( CARB_PROPORTION_TYPE, GOAL ); 
      structure.child( CARB_PROPORTION_VALUE, GOAL );
      structure.child( FAT_PROPORTION_TYPE, GOAL ); 
      structure.child( FAT_PROPORTION_VALUE, GOAL );
      structure.child( PROTEIN_PROPORTION_TYPE, GOAL ); 
      structure.child( PROTEIN_PROPORTION_VALUE, GOAL );
   }//End Method
   
   private void constructReadHandles(){
      parserWithReadHandles.when( GOALS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               parseModel::startGoal, parseModel::finishGoal, null, null ) 
      ) );
      
      parserWithReadHandles.when( ID, new StringParseHandle( parseModel::setId ) );
      parserWithReadHandles.when( NAME, new StringParseHandle( parseModel::setName ) );
      parserWithReadHandles.when( CARB_PROPORTION_TYPE, new EnumParseHandle<>( ProportionType.class, parseModel::setCarbohydrateProportionType ) );
      parserWithReadHandles.when( FAT_PROPORTION_TYPE, new EnumParseHandle<>( ProportionType.class, parseModel::setFatProportionType ) );
      parserWithReadHandles.when( PROTEIN_PROPORTION_TYPE, new EnumParseHandle<>( ProportionType.class, parseModel::setProteinProportionType ) );
      parserWithReadHandles.when( CARB_PROPORTION_VALUE, new DoubleParseHandle( parseModel::setCarbohydrateProportionValue ) );
      parserWithReadHandles.when( FAT_PROPORTION_VALUE, new DoubleParseHandle( parseModel::setFatProportionValue ) );
      parserWithReadHandles.when( PROTEIN_PROPORTION_VALUE, new DoubleParseHandle( parseModel::setProteinProportionValue ) );
   }//End Method
   
   private void constructWriteHandles(){
      parserWithWriteHandles.when( GOALS, new JsonWriteHandleImpl( new JsonArrayWithObjectWriteHandler( 
               writeModel::startWritingGoal, null, writeModel::startWritingGoals, null 
      ) ) );
      
      parserWithWriteHandles.when( ID, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getId ) ) );
      parserWithWriteHandles.when( NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getName ) ) );
      parserWithWriteHandles.when( CARB_PROPORTION_TYPE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getCarbohydrateProportionType ) ) );
      parserWithWriteHandles.when( FAT_PROPORTION_TYPE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getFatProportionType ) ) );
      parserWithWriteHandles.when( PROTEIN_PROPORTION_TYPE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getProteinProportionType ) ) );
      parserWithWriteHandles.when( CARB_PROPORTION_VALUE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getCarbohydrateProportionValue ) ) );
      parserWithWriteHandles.when( FAT_PROPORTION_VALUE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getFatProportionValue ) ) );
      parserWithWriteHandles.when( PROTEIN_PROPORTION_VALUE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getProteinProportionValue ) ) );
   }//End Method
   
   public JsonStructure structure(){
      return structure;
   }//End Method
   
   public JsonParser readHandles(){
      return parserWithReadHandles;
   }//End Method

   public JsonParser writeHandles(){
      return parserWithWriteHandles;
   }//End Method
}//End Class
