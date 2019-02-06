/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.dayplan;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.type.BooleanParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.DoubleParseHandle;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.jupa.json.structure.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.persistence.fooditems.ConceptPersistence;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.persistence.template.TemplatePersistence;
import uk.dangrew.nuts.store.Database;

/**
 * {@link DayPlanPersistence} provides the architecture for reading and writing {@link DayPlan}s.
 */
public class DayPlanPersistence implements ConceptPersistence {
   
   static final String MEAL = MealPersistence.MEAL;
   static final String PORTION = MealPersistence.PORTION;
   static final String DATE = "date";
   static final String CONSUMED = "consumed";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final DayPlanParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final DayPlanWriteModel writeModel;
   
   public DayPlanPersistence( Database database ) {
      this( new DayPlanParseModel( database ), new DayPlanWriteModel( database.dayPlans() ) );
   }//End Constructor
   
   DayPlanPersistence( DayPlanParseModel parseModel, DayPlanWriteModel writeModel ) {
      TemplatePersistence< DayPlan > mealPersistence = new TemplatePersistence<>( parseModel, writeModel );
      
      this.structure = mealPersistence.structure();
      this.parseModel = parseModel;
      this.parserWithReadHandles = mealPersistence.readHandles();
      this.writeModel = writeModel;
      this.parserWithWriteHandles = mealPersistence.writeHandles();
      
      modifyStructure();
      modifyReadHandles();
      modifyWriteHandles();
   }//End Constructor
   
   /**
    * Method to construct the {@link JsonStructure}.
    */
   private void modifyStructure(){
      structure.optionalChild( DATE, MEAL );
      structure.optionalChild( CONSUMED, PORTION );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for reading.
    */
   private void modifyReadHandles(){
      parserWithReadHandles.when( DATE, new StringParseHandle( parseModel::setDateString ) );
      parserWithReadHandles.when( CONSUMED, new BooleanParseHandle( parseModel::setConsumed ) );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for writing.
    */
   private void modifyWriteHandles(){
      parserWithWriteHandles.when( DATE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getDateString ) ) );
      parserWithWriteHandles.when( CONSUMED, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::isConsumed ) ) );
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
