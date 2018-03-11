package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.dangrew.nuts.apis.tesco.api.CalculatedNutritionType;

public class TescoStringParser {

   static final String UNKNOWN_PER_100 = "Per 100-Unknown";
   static final String UNKNOWN_PER_SERVING = "Per Serving-Unknown";
   static final String UNKNOWN_KCAL = "??kcal";
   static final String UNKNOWN_KJ = "??kj";
   
   private static final Pattern NUMBER_MATCHER = Pattern.compile( "\\d+(\\.\\d*)*" );
   
   private static final Pattern GRAM_MATCHER = Pattern.compile( "\\d+g" );
   private static final Pattern MILLILITRE_MATCHER = Pattern.compile( "\\d+ml" );
   
   private static final Pattern KCAL_MATCHER = Pattern.compile( "\\d+ *kcal" );
   private static final Pattern KJ_MATCHER = Pattern.compile( "\\d+ *kj" );
   private static final Pattern KJ_SPLIT_MATCHER = Pattern.compile( "kj\\d+" );
   private static final Pattern COMBINED_ENERGY_KJ_KCAL_MATCHER = Pattern.compile( "kj.*kcal" );
   private static final Pattern COMBINED_ENERGY_KCAL_KJ_MATCHER = Pattern.compile( "kcal.*kj" );
   private static final Pattern COMBINED_ENERGY_EXACT_MATCHER = Pattern.compile( "energy" );
   
   public String parsePer100Header( String header ) {
      if ( header == null ) {
         return UNKNOWN_PER_100;
      }
      return parseHeader( header );
   }//End Method

   public String parsePerServingHeader( String header ) {
      if ( header == null ) {
         return UNKNOWN_PER_SERVING;
      }
      return parseHeader( header );
   }//End Method

   private String parseHeader( String header ) {
      Matcher matcher = MILLILITRE_MATCHER.matcher( header );
      if ( matcher.find() ) {
          return matcher.group();
      }
      
      matcher = GRAM_MATCHER.matcher( header );
      if ( matcher.find() ) {
         return matcher.group();
      }
      return header;
   }//End Method
   
   public CalculatedNutritionType parseNutritionType( String string ) {
      for ( CalculatedNutritionType type : CalculatedNutritionType.values() ) {
         if ( type.matches( string ) ) {
            return type;
         }
      }
      System.out.println( "Found new nutrition type: " + string );
      return null;
   }//End Method

   public String extractKcalFrom( String string ) {
      if ( string == null ) {
         return UNKNOWN_KCAL;
      }
      
      String parseable = string.toLowerCase();
      
      Matcher matcher = KCAL_MATCHER.matcher( parseable );
      if ( matcher.find() ) {
         return extractNumber( matcher.group() );
      }
      
      matcher = KJ_SPLIT_MATCHER.matcher( parseable );
      if ( matcher.find() ) {
         return extractNumber( matcher.group() );
      }
      
      return parseable;
   }//End Method
   
   public String extractKjFrom( String string ) {
      if ( string == null ) {
         return UNKNOWN_KJ;
      }
      
      String parseable = string.toLowerCase();
      
      Matcher matcher = KJ_MATCHER.matcher( parseable );
      if ( matcher.find() ) {
         return extractNumber( matcher.group() );
      }
      
      return parseable;
   }//End Method
   
   public boolean isCombinedEnergy( String string ) {
      if ( string == null ) {
         return false;
      }
      
      String parseable = string.toLowerCase();
      
      Matcher matcher = COMBINED_ENERGY_KJ_KCAL_MATCHER.matcher( parseable );
      if ( matcher.find() ){
         return true;
      }
      
      matcher = COMBINED_ENERGY_KCAL_KJ_MATCHER.matcher( parseable );
      if ( matcher.find() ) {
         return true;
      }
      
      matcher = COMBINED_ENERGY_EXACT_MATCHER.matcher( parseable );
      if ( matcher.find() ){
         boolean containsKcal = string.contains( "kcal" );
         boolean containsKj = string.contains( "kj" );
         return containsKcal == containsKj;
      }
      
      return false;
   }//End Method
   
   public String extractNumber( String string ) {
      if ( string == null ) {
         return null;
      }
      Matcher matcher = NUMBER_MATCHER.matcher( string );
      if ( matcher.find() ) {
         return matcher.group();
      }
      
      return string;
   }//End Method
   
}//End Class
