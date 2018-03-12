package uk.dangrew.nuts.apis.tesco.api.webapi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.jupa.json.JsonHandle;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoGrocerySearchParser;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoParseRecorder;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;

public class TescoParseRecorderTest {

   @Captor private ArgumentCaptor< JsonHandle > interceptorCaptor;
   private JsonHandle interceptor;
   
   private JSONObject json;
   
   @Mock private TescoGrocerySearchParser groceryParser;
   private TescoFoodDescriptionStore descriptionStore;
   private TescoParseRecorder systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      json = new JSONObject();
      
      descriptionStore = new TescoFoodDescriptionStore();
      descriptionStore.createConcept( "a" ).groceryProperties().tpnb().set( "a" );
      descriptionStore.createConcept( "b" ).groceryProperties().tpnb().set( "b" );
      descriptionStore.createConcept( "c" ).groceryProperties().tpnb().set( "c" );
      descriptionStore.createConcept( "d" ).groceryProperties().tpnb().set( "d" );
      
      systemUnderTest = new TescoParseRecorder( descriptionStore, groceryParser );
      
      verify( groceryParser ).intercept( eq( TescoGrocerySearchParser.TPNB ), interceptorCaptor.capture() );
      interceptor = interceptorCaptor.getValue();
   }//End Method

   @Test public void shouldInterceptTpnbProperty() {
      assertThat( interceptor, notNullValue() );
   }//End Method
   
   @Test public void shouldResolveInterceptedValues(){
      json.put( TescoGrocerySearchParser.TPNB, "b" );
      interceptor.handle( TescoGrocerySearchParser.TPNB, json );
      
      assertThat( systemUnderTest.getAndClearRecordedDescriptions(), contains( descriptionStore.objectList().get( 1 ) ) );
      
      json.put( TescoGrocerySearchParser.TPNB, "a" );
      interceptor.handle( TescoGrocerySearchParser.TPNB, json );
      
      json.put( TescoGrocerySearchParser.TPNB, "d" );
      interceptor.handle( TescoGrocerySearchParser.TPNB, json );
      
      assertThat( systemUnderTest.getAndClearRecordedDescriptions(), contains( 
               descriptionStore.objectList().get( 0 ),
               descriptionStore.objectList().get( 3 )
      ) );
   }//End Method
   
   @Test public void shouldIgnoreInterceptedValueIfNotInDatabase(){
      json.put( TescoGrocerySearchParser.TPNB, "b" );
      interceptor.handle( TescoGrocerySearchParser.TPNB, json );
      
      json.put( TescoGrocerySearchParser.TPNB, "e" );
      interceptor.handle( TescoGrocerySearchParser.TPNB, json );
      
      assertThat( systemUnderTest.getAndClearRecordedDescriptions(), contains( descriptionStore.objectList().get( 1 ) ) );
   }//End Method
   
   @Test public void shouldClearInterceptedBetweenParses(){
      json.put( TescoGrocerySearchParser.TPNB, "b" );
      interceptor.handle( TescoGrocerySearchParser.TPNB, json );
      
      assertThat( systemUnderTest.getAndClearRecordedDescriptions(), contains( descriptionStore.objectList().get( 1 ) ) );
      assertThat( systemUnderTest.getAndClearRecordedDescriptions(), is( empty() ) );
   }//End Method

}//End Class
