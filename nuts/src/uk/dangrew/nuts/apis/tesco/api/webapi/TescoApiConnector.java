package uk.dangrew.nuts.apis.tesco.api.webapi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TescoApiConnector {

   static final String TESCO_LABS_BASE_LOCATION = "https://dev.tescolabs.com/";
   static final String TESCO_LABS_PRODUCT_QUERY = TESCO_LABS_BASE_LOCATION + "product/";
   static final String TESCO_LABS_GROCERY_QUERY = TESCO_LABS_BASE_LOCATION + "grocery/products/?query=";
   
   static final String GLOBAL_TRADE_IDENTIFICATION_NUMBER_PARAMETER_KEY = "gtin";
   static final String TESCO_PRODUCT_NUMBER_FOR_BASE_PRODUCT_PARAMETER_KEY = "tpnb";
   static final String TESCO_PRODUCT_NUMBER_FOR_CONSUMER_UNIT_PARAMETER_KEY = "tpnc";
   static final String TESCO_CATALOGUE_NUMBER_PARAMETER_KEY = "catid";
   
   static final String SUBSCRIPTION_ID = "fded68eb986b48268fd737cc24563211";
   static final String OCP_APIM_SUBSCRIPTION_KEY = "Ocp-Apim-Subscription-Key";

   public String retrieveProduct( String productNumberForBaseProduct ) {
      return retrieveProduct( null, productNumberForBaseProduct, null, null );
   }//End Method
   
   String retrieveProduct( String gtin, String tpnb, String tpnc, String catid ) {
      try {
         URIBuilder builder = new URIBuilder( TESCO_LABS_PRODUCT_QUERY );
         if ( gtin != null ) {
            builder.setParameter( GLOBAL_TRADE_IDENTIFICATION_NUMBER_PARAMETER_KEY, gtin );
         }
         if ( tpnb != null ) {
            builder.setParameter( TESCO_PRODUCT_NUMBER_FOR_BASE_PRODUCT_PARAMETER_KEY, tpnb );
         }
         if ( tpnc != null ) {
            builder.setParameter( TESCO_PRODUCT_NUMBER_FOR_CONSUMER_UNIT_PARAMETER_KEY, tpnc );
         }
         if ( catid != null ) {
            builder.setParameter( TESCO_CATALOGUE_NUMBER_PARAMETER_KEY, catid );
         }
         return request( builder );
      } catch ( URISyntaxException e ) {
         System.out.println( "Invalid query constructed for product details request." );
         e.printStackTrace();
         return null;
      }
   }

   public String searchProducts( String criteria, int pageNumber, int countPerPage ) {
      if ( criteria == null || criteria.isEmpty() ) {
         return null;
      }
      try {
         URIBuilder builder = new URIBuilder( query( criteria, pageNumber, countPerPage ) );
         return request( builder );
      } catch ( URISyntaxException e ) {
         System.out.println( "Invalid query constructed for product search." );
         e.printStackTrace();
         return null;
      }
   }//End Method
   
   private String request( URIBuilder builder ) throws URISyntaxException {
      URI uri = builder.build();
      HttpGet request = new HttpGet( uri );
      request.setHeader( OCP_APIM_SUBSCRIPTION_KEY, SUBSCRIPTION_ID );

      CloseableHttpClient httpclient = HttpClients.createDefault();
      try {
         HttpResponse response = httpclient.execute( request );
         HttpEntity entity = response.getEntity();
         
         if ( entity != null ) {
            return EntityUtils.toString( entity );
         }
      } catch ( IOException e ) {
         System.out.println( "Error parsing data from from Tesco, with valid request." );
         e.printStackTrace();
      } finally {
         try {
            httpclient.close();
         } catch ( IOException e ) {
            System.out.println( "Error tidying and closing http connection." );
            e.printStackTrace();
         }
      }
      return null;
   }//End Method
   
   private String query( String criteria, int pageNumber, int countPerPage ) {
      String offsetConcat = "&offset=";
      int offset = ( pageNumber - 1 ) * countPerPage;
      String limitConcat = "&limit=";
      return TESCO_LABS_GROCERY_QUERY + handleSpaces( criteria ) + offsetConcat + offset + limitConcat + countPerPage;
   }//End Method
   
   private String handleSpaces( String value ) {
      return value.replaceAll( " ", "%20" );
   }//End Method

}//End Class
