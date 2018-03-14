package uk.dangrew.nuts.apis.tesco.api;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.apis.tesco.api.webapi.TescoApiConnector;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoApiConverter;
import uk.dangrew.nuts.apis.tesco.api.webpage.TescoWebsiteParser;
import uk.dangrew.nuts.apis.tesco.database.TescoDatabase;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;

public class TescoApiController {
   
   static final int PAGE_SIZE = 1000;
   static final int PAGE_LIMIT = 20;
   
   private final int pageSize;
   private final TescoApiConnector connector;
   private final TescoApiConverter converter;
   private final TescoWebsiteParser websiteParser;
   
   public TescoApiController( TescoDatabase database ) {
      this( 
               PAGE_SIZE, 
               new TescoApiConnector(), 
               new TescoApiConverter( database ),
               new TescoWebsiteParser()
      );
   }//End Constructor
   
   TescoApiController( 
            int pageSize, 
            TescoApiConnector connector, 
            TescoApiConverter converter,
            TescoWebsiteParser websiteParser
   ) {
      this.pageSize = pageSize;
      this.connector = connector;
      this.converter = converter;
      this.websiteParser = websiteParser;
   }//End Constructor

   public List< TescoFoodDescription > search( String criteria ) {
      int lastResponseCount = pageSize;
      int pageNumber = 1;
      
      List< TescoFoodDescription > searchResults = new ArrayList<>();
      while ( lastResponseCount == pageSize && pageNumber < PAGE_LIMIT ) {
         String response = connector.searchProducts( criteria, pageNumber, pageSize );
         List< TescoFoodDescription > descriptionsFound = converter.importGrocerySearchResponse( response );
         searchResults.addAll( descriptionsFound );
         
         lastResponseCount = descriptionsFound.size();
         pageNumber++;
      }
      return searchResults;
   }//End Method

   public void downloadProductDetail( TescoFoodDescription description ) {
      if ( description.productDetail().nutrition().isPopulated() ) {
         return;
      }
      
      String tpnb = description.groceryProperties().tpnb().get();
      if ( tpnb == null ) {
         System.out.println( "Cannot download product detail: No tpnb present for " + description.properties().nameProperty().get() );
         return;
      }
      String response = connector.retrieveProduct( description.groceryProperties().tpnb().get() );
      converter.importProductDetailResponse( response );
      
      if ( description.productDetail().nutrition().per100Header().get() == null ) {
         websiteParser.parseNutritionFor( description );
      }
   }//End Method

}//End Class
