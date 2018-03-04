package uk.dangrew.nuts.apis.tesco.api;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class TescoApiConnectorTest {

   private TescoApiConnector systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TescoApiConnector();
   }//End Method

   @Ignore
   @Test public void manualProduct() {
      System.out.println( systemUnderTest.retrieveProduct( "76274694" ) );
   }//End Method
   
   @Ignore
   @Test public void manualSearch() {
      System.out.println( systemUnderTest.searchProducts( "a", 1, 1000 ) );
   }//End Method
   
   @Test public void shouldReturnValidResultForSimpleSearch(){
      String result = systemUnderTest.searchProducts( "coconut", 1, 10 );
      assertThat( result, containsString( "coconut" ) );
   }//End Method
   
   @Test public void shouldReturnValidResultForSimpleProductQuery(){
      String result = systemUnderTest.retrieveProduct( "76274694" );
      assertThat( result, containsString( "Coconut" ) );
   }//End Method
   
   @Test public void shouldHandleInvalidSearchCriteria(){
      String result = systemUnderTest.searchProducts( "", 1, 10 );
      assertThat( result, is( nullValue() ) );
      result = systemUnderTest.searchProducts( null, 1, 10 );
      assertThat( result, is( nullValue() ) );
   }//End Method
   
   @Test public void shouldCatchSpacesInSearchCriteria(){
      String result = systemUnderTest.searchProducts( "coconut flour", 1, 10 );
      assertThat( result, containsString( "coconut" ) );
   }//End Method

}//End Class
