package uk.dangrew.nuts.apis.tesco.api.webpage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import uk.dangrew.nuts.apis.tesco.api.parsing.CalculatedNutritionParsingHandler;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;

public class TescoWebsiteParser {

   private static final String TESCO_PRODUCT_ADDRESS = "https://www.tesco.com/groceries/en-GB/products/";
   
   private final CalculatedNutritionParsingHandler model;
   
   public TescoWebsiteParser() {
      this.model = new CalculatedNutritionParsingHandler();
   }//End Constructor

   public void parseNutritionFor( TescoFoodDescription description ) {
      if ( description == null ) {
         return;
      }
      model.setCurrentNutrition( description.productDetail().nutrition() );
      
      Document webpageDocument = connectToProductPage( description );
      if ( webpageDocument == null ) {
         return;
      }
      
      TescoWebpageNutritionTable table = new TescoWebpageNutritionTable( webpageDocument );
      new TescoNutritionExtractor( table, model ).run();;
   }//End Method 
   
   private Document streamProductPage( String webAddress ) {
      URL url = null;
      try {
         url = new URL( webAddress );
      } catch ( MalformedURLException e ) {
         return null;
      }
      try (
         InputStream is = url.openStream();
         BufferedReader br = new BufferedReader( new InputStreamReader( is ) );
      ) {
         String line =  br.lines().collect( Collectors.joining() );
//         line = line.substring( line.indexOf( TescoWebpageNutritionTable.NUTRITION_TABLE_HTML_TAG ) );
         return Jsoup.parse( line );
      } catch ( Exception exception ) {
         //unable to stream, not a problem, best effort
      }
      return null;
   }//End Method
   
   private Document connectToProductPage( TescoFoodDescription description ) {
      for ( String tpnc : description.productDetail().tpncs() ) {
         String webpage = TESCO_PRODUCT_ADDRESS + tpnc;
         Document document = streamProductPage( webpage );
         //strip out eveyrthing before table ref
         
         if ( document != null ) {
            return document;
         }
      }
      System.out.println( "Unable to connect to web page " + description.properties().nameProperty().get() );
      return null;
   }//End Method
   
}//End Class
