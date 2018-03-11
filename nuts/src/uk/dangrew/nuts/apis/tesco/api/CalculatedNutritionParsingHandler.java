package uk.dangrew.nuts.apis.tesco.api;

import uk.dangrew.nuts.apis.tesco.graphics.selection.TescoStringParser;
import uk.dangrew.nuts.apis.tesco.item.CalculatedNutrientValue;
import uk.dangrew.nuts.apis.tesco.item.CalculatedNutrition;

public class CalculatedNutritionParsingHandler {

   private final TescoStringParser stringParser;
   
   private CalculatedNutrition currentNutrition;
   private String name;
   private String valuePer100;
   private String valuePerServing;
   
   public CalculatedNutritionParsingHandler() {
      this( new TescoStringParser() );
   }//End Constructor
   
   CalculatedNutritionParsingHandler( TescoStringParser stringParser ) {
      this.stringParser = stringParser;
   }//End Constructor
   
   public void setCurrentNutrition( CalculatedNutrition nutrition ) {
      this.currentNutrition = nutrition;
   }//End Method
   
   public void startedCalculatedNutrition( String key ) {
      //do nothing
   }//End Method
   
   public void finishedCalculatedNutrition( String key ) {
      //do nothing
   }//End Method
   
   public void setPer100Header( String key, String value ) {
      currentNutrition.per100Header().set( value );
   }//End Method
   
   public void setPerServingHeader( String key, String value ) {
      currentNutrition.perServingHeader().set( value );
   }//End Method
   
   public void startedCalculatedNutrientsObject( String key ) {
      this.name = null;
      this.valuePer100 = null;
      this.valuePerServing = null;
   }//End Method
   
   public void finishedCalculatedNutrientsObject( String key ) {
      CalculatedNutritionType nutritionType = stringParser.parseNutritionType( name );
      if ( nutritionType == null ) {
         return;
      }
      if ( nutritionType == CalculatedNutritionType.EnergyInKcal ) {
         valuePer100 = stringParser.extractKcalFrom( valuePer100 );
         valuePerServing = stringParser.extractKcalFrom( valuePerServing );
      }
      setNutritionValues( nutritionType.redirect( currentNutrition ) );
   }//End Method
   
   private void setNutritionValues( CalculatedNutrientValue value ) {
      value.valuePer100().set( valuePer100 );
      value.valuePerServing().set( valuePerServing );
   }//End Method
   
   public void startedCalculatedNutrientsArray( String key ) {
      //do nothing
   }//End Method
   
   public void finishedCalculatedNutrientsArray( String key ) {
      //do nothing
   }//End Method
   
   public void setValuePer100( String key, String value ) {
      this.valuePer100 = value;
   }//End Method
   
   public void setValuePerServing( String key, String value ) {
      this.valuePerServing = value;
   }//End Method
   
   public void setName( String key, String value ) {
      this.name = value;
   }//End Method
   
}//End Constructor
