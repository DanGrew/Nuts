package uk.dangrew.nuts.apis.tesco.api.parsing;

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
   
   public void startedCalculatedNutrition() {
      //do nothing
   }//End Method
   
   public void finishedCalculatedNutrition() {
      //do nothing
   }//End Method
   
   public void setPer100Header( String value ) {
      currentNutrition.per100Header().set( value );
   }//End Method
   
   public void setPerServingHeader( String value ) {
      currentNutrition.perServingHeader().set( value );
   }//End Method
   
   public void startedCalculatedNutrientsObject() {
      this.name = null;
      this.valuePer100 = null;
      this.valuePerServing = null;
   }//End Method
   
   public void finishedCalculatedNutrientsObject() {
      if ( stringParser.isCombinedEnergy( name ) ) {
         String parsedValuePer100 = valuePer100;
         String parsedValuePerServing = valuePerServing;
         
         valuePer100 = stringParser.extractKcalFrom( parsedValuePer100 );
         valuePerServing = stringParser.extractKcalFrom( parsedValuePerServing );
         setNutritionValues( CalculatedNutritionType.EnergyInKcal.redirect( currentNutrition ) );
         
         valuePer100 = stringParser.extractKjFrom( parsedValuePer100 );
         valuePerServing = stringParser.extractKjFrom( parsedValuePerServing );
         setNutritionValues( CalculatedNutritionType.EnergyInKj.redirect( currentNutrition ) );
      } else {
         CalculatedNutritionType nutritionType = stringParser.parseNutritionType( name );
         if ( nutritionType == null ) {
            return;
         }
         if ( nutritionType == CalculatedNutritionType.EnergyInKcal ) {
            valuePer100 = stringParser.extractKcalFrom( valuePer100 );
            valuePerServing = stringParser.extractKcalFrom( valuePerServing );
         }
         setNutritionValues( nutritionType.redirect( currentNutrition ) );
      }
   }//End Method
   
   private void setNutritionValues( CalculatedNutrientValue value ) {
      value.name().set( name );
      value.valuePer100().set( stringParser.extractNumber( valuePer100 ) );
      value.valuePerServing().set( stringParser.extractNumber( valuePerServing ) );
   }//End Method
   
   public void startedCalculatedNutrientsArray() {
      //do nothing
   }//End Method
   
   public void finishedCalculatedNutrientsArray() {
      //do nothing
   }//End Method
   
   public void setValuePer100( String value ) {
      this.valuePer100 = value;
   }//End Method
   
   public void setValuePerServing( String value ) {
      this.valuePerServing = value;
   }//End Method
   
   public void setName( String value ) {
      this.name = value;
   }//End Method
   
}//End Constructor
