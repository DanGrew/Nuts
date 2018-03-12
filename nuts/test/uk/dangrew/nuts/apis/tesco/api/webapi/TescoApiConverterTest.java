package uk.dangrew.nuts.apis.tesco.api.webapi;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jtt.connection.data.json.conversion.ApiResponseToJsonConverter;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoApiConverter;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoGrocerySearchParser;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoParseRecorder;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoProductDetailParser;
import uk.dangrew.nuts.apis.tesco.model.TescoFoodDescription;

public class TescoApiConverterTest {

   private static final String RESULT = "any result from api";
   
   @Mock private JSONObject json;
   private List< TescoFoodDescription > resultingDescriptions;
   
   @Mock private TescoGrocerySearchParser groceryParser;
   @Mock private TescoProductDetailParser productDetailParser;
   @Mock private TescoParseRecorder parseRecorder;
   @Mock private ApiResponseToJsonConverter converter;
   private TescoApiConverter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      resultingDescriptions = Arrays.asList( new TescoFoodDescription( "a" ), new TescoFoodDescription( "b" ) );
      systemUnderTest = new TescoApiConverter( 
               converter, 
               parseRecorder, 
               groceryParser,
               productDetailParser
      );
   }//End Method

   @Test public void shouldParseSearchIntoStore() {
      when( converter.convert( RESULT ) ).thenReturn( json );
      when( parseRecorder.getAndClearRecordedDescriptions() ).thenReturn( resultingDescriptions );
      
      assertThat( systemUnderTest.importGrocerySearchResponse( RESULT ), is( resultingDescriptions ) );
      
      InOrder verifier = inOrder( groceryParser, parseRecorder );
      verifier.verify( groceryParser ).parse( json );
      verifier.verify( parseRecorder ).getAndClearRecordedDescriptions();
   }//End Method
   
   @Test public void shouldIgnoreInvalidConversion() {
      assertThat( systemUnderTest.importGrocerySearchResponse( RESULT ), is( new ArrayList<>() ) );
   }//End Method
   
   @Test public void shouldParseProductDetailIntoDescriptions(){
      when( converter.convert( RESULT ) ).thenReturn( json );
      
      systemUnderTest.importProductDetailResponse( RESULT );
      verify( productDetailParser ).parse( json );
   }//End Method
   
}//End Class
