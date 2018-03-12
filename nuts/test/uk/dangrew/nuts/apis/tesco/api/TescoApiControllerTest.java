package uk.dangrew.nuts.apis.tesco.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoApiConnector;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoApiConverter;
import uk.dangrew.nuts.apis.tesco.api.webpage.TescoWebsiteParser;
import uk.dangrew.nuts.apis.tesco.model.TescoFoodDescription;

public class TescoApiControllerTest {

   private static final int PAGE_SIZE = 2;
   private static final String RESULT = "some result";
   
   private TescoFoodDescription description; 
   
   @Mock private TescoWebsiteParser websiteParser;
   @Mock private TescoApiConverter converter;
   @Mock private TescoApiConnector connector;
   private TescoApiController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      description = new TescoFoodDescription( "Anything" );
      
      description.groceryProperties().tpnb().set( "tpnbValue" );
      when( connector.retrieveProduct( "tpnbValue" ) ).thenReturn( RESULT );
      systemUnderTest = new TescoApiController( PAGE_SIZE, connector, converter, websiteParser );
   }//End Method

   @Test public void shouldSearchThroughAllPages() {
      final String searchCriteria = "coconut";
      
      List< TescoFoodDescription > firstPage = Arrays.asList( new TescoFoodDescription( "a" ), new TescoFoodDescription( "b" ) );
      List< TescoFoodDescription > secondPage = Arrays.asList( new TescoFoodDescription( "c" ), new TescoFoodDescription( "d" ) );
      List< TescoFoodDescription > thirdPage = Arrays.asList( new TescoFoodDescription( "e" ), new TescoFoodDescription( "f" ) );
      List< TescoFoodDescription > fourthPage = Arrays.asList( new TescoFoodDescription( "g" ), new TescoFoodDescription( "h" ) );
      List< TescoFoodDescription > fifthPage = Arrays.asList( new TescoFoodDescription( "i" ) );
      
      String firstResult = "firstResult";
      String secondResult = "secondResult";
      String thirdResult = "thirdResult";
      String fourthResult = "fourthResult";
      String fifthResult = "fifthResult";
      
      when( connector.searchProducts( searchCriteria, 1, PAGE_SIZE ) ).thenReturn( firstResult );
      when( connector.searchProducts( searchCriteria, 2, PAGE_SIZE ) ).thenReturn( secondResult );
      when( connector.searchProducts( searchCriteria, 3, PAGE_SIZE ) ).thenReturn( thirdResult );
      when( connector.searchProducts( searchCriteria, 4, PAGE_SIZE ) ).thenReturn( fourthResult );
      when( connector.searchProducts( searchCriteria, 5, PAGE_SIZE ) ).thenReturn( fifthResult );
      
      when( converter.importGrocerySearchResponse( firstResult ) ).thenReturn( firstPage );
      when( converter.importGrocerySearchResponse( secondResult ) ).thenReturn( secondPage );
      when( converter.importGrocerySearchResponse( thirdResult ) ).thenReturn( thirdPage );
      when( converter.importGrocerySearchResponse( fourthResult ) ).thenReturn( fourthPage );
      when( converter.importGrocerySearchResponse( fifthResult ) ).thenReturn( fifthPage );
      
      List< TescoFoodDescription > expectedSearchResult = new ArrayList<>();
      expectedSearchResult.addAll( firstPage );
      expectedSearchResult.addAll( secondPage );
      expectedSearchResult.addAll( thirdPage );
      expectedSearchResult.addAll( fourthPage );
      expectedSearchResult.addAll( fifthPage );
      
      assertThat( systemUnderTest.search( searchCriteria ), is( expectedSearchResult ) );
      verify( connector, never() ).searchProducts( "coconut", 0, PAGE_SIZE );
      verify( connector ).searchProducts( searchCriteria, 1, PAGE_SIZE );
      verify( connector ).searchProducts( searchCriteria, 2, PAGE_SIZE );
      verify( connector ).searchProducts( searchCriteria, 3, PAGE_SIZE );
      verify( connector ).searchProducts( searchCriteria, 4, PAGE_SIZE );
      verify( connector ).searchProducts( searchCriteria, 5, PAGE_SIZE );
      verify( connector, never() ).searchProducts( "coconut", 6, PAGE_SIZE );
   }//End Method
   
   @Test public void shouldNotSearchIndefinitely(){
      final String searchCriteria = "coconut";
      
      List< TescoFoodDescription > page = Arrays.asList( new TescoFoodDescription( "a" ), new TescoFoodDescription( "b" ) );
      String firstResult = "firstResult";
      
      when( connector.searchProducts( Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt() ) ).thenReturn( firstResult );
      when( converter.importGrocerySearchResponse( firstResult ) ).thenReturn( page );
      
      systemUnderTest.search( searchCriteria );
      verify( connector, never() ).searchProducts( "coconut", 0, PAGE_SIZE );
      for ( int i = 1; i < TescoApiController.PAGE_LIMIT; i++ ) {
         verify( connector ).searchProducts( searchCriteria, i, PAGE_SIZE );
      }
      verify( connector, never() ).searchProducts( searchCriteria, TescoApiController.PAGE_LIMIT, PAGE_SIZE );
   }//End Method
   
   @Test public void shouldDownloadAndParseProductDetailUsingWebApi(){
      doAnswer( i -> {
         description.productDetail().nutrition().per100Header().set( "not null" );
         description.productDetail().nutrition().perServingHeader().set( "not null" );
         return null;
      } ).when( converter ).importProductDetailResponse( RESULT );
      
      systemUnderTest.downloadProductDetail( description );
      verify( converter ).importProductDetailResponse( RESULT );
      verify( websiteParser, never() ).parseNutritionFor( description );
   }//End Method
   
   @Test public void shouldNotDownloadProductDetailIfDescriptionHasNoTpnb() {
      description.groceryProperties().tpnb().set( null );
      systemUnderTest.downloadProductDetail( description );
      verify( connector, never() ).retrieveProduct( Mockito.anyString() );
   }//End Method
   
   @Test public void shouldUseWebsiteParserIfProductDetailNotProvided(){
      systemUnderTest.downloadProductDetail( description );
      verify( converter ).importProductDetailResponse( RESULT );
      verify( websiteParser ).parseNutritionFor( description );
   }//End Method

}//End Class
