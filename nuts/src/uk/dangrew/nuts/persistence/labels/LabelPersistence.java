/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.labels;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayWithObjectParseHandler;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.jupa.json.structure.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonArrayWithObjectWriteHandler;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;
import uk.dangrew.nuts.persistence.fooditems.ConceptPersistence;
import uk.dangrew.nuts.store.Database;

public class LabelPersistence implements ConceptPersistence {
   
   static final String LABELS = "labels";
   public static final String LABEL = "label";
   
   static final String ID = "id";
   static final String NAME = "name";
   
   static final String CONCEPTS = "concepts";
   static final String CONCEPT = "concept";
   static final String CONCEPT_ID = "conceptId";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final LabelParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final LabelWriteModel writeModel;
   
   public LabelPersistence( Database database ) {
      this( new LabelParseModel( database ), new LabelWriteModel( database.labels() ) );
   }//End Constructor
   
   public LabelPersistence( LabelParseModel parseModel, LabelWriteModel writeModel ) {
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
      structure.array( LABELS, structure.root(), writeModel::getNumberOfLabels );
      structure.child( LABEL, LABELS );
      structure.child( ID, LABEL );
      structure.child( NAME, LABEL );
      structure.array( CONCEPTS, LABEL, writeModel::getNumberOfConcepts );
      structure.child( CONCEPT, CONCEPTS );
      structure.child( CONCEPT_ID, CONCEPT );
   }//End Method
   
   private void constructReadHandles(){
      parserWithReadHandles.when( LABELS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               parseModel::startLabel, parseModel::finishLabel, null, null ) 
      ) );
      
      parserWithReadHandles.when( ID, new StringParseHandle( parseModel::setId ) );
      parserWithReadHandles.when( NAME, new StringParseHandle( parseModel::setName ) );
      
      parserWithReadHandles.when( CONCEPTS, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               parseModel::startConcept, parseModel::finishConcept, null, null ) 
      ) );
      parserWithReadHandles.when( CONCEPT_ID, new StringParseHandle( parseModel::setConceptId ) );
   }//End Method
   
   private void constructWriteHandles(){
      parserWithWriteHandles.when( LABELS, new JsonWriteHandleImpl( new JsonArrayWithObjectWriteHandler( 
               writeModel::startWritingLabel, null, writeModel::startWritingLabels, null 
      ) ) );
      
      parserWithWriteHandles.when( ID, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getId ) ) );
      parserWithWriteHandles.when( NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getName ) ) );
      
      parserWithWriteHandles.when( CONCEPTS, new JsonWriteHandleImpl( new JsonArrayWithObjectWriteHandler( 
               writeModel::startWritingConcepts, null, null, null 
      ) ) );
      parserWithWriteHandles.when( CONCEPT_ID, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getConceptId ) ) );
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
