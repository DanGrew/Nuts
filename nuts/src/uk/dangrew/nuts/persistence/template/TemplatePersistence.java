/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.template;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.type.StringParseHandle;
import uk.dangrew.jupa.json.structure.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;
import uk.dangrew.nuts.template.TemplateStore;

/**
 * {@link TemplatePersistence} provides the architecture for reading and writing {@link Template}s.
 */
public class TemplatePersistence {
   
   static final String MEAL = MealPersistence.MEAL;
   static final String GOAL = "goalId";
   
   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final TemplateParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final TemplateWriteModel writeModel;
   
  /**
    * Constructs a new {@link TemplatePersistence}.
    * @param database the {@link Database}.
    * @param templates the {@link FoodStore} providing the {@link Template}s.
    */
   public TemplatePersistence( Database database, TemplateStore templates ) {
      this( new TemplateParseModel( database, templates ), new TemplateWriteModel( templates ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link TemplatePersistence}.
    * @param parseModel the {@link TemplateParseModel}.
    * @param writeModel the {@link TemplateWriteModel}.
    */
   TemplatePersistence( TemplateParseModel parseModel, TemplateWriteModel writeModel ) {
      MealPersistence< Template > mealPersistence = new MealPersistence<>( parseModel, writeModel );
      
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
      structure.optionalChild( GOAL, MEAL );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for reading.
    */
   private void modifyReadHandles(){
      parserWithReadHandles.when( GOAL, new StringParseHandle( parseModel::setGoalId ) );
   }//End Method
   
   /**
    * Method to construct the {@link JsonParser} for writing.
    */
   private void modifyWriteHandles(){
      parserWithWriteHandles.when( GOAL, new JsonWriteHandleImpl( new JsonValueWriteHandler( writeModel::getGoalId ) ) );
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
