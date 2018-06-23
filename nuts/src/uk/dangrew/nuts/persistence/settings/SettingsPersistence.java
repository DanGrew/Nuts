/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.settings;

import uk.dangrew.jupa.json.parse.JsonParser;
import uk.dangrew.jupa.json.parse.handle.type.BooleanParseHandle;
import uk.dangrew.jupa.json.structure.JsonStructure;
import uk.dangrew.jupa.json.write.handle.key.JsonValueWriteHandler;
import uk.dangrew.jupa.json.write.handle.type.JsonWriteHandleImpl;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.persistence.fooditems.ConceptPersistence;

public class SettingsPersistence implements ConceptPersistence {
   
   static final String SHOW_NUTRITIONAL_UNITS = "showNutritionalUnits";

   private final JsonStructure structure;
   private final JsonParser parserWithReadHandles;
   private final SettingsParseModel parseModel;
   private final JsonParser parserWithWriteHandles;
   private final SettingsWriteModel writeModel;
   
   public SettingsPersistence( NutsSettings settings ) {
      this( new SettingsParseModel( settings ), new SettingsWriteModel( settings ) );
   }//End Constructor
   
   SettingsPersistence( SettingsParseModel parseModel, SettingsWriteModel writeModel ) {
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
      structure.child( SHOW_NUTRITIONAL_UNITS, structure.root() );
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         structure.optionalChild( unit.name().toLowerCase(), SHOW_NUTRITIONAL_UNITS );
      }
   }//End Method
   
   private void constructReadHandles(){
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         parserWithReadHandles.when( unit.name().toLowerCase(), new BooleanParseHandle( v -> parseModel.showNutritionalUnit( unit, v ) ) );
      }
   }//End Method
   
   private void constructWriteHandles(){
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         parserWithWriteHandles.when( unit.name().toLowerCase(), new JsonWriteHandleImpl( new JsonValueWriteHandler( () -> writeModel.isShowingNutritionalUnit( unit ) ) ) );
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
