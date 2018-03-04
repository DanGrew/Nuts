package uk.dangrew.nuts.apis.tesco.api;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.jtt.connection.data.json.conversion.ApiResponseToJsonConverter;
import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoGrocerySearchParserTest {

   private TescoFoodDescriptionStore store;
   private TescoParseRecorder recorder;
   private TescoGrocerySearchParser systemUnderTest;
   
   @Before public void initialiseSystemUnderTest(){
      store = new TescoFoodDescriptionStore();
      systemUnderTest = new TescoGrocerySearchParser( store );
      recorder = new TescoParseRecorder( store, systemUnderTest );
   }//End Method
   
   @Ignore
   @Test public void manual() {
      TescoApiConnector provider = new TescoApiConnector();
      String products = provider.searchProducts( "Conconut", 1, 100 );
      JSONObject json = new ApiResponseToJsonConverter().convert( products );
      
      systemUnderTest.parse( json );
      recorder.getAndClearRecordedDescriptions().forEach( f -> System.out.println( f.name().get() ) );
   }//End Method
   
   @Test public void shouldParseSampleFile(){
      JSONObject json = new JSONObject( TestCommon.readFileIntoString( getClass(), "grocery-search-sample.txt" ) );
      systemUnderTest.parse( json );
      
      List< TescoFoodDescription > descriptions = recorder.getAndClearRecordedDescriptions();
      
      TescoFoodDescription sampleTest = descriptions.get( 2 );
      assertThat( sampleTest.image().get(), is( "http://img.tesco.com/Groceries/pi/231/5052109963231/IDShot_90x90.jpg" ) );
      assertThat( sampleTest.superDepartment().get(), is( "Fresh Food" ) );
      assertThat( sampleTest.tpnb().get(), is( "70819043" ) );
      assertThat( sampleTest.unitOfSale().get(), is( 1.0 ) );
      assertThat( sampleTest.unitQuantity().get(), is( "100G" ) );
      assertThat( sampleTest.promotionDescription().get(), is( "25 extra points on any yoghurt Includes dairy free" ) );
      assertThat( sampleTest.contentsMeasureType().get(), is( "G" ) );
      assertThat( sampleTest.name().get(), is( "Tesco Low Fat Coconut Yogurt 450G" ) );
      assertThat( sampleTest.averageSellingUnitWeight().get(), is( 0.505 ) );
      assertThat( sampleTest.id().get(), is( "271656356" ) );
      assertThat( sampleTest.contentsQuantity().get(), is( 450.0 ) );
      assertThat( sampleTest.department().get(), is( "Yoghurts" ) );
      assertThat( sampleTest.price().get(), is( 0.9 ) );
      assertThat( sampleTest.unitPrice().get(), is( 0.2 ) );
      
      assertThat( sampleTest.description(), contains( 
               "Low fat coconut yogurt.", 
               "low fat coconut yogurt made with British milk, bursting with flavour"
      ) );
   }//End Method
   
}//End Class
