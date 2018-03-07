package uk.dangrew.nuts.apis.tesco.api;

import uk.dangrew.nuts.apis.tesco.item.CalculatedNutrientValue;
import uk.dangrew.nuts.apis.tesco.item.CalculatedNutrition;

public class CalculatedNutritionParsingHandler {

   private CalculatedNutrition currentNutrition;
   private String name;
   private String valuePer100;
   private String valuePerServing;
   
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
      if ( name.equalsIgnoreCase( "Energy (kJ)" ) ) {
         setNutritionValues( currentNutrition.energyInKj() );
      } else if ( name.equalsIgnoreCase( "Energy (kcal)" ) ) {
         setNutritionValues( currentNutrition.energyInKcal() );
      } else if ( name.equalsIgnoreCase( "Fat (g)" ) ) {
         setNutritionValues( currentNutrition.fat() );
      } else if ( name.equalsIgnoreCase( "Saturates (g)" ) ) {
         setNutritionValues( currentNutrition.saturates() );
      } else if ( name.equalsIgnoreCase( "Carbohydrate (g)" ) ) {
         setNutritionValues( currentNutrition.carbohydrates() );
      } else if ( name.equalsIgnoreCase( "Sugars (g)" ) ) {
         setNutritionValues( currentNutrition.sugars() );
      } else if ( name.equalsIgnoreCase( "Fibre (g)" ) ) {
         setNutritionValues( currentNutrition.fibre() );
      } else if ( name.equalsIgnoreCase( "Protein (g)" ) ) {
         setNutritionValues( currentNutrition.protein() );
      } else if ( name.equalsIgnoreCase( "Salt (g)" ) ) {
         setNutritionValues( currentNutrition.salt() );
      } else {
         System.out.println( "Found new nutritional value: " + name );
      }
   }//End Method
   
   private void setNutritionValues( CalculatedNutrientValue value ) {
      value.valuePer100().set( valuePer100 );
      value.valuePerServing().set( valuePerServing );
   }//End Method
   
   public void startedCalculatedNutrientsArray( String key ) {
      
   }//End Method
   
   public void finishedCalculatedNutrientsArray( String key ) {
      
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
