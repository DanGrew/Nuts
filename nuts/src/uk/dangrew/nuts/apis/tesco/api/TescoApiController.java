package uk.dangrew.nuts.apis.tesco.api;

import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.apis.tesco.database.TescoDatabase;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoApiController {
   
   static final int PAGE_SIZE = 1000;
   static final int PAGE_LIMIT = 20;
   
   private final int pageSize;
   private final TescoApiConnector connector;
   private final TescoApiConverter converter;
   
   public TescoApiController( TescoDatabase database ) {
      this( PAGE_SIZE, new TescoApiConnector(), new TescoApiConverter( database ) );
   }//End Constructor
   
   TescoApiController( 
            int pageSize, 
            TescoApiConnector connector, 
            TescoApiConverter converter 
   ) {
      this.pageSize = pageSize;
      this.connector = connector;
      this.converter = converter;
   }//End Constructor

   public List< TescoFoodDescription > search( String criteria ) {
      int lastResponseCount = pageSize;
      int pageNumber = 1;
      
      List< TescoFoodDescription > searchResults = new ArrayList<>();
      while ( lastResponseCount == pageSize && pageNumber < PAGE_LIMIT ) {
         String response = connector.searchProducts( criteria, pageNumber, pageSize );
         List< TescoFoodDescription > descriptionsFound = converter.convertDescriptions( response );
         searchResults.addAll( descriptionsFound );
         
         lastResponseCount = descriptionsFound.size();
         pageNumber++;
      }
      return searchResults;
   }//End Method

}//End Class
