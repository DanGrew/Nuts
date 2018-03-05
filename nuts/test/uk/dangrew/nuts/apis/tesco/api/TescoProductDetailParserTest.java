package uk.dangrew.nuts.apis.tesco.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.item.ProductDetail;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoProductDetailParserTest {

   @Test public void shouldParseSampleDate() {
      String data = TestCommon.readFileIntoString( getClass(), "product-detail-sample.txt" );
      JSONObject json = new JSONObject( data );
      
      TescoFoodDescriptionStore store = new TescoFoodDescriptionStore();
      TescoProductDetailParser parser = new TescoProductDetailParser( store );
      parser.parse( json );
      
      TescoFoodDescription description = store.get( "081232414" );
      ProductDetail detail = description.productDetail();
      
      assertThat( detail.gtin().get(), is( "05054775980437" ) );
      assertThat( detail.tpnb().get(), is( "081232414" ) );
      assertThat( detail.tpnc().get(), is( "292718570" ) );
      assertThat( detail.description().get(), is( "Tesco Virgin Organic Coconut Oil 300Ml" ) );
      assertThat( detail.brand().get(), is( "TESCO" ) );
      
      assertThat( detail.quantityContents().quantity().get(), is( 300.0 ) );
      assertThat( detail.quantityContents().totalQuantity().get(), is( 301.0 ) );
      assertThat( detail.quantityContents().quantityUom().get(), is( "ml" ) );
      assertThat( detail.quantityContents().netContents().get(), is( "300ml ℮" ) );
      assertThat( detail.quantityContents().averageMeasure().get(), is( "Average Measure (e)" ) );
      
      assertThat( detail.characteristics().isFood().get(), is( true ) );
      assertThat( detail.characteristics().isDrink().get(), is( false ) );
      assertThat( detail.characteristics().healthScore().get(), is( 30.0 ) );
      assertThat( detail.characteristics().isHazardous().get(), is( false ) );
      assertThat( detail.characteristics().storageType().get(), is( "Ambient" ) );
      assertThat( detail.characteristics().isNonLiquidAnalgesic().get(), is( false ) );
      assertThat( detail.characteristics().containsLoperamide().get(), is( true ) );
   }//End Method

}//End Class
