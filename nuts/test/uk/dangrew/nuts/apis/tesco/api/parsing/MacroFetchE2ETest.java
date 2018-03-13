package uk.dangrew.nuts.apis.tesco.api.parsing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.jtt.connection.data.json.conversion.ApiResponseToJsonConverter;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoApiConnector;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoGrocerySearchParser;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoParseRecorder;
import uk.dangrew.nuts.apis.tesco.api.webapi.TescoProductDetailParser;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;

public class MacroFetchE2ETest {

   @Test public void shouldConnectToTescoAndParseFullUseCase() {
      TescoApiConnector provider = new TescoApiConnector();
      String products = provider.searchProducts( "Chicken", 1, 100 );
      JSONObject json = new ApiResponseToJsonConverter().convert( products );
      
      TescoFoodDescriptionStore store = new TescoFoodDescriptionStore();
      TescoProductDetailParser productParser = new TescoProductDetailParser( store );
      TescoGrocerySearchParser groceryParser = new TescoGrocerySearchParser( store );
      TescoParseRecorder recorder = new TescoParseRecorder( store, groceryParser );
      
      groceryParser.parse( json );
      recorder.getAndClearRecordedDescriptions().forEach( f -> System.out.println( f.groceryProperties().name().get() ) );
      
      boolean atLeastOneKcalPopulated = false;
      boolean atLeastOneCarbsPopulated = false;
      boolean atLeastOneFatsPopulated = false;
      boolean atLeastOneProteinPopulated = false;
      
      for ( int i = 0; i < 10; i++ ) {
         TescoFoodDescription description = store.objectList().get( i );
         String detail = provider.retrieveProduct( description.groceryProperties().tpnb().get() );
         
         json = new ApiResponseToJsonConverter().convert( detail );
         productParser.parse( json );
         
         System.out.println();
         System.out.println( description.properties().nameProperty().get() + " per 100g" );
         System.out.println( "kcal: " + description.productDetail().nutrition().energyInKcal().valuePer100().get() );
         System.out.println( "carbs: " + description.productDetail().nutrition().carbohydrates().valuePer100().get() );
         System.out.println( "fats: " + description.productDetail().nutrition().fat().valuePer100().get() );
         System.out.println( "protein: " + description.productDetail().nutrition().protein().valuePer100().get() );
         
         atLeastOneKcalPopulated |= description.productDetail().nutrition().energyInKcal().valuePer100().get() != null;
         atLeastOneCarbsPopulated |= description.productDetail().nutrition().carbohydrates().valuePer100().get() != null;
         atLeastOneFatsPopulated |= description.productDetail().nutrition().fat().valuePer100().get() != null;
         atLeastOneProteinPopulated |= description.productDetail().nutrition().protein().valuePer100().get() != null;
      }
      
      assertThat( atLeastOneKcalPopulated, is( true ) );
      assertThat( atLeastOneCarbsPopulated, is( true ) );
      assertThat( atLeastOneFatsPopulated, is( true ) );
      assertThat( atLeastOneProteinPopulated, is( true ) );
   }//End Method
   
}//End Class
