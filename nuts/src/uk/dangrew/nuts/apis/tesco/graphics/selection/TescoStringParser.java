package uk.dangrew.nuts.apis.tesco.graphics.selection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TescoStringParser {

   static final String UNKNOWN_PER_100 = "Per 100-Unknown";
   static final String UNKNOWN_PER_SERVING = "Per Serving-Unknown";
   
   private static final Pattern GRAM_MATCHER = Pattern.compile( "\\d+g" );
   private static final Pattern MILLILITRE_MATCHER = Pattern.compile( "\\d+ml" );
   
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
}//End Class
