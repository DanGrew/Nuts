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
import uk.dangrew.nuts.day.DayPlanStore;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.persistence.template.TemplatePersistence;
import uk.dangrew.nuts.store.Database;

/**
 * {@link DayPlanPersistence} provides the architecture for reading and writing {@link DayPlan}s.
 */
public class DayPlanPersistence {
   
   static final String MEAL = MealPersistence.MEAL;
   static final String PORTION = MealPersistence.PORTION;
   static final String DATE = "date";
   static final String CONSUMED = "consumed";
   static final String CONSUMED_CALORIES = "consumedCalories";
   static final String ALLOWED_CALORIES = "allowedCalories";
   static final String CALORIE_BALANCE = "calorieBalance";
   static final String IS_BALANCE_RESET = "isBalanceReset";
   
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
      structure.optionalChild( CONSUMED_CALORIES, MEAL );
      structure.optionalChild( ALLOWED_CALORIES, MEAL );
      structure.optionalChild( CALORIE_BALANCE, MEAL );
      structure.optionalChild( IS_BALANCE_RESET, MEAL );
      structure.optionalChild( CONSUMED, PORTION );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for reading.
    */
   private void modifyReadHandles(){
      parserWithReadHandles.when( DATE, new StringParseHandle( parseModel::setDateString ) );
      parserWithReadHandles.when( CONSUMED, new BooleanParseHandle( parseModel::setConsumed ) );
      parserWithReadHandles.when( CONSUMED_CALORIES, new DoubleParseHandle( parseModel::setConsumedCalories ) );
      parserWithReadHandles.when( ALLOWED_CALORIES, new DoubleParseHandle( parseModel::setAllowedCalories ) );
      parserWithReadHandles.when( CALORIE_BALANCE, new DoubleParseHandle( parseModel::setCalorieBalance ) );
      parserWithReadHandles.when( IS_BALANCE_RESET, new BooleanParseHandle( parseModel::setIsBalanceReset ) );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for writing.
    */
   private void modifyWriteHandles(){
      parserWithWriteHandles.when( DATE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getDateString ) ) );
      parserWithWriteHandles.when( CONSUMED, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::isConsumed ) ) );
      parserWithWriteHandles.when( CONSUMED_CALORIES, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getConsumedCalories ) ) );
      parserWithWriteHandles.when( ALLOWED_CALORIES, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getAllowedCalories ) ) );
      parserWithWriteHandles.when( CALORIE_BALANCE, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getCalorieBalance ) ) );
      parserWithWriteHandles.when( IS_BALANCE_RESET, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::isBalanceReset ) ) );
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
