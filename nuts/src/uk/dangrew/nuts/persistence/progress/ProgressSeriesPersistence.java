/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.progress;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.key.JsonArrayWithObjectParseHandler;
import uk.dangrew.jupa.json.parse.handle.type.DoubleParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.jupa.json.structure.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonArrayWithObjectWriteHandler;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesPersistence {
   
   static final String SERIES_LIST = "seriesList";
   public static final String SERIES = "series";
   
   static final String ID = "id";
   static final String NAME = "name";
   
   static final String ENTRIES = "entries";
   static final String ENTRY = "entry";
   static final String TIMESTAMP = "timestamp";
   static final String VALUE = "value";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final ProgressSeriesParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final ProgressSeriesWriteModel writeModel;
   
   public ProgressSeriesPersistence( Database database ) {
      this( new ProgressSeriesParseModel( database ), new ProgressSeriesWriteModel( database.progressSeries() ) );
   }//End Constructor
   
   public ProgressSeriesPersistence( ProgressSeriesParseModel parseModel, ProgressSeriesWriteModel writeModel ) {
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
      structure.array( SERIES_LIST, structure.root(), writeModel::getNumberOfSeries );
      structure.child( SERIES, SERIES_LIST );
      structure.child( ID, SERIES );
      structure.child( NAME, SERIES );
      structure.array( ENTRIES, SERIES, writeModel::getNumberOfEntries );
      structure.child( ENTRY, ENTRIES );
      structure.child( TIMESTAMP, ENTRY );
      structure.child( VALUE, ENTRY );
   }//End Method
   
   private void constructReadHandles(){
      parserWithReadHandles.when( SERIES_LIST, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               parseModel::startSeries, parseModel::finishSeries, null, null ) 
      ) );
      
      parserWithReadHandles.when( ID, new StringParseHandle( parseModel::setId ) );
      parserWithReadHandles.when( NAME, new StringParseHandle( parseModel::setName ) );
      
      parserWithReadHandles.when( ENTRIES, new StringParseHandle( new JsonArrayWithObjectParseHandler<>( 
               parseModel::startEntry, parseModel::finishEntry, null, null ) 
      ) );
      parserWithReadHandles.when( TIMESTAMP, new StringParseHandle( parseModel::setTimestamp ) );
      parserWithReadHandles.when( VALUE, new DoubleParseHandle( parseModel::setValue ) );
   }//End Method
   
   private void constructWriteHandles(){
      parserWithWriteHandles.when( SERIES_LIST, new JsonWriteHandleImpl( new JsonArrayWithObjectWriteHandler( 
               writeModel::startWritingSeries, null, writeModel::startWritingSeriesList, null 
      ) ) );
      
      parserWithWriteHandles.when( ID, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getId ) ) );
      parserWithWriteHandles.when( NAME, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getName ) ) );
      
      parserWithWriteHandles.when( ENTRIES, new JsonWriteHandleImpl( new JsonArrayWithObjectWriteHandler( 
               writeModel::startWritingEntries, null, null, null 
      ) ) );
      parserWithWriteHandles.when( TIMESTAMP, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getTimestamp ) ) );
      parserWithWriteHandles.when( VALUE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getValue ) ) );
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
