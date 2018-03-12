package uk.dangrew.nuts.apis.tesco.graphics.selection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.api.CalculatedNutritionType;

public class TescoStringParserTest {

   private TescoStringParser systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TescoStringParser();
   }//End Method

   @Test public void shouldParsePer100Header() {
      assertThat( systemUnderTest.parsePer100Header( "100g contains" ), is( "100g" ) );
      assertThat( systemUnderTest.parsePer100Header( "100g" ), is( "100g" ) );
      assertThat( systemUnderTest.parsePer100Header( "for 100g" ), is( "100g" ) );
      assertThat( systemUnderTest.parsePer100Header( "for 100ml" ), is( "100ml" ) );
      assertThat( systemUnderTest.parsePer100Header( "100ml contains" ), is( "100ml" ) );
      assertThat( systemUnderTest.parsePer100Header( "not normal" ), is( "not normal" ) );
   }//End Method
   
   @Test public void shouldParsePerServingHeader() {
      assertThat( systemUnderTest.parsePerServingHeader( "Each tablespoon (15g) contains" ), is( "15g" ) );
      assertThat( systemUnderTest.parsePerServingHeader( "Per 15g" ), is( "15g" ) );
      assertThat( systemUnderTest.parsePerServingHeader( "Each 15ml Portion" ), is( "15ml" ) );
      assertThat( systemUnderTest.parsePerServingHeader( "Cup (15ml)" ), is( "15ml" ) );
   }//End Method
   
   @Test public void shouldProvideDefaultHeader(){
      assertThat( systemUnderTest.parsePer100Header( null ), is( TescoStringParser.UNKNOWN_PER_100 ) );
      assertThat( systemUnderTest.parsePerServingHeader( null ), is( TescoStringParser.UNKNOWN_PER_SERVING ) );
   }//End Method
   
   @Test public void shouldNotParseValues(){
      assertThat( systemUnderTest.parseNutritionType( "Energy" ), is( nullValue() ) );
      assertThat( systemUnderTest.parseNutritionType( "anything" ), is( nullValue() ) );
      assertThat( systemUnderTest.parseNutritionType( "Energy (kcal/kj)" ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldParseEnergyInKcalName() {
      assertThat( systemUnderTest.parseNutritionType( "Energy (kcal)" ), is( CalculatedNutritionType.EnergyInKcal ) );
      assertThat( systemUnderTest.parseNutritionType( "Energy kcal" ), is( CalculatedNutritionType.EnergyInKcal ) );
      assertThat( systemUnderTest.parseNutritionType( "Energy Kcal" ), is( CalculatedNutritionType.EnergyInKcal ) );
      assertThat( systemUnderTest.parseNutritionType( "Energy in kcal" ), is( CalculatedNutritionType.EnergyInKcal ) );
   }//End Method
   
   @Test public void shouldParseEnergyInKjName() {
      assertThat( systemUnderTest.parseNutritionType( "Energy (kj)" ), is( CalculatedNutritionType.EnergyInKj ) );
      assertThat( systemUnderTest.parseNutritionType( "Energy kj" ), is( CalculatedNutritionType.EnergyInKj ) );
      assertThat( systemUnderTest.parseNutritionType( "Energy kJ" ), is( CalculatedNutritionType.EnergyInKj ) );
      assertThat( systemUnderTest.parseNutritionType( "Energy Kj" ), is( CalculatedNutritionType.EnergyInKj ) );
      assertThat( systemUnderTest.parseNutritionType( "Energy in kj" ), is( CalculatedNutritionType.EnergyInKj ) );
   }//End Method
   
   @Test public void shouldParseFatName() {
      assertThat( systemUnderTest.parseNutritionType( "Fat" ), is( CalculatedNutritionType.Fats ) );
      assertThat( systemUnderTest.parseNutritionType( "Fats" ), is( CalculatedNutritionType.Fats ) );
      assertThat( systemUnderTest.parseNutritionType( "fat" ), is( CalculatedNutritionType.Fats ) );
      assertThat( systemUnderTest.parseNutritionType( " Fat " ), is( CalculatedNutritionType.Fats ) );
      assertThat( systemUnderTest.parseNutritionType( "fat (g)" ), is( CalculatedNutritionType.Fats ) );
   }//End Method
   
   @Test public void shouldParseSaturatesName() {
      assertThat( systemUnderTest.parseNutritionType( "Saturates" ), is( CalculatedNutritionType.Saturates ) );
      assertThat( systemUnderTest.parseNutritionType( "Saturate" ), is( CalculatedNutritionType.Saturates ) );
      assertThat( systemUnderTest.parseNutritionType( "saturate" ), is( CalculatedNutritionType.Saturates ) );
      assertThat( systemUnderTest.parseNutritionType( " of which saturates " ), is( CalculatedNutritionType.Saturates ) );
      assertThat( systemUnderTest.parseNutritionType( "Saturates (g)" ), is( CalculatedNutritionType.Saturates ) );
   }//End Method
   
   @Test public void shouldParseCarbohydratesName() {
      assertThat( systemUnderTest.parseNutritionType( "Carbohydrates" ), is( CalculatedNutritionType.Carbohydrate ) );
      assertThat( systemUnderTest.parseNutritionType( "Carbohydrate" ), is( CalculatedNutritionType.Carbohydrate ) );
      assertThat( systemUnderTest.parseNutritionType( "carbohydrates" ), is( CalculatedNutritionType.Carbohydrate ) );
      assertThat( systemUnderTest.parseNutritionType( " Carbohydrates " ), is( CalculatedNutritionType.Carbohydrate ) );
      assertThat( systemUnderTest.parseNutritionType( "Carbohydrates (g)" ), is( CalculatedNutritionType.Carbohydrate ) );
   }//End Method
   
   @Test public void shouldParseSugarsName() {
      assertThat( systemUnderTest.parseNutritionType( "Sugars" ), is( CalculatedNutritionType.Sugars ) );
      assertThat( systemUnderTest.parseNutritionType( "Sugar" ), is( CalculatedNutritionType.Sugars ) );
      assertThat( systemUnderTest.parseNutritionType( "sugar" ), is( CalculatedNutritionType.Sugars ) );
      assertThat( systemUnderTest.parseNutritionType( " Sugars " ), is( CalculatedNutritionType.Sugars ) );
      assertThat( systemUnderTest.parseNutritionType( "Sugars (g)" ), is( CalculatedNutritionType.Sugars ) );
   }//End Method
   
   @Test public void shouldParseFibreName() {
      assertThat( systemUnderTest.parseNutritionType( "Fibre" ), is( CalculatedNutritionType.Fibre ) );
      assertThat( systemUnderTest.parseNutritionType( "Fibres" ), is( CalculatedNutritionType.Fibre ) );
      assertThat( systemUnderTest.parseNutritionType( "fibre" ), is( CalculatedNutritionType.Fibre ) );
      assertThat( systemUnderTest.parseNutritionType( " Fibre " ), is( CalculatedNutritionType.Fibre ) );
      assertThat( systemUnderTest.parseNutritionType( "Fibre (g)" ), is( CalculatedNutritionType.Fibre ) );
   }//End Method
   
   @Test public void shouldParseProteinName() {
      assertThat( systemUnderTest.parseNutritionType( "Protein" ), is( CalculatedNutritionType.Protein ) );
      assertThat( systemUnderTest.parseNutritionType( "Proteins" ), is( CalculatedNutritionType.Protein ) );
      assertThat( systemUnderTest.parseNutritionType( "protein" ), is( CalculatedNutritionType.Protein ) );
      assertThat( systemUnderTest.parseNutritionType( " Protein " ), is( CalculatedNutritionType.Protein ) );
      assertThat( systemUnderTest.parseNutritionType( "Protein (g)" ), is( CalculatedNutritionType.Protein ) );
   }//End Method
   
   @Test public void shouldParseSaltName() {
      assertThat( systemUnderTest.parseNutritionType( "Salt" ), is( CalculatedNutritionType.Salt ) );
      assertThat( systemUnderTest.parseNutritionType( "Salts" ), is( CalculatedNutritionType.Salt ) );
      assertThat( systemUnderTest.parseNutritionType( "salt" ), is( CalculatedNutritionType.Salt ) );
      assertThat( systemUnderTest.parseNutritionType( " Salt " ), is( CalculatedNutritionType.Salt ) );
      assertThat( systemUnderTest.parseNutritionType( "Salt (g)" ), is( CalculatedNutritionType.Salt ) );
   }//End Method

   @Test public void shouldExtractKcalFromEnergyString(){
      assertThat( systemUnderTest.extractKcalFrom( "100kcal" ), is( "100" ) );
      assertThat( systemUnderTest.extractKcalFrom( "100Kcal" ), is( "100" ) );
      assertThat( systemUnderTest.extractKcalFrom( "100 kcal" ), is( "100" ) );
      assertThat( systemUnderTest.extractKcalFrom( "100" ), is( "100" ) );
      assertThat( systemUnderTest.extractKcalFrom( "3700.00kj900." ), is( "900" ) );
      assertThat( systemUnderTest.extractKcalFrom( "555.00kj135." ), is( "135" ) );
      assertThat( systemUnderTest.extractKcalFrom( "anything" ), is( "anything" ) );
      assertThat( systemUnderTest.extractKcalFrom( null ), is( nullValue() ) );
      assertThat( systemUnderTest.extractKcalFrom( "708.20kJ(169.90kcal)" ), is( "169.90" ) );
      assertThat( systemUnderTest.extractKcalFrom( ".90kcal)" ), is( ".90" ) );
      assertThat( systemUnderTest.extractKcalFrom( "1.kcal)" ), is( "1." ) );
      assertThat( systemUnderTest.extractKcalFrom( "1.90kcal)" ), is( "1.90" ) );
   }//End Method
   
   @Test public void shouldExtractKjFromEnergyString(){
      assertThat( systemUnderTest.extractKjFrom( "100kj" ), is( "100" ) );
      assertThat( systemUnderTest.extractKjFrom( "100Kj" ), is( "100" ) );
      assertThat( systemUnderTest.extractKjFrom( "100 kj" ), is( "100" ) );
      assertThat( systemUnderTest.extractKjFrom( "100" ), is( "100" ) );
      assertThat( systemUnderTest.extractKjFrom( "3700.00kj900." ), is( "3700.00" ) );
      assertThat( systemUnderTest.extractKjFrom( "555.00kj135." ), is( "555.00" ) );
      assertThat( systemUnderTest.extractKjFrom( "anything" ), is( "anything" ) );
      assertThat( systemUnderTest.extractKjFrom( null ), is( nullValue() ) );
      assertThat( systemUnderTest.extractKjFrom( "708.20kJ(169.90kcal)" ), is( "708.20" ) );
      assertThat( systemUnderTest.extractKjFrom( ".90kj)" ), is( ".90" ) );
      assertThat( systemUnderTest.extractKjFrom( "1.90kj)" ), is( "1.90" ) );
   }//End Method
   
   @Test public void shoudlExtractNumbers(){
      assertThat( systemUnderTest.extractNumber( "9.g" ), is( "9." ) );
      assertThat( systemUnderTest.extractNumber( ".10g" ), is( ".10" ) );
      assertThat( systemUnderTest.extractNumber( "9.10g" ), is( "9.10" ) );
      assertThat( systemUnderTest.extractNumber( "3.6" ), is( "3.6" ) );
      assertThat( systemUnderTest.extractNumber( "3" ), is( "3" ) );
      assertThat( systemUnderTest.extractNumber( "help3.6" ), is( "3.6" ) );
      assertThat( systemUnderTest.extractNumber( "3.6help" ), is( "3.6" ) );
      assertThat( systemUnderTest.extractNumber( "65kcal" ), is( "65" ) );
      assertThat( systemUnderTest.extractNumber( null ), is( nullValue() ) );
      assertThat( systemUnderTest.extractNumber( "anything" ), is( "anything" ) );
   }//End Method
   
   @Test public void shouldIdentifyCombinedEnergyStrings(){
      assertThat( systemUnderTest.isCombinedEnergy( "kj/kcal" ), is( true ) );
      assertThat( systemUnderTest.isCombinedEnergy( "kj kcal" ), is( true ) );
      assertThat( systemUnderTest.isCombinedEnergy( "2000kj 100kcal" ), is( true ) );
      assertThat( systemUnderTest.isCombinedEnergy( "kcalkj" ), is( true ) );
      assertThat( systemUnderTest.isCombinedEnergy( "kcal/kj" ), is( true ) );
      assertThat( systemUnderTest.isCombinedEnergy( "100kcal2000kj" ), is( true ) );
      assertThat( systemUnderTest.isCombinedEnergy( "energy" ), is( true ) );
      assertThat( systemUnderTest.isCombinedEnergy( "Energy" ), is( true ) );
      assertThat( systemUnderTest.isCombinedEnergy( "100Energyanything" ), is( true ) );
      assertThat( systemUnderTest.isCombinedEnergy( "Energy (kcal)" ), is( false ) );
      assertThat( systemUnderTest.isCombinedEnergy( "Energy (kj)" ), is( false ) );
      assertThat( systemUnderTest.isCombinedEnergy( "Energy (kj/kcal)" ), is( true ) );
      assertThat( systemUnderTest.isCombinedEnergy( null ), is( false ) );
   }//End Method
   
}//End Class
