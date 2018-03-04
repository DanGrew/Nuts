package uk.dangrew.nuts.apis.tesco.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
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
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoApiControllerTest {

   private static final int PAGE_SIZE = 2;
   
   @Mock private TescoApiConverter converter;
   @Mock private TescoApiConnector connector;
   private TescoApiController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TescoApiController( PAGE_SIZE, connector, converter );
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
      
      when( converter.convertDescriptions( firstResult ) ).thenReturn( firstPage );
      when( converter.convertDescriptions( secondResult ) ).thenReturn( secondPage );
      when( converter.convertDescriptions( thirdResult ) ).thenReturn( thirdPage );
      when( converter.convertDescriptions( fourthResult ) ).thenReturn( fourthPage );
      when( converter.convertDescriptions( fifthResult ) ).thenReturn( fifthPage );
      
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
      when( converter.convertDescriptions( firstResult ) ).thenReturn( page );
      
      systemUnderTest.search( searchCriteria );
      verify( connector, never() ).searchProducts( "coconut", 0, PAGE_SIZE );
      for ( int i = 1; i < TescoApiController.PAGE_LIMIT; i++ ) {
         verify( connector ).searchProducts( searchCriteria, i, PAGE_SIZE );
      }
      verify( connector, never() ).searchProducts( searchCriteria, TescoApiController.PAGE_LIMIT, PAGE_SIZE );
   }//End Method

}//End Class
