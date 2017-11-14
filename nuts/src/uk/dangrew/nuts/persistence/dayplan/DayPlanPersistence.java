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
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.jupa.json.structure.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanStore;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.persistence.template.TemplatePersistence;
import uk.dangrew.nuts.store.Database;

/**
 * {@link DayPlanPersistence} provides the architecture for reading and writing {@link DayPlan}s.
 */
public class DayPlanPersistence {
   
   static final String MEAL = MealPersistence.MEAL;
   static final String DATE = "date";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final DayPlanParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final DayPlanWriteModel writeModel;
   
   public DayPlanPersistence( Database database, DayPlanStore dayPlans ) {
      this( new DayPlanParseModel( database, dayPlans ), new DayPlanWriteModel( dayPlans ) );
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
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for reading.
    */
   private void modifyReadHandles(){
      parserWithReadHandles.when( DATE, new StringParseHandle( parseModel::setDateString ) );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for writing.
    */
   private void modifyWriteHandles(){
      parserWithWriteHandles.when( DATE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getDateString ) ) );
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
