package uk.dangrew.nuts.apis.tesco.api.webapi;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import uk.dangrew.jtt.connection.data.json.conversion.ApiResponseToJsonConverter;
import uk.dangrew.nuts.apis.tesco.database.TescoDatabase;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;

public class TescoApiConverter {

   private final ApiResponseToJsonConverter converter;
   private final TescoParseRecorder parseRecorder;
   private final TescoGrocerySearchParser groceryParser;
   private final TescoProductDetailParser productDetailParser;
   
   public TescoApiConverter( TescoDatabase database ){
      this( 
               database.descriptionStore(),
               new ApiResponseToJsonConverter(), 
               new TescoGrocerySearchParser( database.descriptionStore() ),
               new TescoProductDetailParser( database.descriptionStore() )
      );
   }//End Constructor
   
   private TescoApiConverter( 
            TescoFoodDescriptionStore descriptionStore,
            ApiResponseToJsonConverter converter, 
            TescoGrocerySearchParser groceryParser,
            TescoProductDetailParser productDetailParser
   ) {
      this( 
               converter, 
               new TescoParseRecorder( descriptionStore, groceryParser ), 
               groceryParser,
               productDetailParser
      );
   }//End Constructor
   
   TescoApiConverter( 
            ApiResponseToJsonConverter converter, 
            TescoParseRecorder parseRecorder,
            TescoGrocerySearchParser groceryParser,
            TescoProductDetailParser productDetailParser
   ) {
      this.converter = converter;
      this.parseRecorder = parseRecorder;
      this.groceryParser = groceryParser;
      this.productDetailParser = productDetailParser;
   }//End Constructor

   public List< TescoFoodDescription > importGrocerySearchResponse( String result ) {
      JSONObject json = converter.convert( result );
      if ( json == null ) {
         System.out.println( "Unable to convert " + result );
         return new ArrayList<>();
      }
      
      groceryParser.parse( json );
      return parseRecorder.getAndClearRecordedDescriptions();
   }//End Method

   public void importProductDetailResponse( String result ) {
      JSONObject json = converter.convert( result );
      if ( json == null ) {
         System.out.println( "Unable to convert " + result );
      }
      
      productDetailParser.parse( json );
   }//End Method

}//End Class
