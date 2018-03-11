package uk.dangrew.nuts.apis.tesco.api;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.apis.tesco.database.TescoFoodDescriptionStore;
import uk.dangrew.nuts.apis.tesco.item.GuidelineDailyAmountReference;
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
      assertThat( detail.tpncs().get( 0 ), is( "292718570-extra" ) );
      assertThat( detail.tpncs().get( 1 ), is( "292718570" ) );
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
      
      GuidelineDailyAmountReference gda = detail.gdas().get( "Guideline Amounts Per Serv" );
      assertThat( gda.description(), is( "Guideline Amounts Per Serv" ) );
      assertThat( gda.headers(), is( Arrays.asList( "Each tablespoon contains" ) ) );
      assertThat( gda.footers(), is( Arrays.asList( 
               "*Reference intake of an average adult (8400 kJ / 2000 kcal)",
               "Typical values per 100g: Energy 3700.00kJ(900.00kcal)" 
      ) ) );
      
      assertThat( gda.gda().energyGda().energyInKcal().get(), is( "135kcal" ) );
      assertThat( gda.gda().energyGda().amount().get(), is( "135kcal" ) );
      assertThat( gda.gda().energyGda().energyInKj().get(), is( "555kJ" ) );
      assertThat( gda.gda().energyGda().percent().get(), is( "7" ) );
      assertThat( gda.gda().energyGda().rating().get(), is( nullValue() ) );
      
      assertThat( gda.gda().fatGda().amount().get(), is( "15.0g" ) );
      assertThat( gda.gda().fatGda().percent().get(), is( "21" ) );
      assertThat( gda.gda().fatGda().rating().get(), is( "high" ) );
      
      assertThat( gda.gda().saturatesGda().amount().get(), is( "13.1g" ) );
      assertThat( gda.gda().saturatesGda().percent().get(), is( "66" ) );
      assertThat( gda.gda().saturatesGda().rating().get(), is( "high" ) );
      
      assertThat( gda.gda().sugarsGda().amount().get(), is( "0gg" ) );
      assertThat( gda.gda().sugarsGda().percent().get(), is( "0" ) );
      assertThat( gda.gda().sugarsGda().rating().get(), is( "low" ) );
      
      assertThat( gda.gda().saltGda().amount().get(), is( "0gg" ) );
      assertThat( gda.gda().saltGda().percent().get(), is( "0" ) );
      assertThat( gda.gda().saltGda().rating().get(), is( "low" ) );
      
      assertThat( detail.nutrition().per100Header().get(), is( "100g contains" ) );
      assertThat( detail.nutrition().perServingHeader().get(), is( "Each tablespoon (15g) contains" ) );
      
      assertThat( detail.nutrition().energyInKj().name(), is( "Energy (kJ)" ) );
      assertThat( detail.nutrition().energyInKj().valuePer100().get(), is( "3700" ) );
      assertThat( detail.nutrition().energyInKj().valuePerServing().get(), is( "555" ) );
      
      assertThat( detail.nutrition().energyInKcal().name(), is( "Energy (kcal)" ) );
      assertThat( detail.nutrition().energyInKcal().valuePer100().get(), is( "900" ) );
      assertThat( detail.nutrition().energyInKcal().valuePerServing().get(), is( "135" ) );
      
      assertThat( detail.nutrition().fat().name(), is( "Fat (g)" ) );
      assertThat( detail.nutrition().fat().valuePer100().get(), is( "100" ) );
      assertThat( detail.nutrition().fat().valuePerServing().get(), is( "15" ) );
      
      assertThat( detail.nutrition().saturates().name(), is( "Saturates (g)" ) );
      assertThat( detail.nutrition().saturates().valuePer100().get(), is( "87" ) );
      assertThat( detail.nutrition().saturates().valuePerServing().get(), is( "13.05" ) );
      
      assertThat( detail.nutrition().carbohydrates().name(), is( "Carbohydrate (g)" ) );
      assertThat( detail.nutrition().carbohydrates().valuePer100().get(), is( "a" ) );
      assertThat( detail.nutrition().carbohydrates().valuePerServing().get(), is( "b" ) );
      
      assertThat( detail.nutrition().sugars().name(), is( "Sugars (g)" ) );
      assertThat( detail.nutrition().sugars().valuePer100().get(), is( "c" ) );
      assertThat( detail.nutrition().sugars().valuePerServing().get(), is( "d" ) );
      
      assertThat( detail.nutrition().fibre().name(), is( "Fibre (g)" ) );
      assertThat( detail.nutrition().fibre().valuePer100().get(), is( "e" ) );
      assertThat( detail.nutrition().fibre().valuePerServing().get(), is( "f" ) );

      assertThat( detail.nutrition().protein().name(), is( "Protein (g)" ) );
      assertThat( detail.nutrition().protein().valuePer100().get(), is( "g" ) );
      assertThat( detail.nutrition().protein().valuePerServing().get(), is( "h" ) );
      
      assertThat( detail.nutrition().salt().name(), is( "Salt (g)" ) );
      assertThat( detail.nutrition().salt().valuePer100().get(), is( "i" ) );
      assertThat( detail.nutrition().salt().valuePerServing().get(), is( "j" ) );
      
      assertThat( detail.storageInstructions(), is( Arrays.asList( 
               "Store in a cool, dry place. This product is solid when refrigerated or kept below 24oC." 
      ) ) );
      assertThat( detail.marketingTextProperty().get(), is( 
               "Organic virgin coconut oil (Cocos nucifera) is cold pressed using fresh organic coconuts, "
               + "this produces a mild and aromatic flavour of fresh coconuts.\nOrganic virgin coconut oil "
               + "(Cocos nucifera) is cold pressed using fresh organic coconuts, this produces a mild and aromatic "
               + "flavour of fresh coconuts." 
      ) );
      
      assertThat( detail.packageDimensions().number().get(), is( 1.0 ) );
      assertThat( detail.packageDimensions().height().get(), is( 8.3 ) );
      assertThat( detail.packageDimensions().width().get(), is( 8.0 ) );
      assertThat( detail.packageDimensions().depth().get(), is( 8.1 ) );
      assertThat( detail.packageDimensions().dimensionUom().get(), is( "cm" ) );
      assertThat( detail.packageDimensions().weight().get(), is( 484.0 ) );
      assertThat( detail.packageDimensions().weightUom().get(), is( "g" ) );
      assertThat( detail.packageDimensions().volume().get(), is( 531.2 ) );
      assertThat( detail.packageDimensions().volumeUom().get(), is( "cc" ) );
   }//End Method

}//End Class
