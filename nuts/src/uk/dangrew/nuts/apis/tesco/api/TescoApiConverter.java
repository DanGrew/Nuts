package uk.dangrew.nuts.apis.tesco.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import uk.dangrew.jtt.connection.data.json.conversion.ApiResponseToJsonConverter;
import uk.dangrew.nuts.apis.tesco.database.TescoDatabase;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoApiConverter {

   private final ApiResponseToJsonConverter converter;
   private final TescoParseRecorder parseRecorder;
   private final TescoGrocerySearchParser groceryParser;
   
   public TescoApiConverter( TescoDatabase database ){
      this( 
               database.descriptionStore(),
               new ApiResponseToJsonConverter(), 
               new TescoGrocerySearchParser( database.descriptionStore() ) 
      );
   }//End Constructor
   
   private TescoApiConverter( 
            TescoFoodDescriptionStore descriptionStore,
            ApiResponseToJsonConverter converter, 
            TescoGrocerySearchParser groceryParser 
   ) {
      this( converter, new TescoParseRecorder( descriptionStore, groceryParser ), groceryParser );
   }//End Constructor
   
   TescoApiConverter( 
            ApiResponseToJsonConverter converter, 
            TescoParseRecorder parseRecorder,
            TescoGrocerySearchParser groceryParser 
   ) {
      this.converter = converter;
      this.parseRecorder = parseRecorder;
      this.groceryParser = groceryParser;
   }//End Constructor

   public List< TescoFoodDescription > convertDescriptions( String result ) {
      JSONObject json = converter.convert( result );
      if ( json == null ) {
         System.out.println( "Unable to convert " + result );
         return new ArrayList<>();
      }
      
      groceryParser.parse( json );
      return parseRecorder.getAndClearRecordedDescriptions();
   }//End Method

}//End Class
