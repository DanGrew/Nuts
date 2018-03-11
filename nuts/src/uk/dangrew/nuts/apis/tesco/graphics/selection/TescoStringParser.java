package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.dangrew.nuts.apis.tesco.api.CalculatedNutritionType;

public class TescoStringParser {

   static final String UNKNOWN_PER_100 = "Per 100-Unknown";
   static final String UNKNOWN_PER_SERVING = "Per Serving-Unknown";
   static final String UNKNOWN_KCAL = "??kcal";
   
   private static final Pattern NUMBER_MATCHER = Pattern.compile( "\\d+" );
   
   private static final Pattern GRAM_MATCHER = Pattern.compile( "\\d+g" );
   private static final Pattern MILLILITRE_MATCHER = Pattern.compile( "\\d+ml" );
   
   private static final Pattern KCAL_MATCHER = Pattern.compile( "\\d+.*kcal" );
   private static final Pattern KJ_SPLIT_MATCHER = Pattern.compile( "kj\\d+" );
   
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
   
   private String extractNumber( String string ) {
      Matcher matcher = NUMBER_MATCHER.matcher( string );
      if ( matcher.find() ) {
         return matcher.group();
      }
      
      return string;
   }//End Method
}//End Class
