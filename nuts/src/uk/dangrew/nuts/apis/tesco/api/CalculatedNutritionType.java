package uk.dangrew.nuts.apis.tesco.api;

import java.util.function.Function;
import java.util.regex.Pattern;

import uk.dangrew.nuts.apis.tesco.item.CalculatedNutrientValue;
import uk.dangrew.nuts.apis.tesco.item.CalculatedNutrition;

public enum CalculatedNutritionType {

   EnergyInKcal( 
            Pattern.compile( ".*kcal.*" ),
            CalculatedNutrition::energyInKcal
   ),
   EnergyInKj( 
            Pattern.compile( ".*kj.*" ),
            CalculatedNutrition::energyInKj
   ),
   Fats( 
            Pattern.compile( ".*fat.*" ),
            CalculatedNutrition::fat
   ),
   Saturates( 
            Pattern.compile( ".*saturate.*" ),
            CalculatedNutrition::saturates 
   ),
   Carbohydrate( 
            Pattern.compile( ".*carbohydrate.*" ),
            CalculatedNutrition::carbohydrates 
   ),
   Sugars( 
            Pattern.compile( ".*sugar.*" ),
            CalculatedNutrition::sugars
   ),
   Fibre( 
            Pattern.compile( ".*fibre.*" ),
            CalculatedNutrition::fibre 
   ),
   Protein( 
            Pattern.compile( ".*protein.*" ),
            CalculatedNutrition::protein
   ),
   Salt( 
            Pattern.compile( ".*salt.*" ),
            CalculatedNutrition::salt 
   ),
   Calcium( 
            Pattern.compile( ".*calcium.*" ),
            CalculatedNutrition::calcium 
   ),
   Thiamin( 
            Pattern.compile( ".*thiamin.*" ),
            CalculatedNutrition::thiamin 
   ),
   Riboflavin( 
            Pattern.compile( ".*riboflavin.*" ),
            CalculatedNutrition::riboflavin 
   ),
   Niacin( 
            Pattern.compile( ".*niacin.*" ),
            CalculatedNutrition::niacin 
   ),
   FolicAcid( 
            Pattern.compile( ".*folic( )*acid.*" ),
            CalculatedNutrition::folicAcid 
   ),
   Iron( 
            Pattern.compile( ".*iron.*" ),
            CalculatedNutrition::iron 
   ),
   Omega3( 
            Pattern.compile( ".*omega.*" ),
            CalculatedNutrition::omega3 
   );
   
   private final Pattern pattern;
   private final Function< CalculatedNutrition, CalculatedNutrientValue > valueRetriever;
   
   private CalculatedNutritionType( 
            Pattern pattern,
            Function< CalculatedNutrition, CalculatedNutrientValue > valueRetriever
   ) {
      this.pattern = pattern;
      this.valueRetriever = valueRetriever;
   }//End Constructor
   
   public boolean matches( String string ) {
      if ( string == null ) {
         return false;
      }
      return pattern.matcher( string.toLowerCase() ).find();
   }//End Method
   
   public CalculatedNutrientValue redirect( CalculatedNutrition nutrition ) {
      return valueRetriever.apply( nutrition );
   }//End Method
   
}//End Enum
