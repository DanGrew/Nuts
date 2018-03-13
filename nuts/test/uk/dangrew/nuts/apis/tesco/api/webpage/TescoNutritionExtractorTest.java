package uk.dangrew.nuts.apis.tesco.api.webpage;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.api.NutritionAsserter;
import uk.dangrew.nuts.apis.tesco.api.parsing.CalculatedNutritionParsingHandler;
import uk.dangrew.nuts.apis.tesco.api.webpage.TescoNutritionExtractor;
import uk.dangrew.nuts.apis.tesco.api.webpage.TescoWebpageNutritionTable;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;
import uk.dangrew.nuts.manual.data.TescoExamples;

public class TescoNutritionExtractorTest {

   private TescoFoodDescription description;
   
   private CalculatedNutritionParsingHandler model;
   private TescoWebpageNutritionTable table;
   private TescoNutritionExtractor systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      model = new CalculatedNutritionParsingHandler();
      table = new TescoWebpageNutritionTable();
      description = TescoExamples.cravendaleMilk();
      model.setCurrentNutrition( description.productDetail().nutrition() );
      systemUnderTest = new TescoNutritionExtractor( table, model );
   }//End Method
   
   @Test public void integrationTestWithRealData(){
      Document document = TescoExamples.crandaleMilkHtml();
      table = new TescoWebpageNutritionTable( document );
      systemUnderTest = new TescoNutritionExtractor( table, model );
      
      model.setCurrentNutrition( description.productDetail().nutrition() );
      systemUnderTest.run();
      new NutritionAsserter()
         .per100Header( "Typical Values Per 100ml" )
         .perServingHeader( "Per 200ml" )
         .energyInKcalName( "Energy (kJ/kcal)" )
         .energyInKcalValuePer100( "65" )
         .energyInKcalValuePerServing( "130" )
         .energyInKjName( "Energy (kJ/kcal)" )
         .energyInKjValuePer100( "271" )
         .energyInKjValuePerServing( "542" )
         .fatName( "Fat" )
         .fatValuePer100( "3.6" )
         .fatValuePerServing( "7.2" )
         .saturatesName( "Of which saturates" )
         .saturatesValuePer100( "2.3" )
         .saturatesValuePerServing( "4.6" )
         .carbohydratesName( "Carbohydrate" )
         .carbohydratesValuePer100( "4.7" )
         .carbohydratesValuePerServing( "9.4" )
         .sugarsName( "Of which sugars" )
         .sugarsValuePer100( "4.7" )
         .sugarsValuePerServing( "9.4" )
         .proteinName( "Protein" )
         .proteinValuePer100( "3.4" )
         .proteinValuePerServing( "6.8" )
         .saltName( "Salt" )
         .saltValuePer100( "0.1" )
         .saltValuePerServing( "0.2" )
         .calciumName( "Calcium" )
         .calciumValuePer100( "122" )
         .calciumValuePerServing( "243" )
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method
   
   @Test public void shouldNotExtractRowExclusion(){
      table.modifyColumnRow( 0, 1, TescoNutritionExtractor.EXCLUSION_REFRENCE_INTAKE + " something energy" );
      table.modifyColumnRow( 1, 1, "200kcal" );
      table.modifyColumnRow( 2, 1, "400kcal" );
      
      systemUnderTest.run();
      assertThat( description.productDetail().nutrition().energyInKcal().valuePer100().get(), is( nullValue() ) );
      assertThat( description.productDetail().nutrition().energyInKcal().valuePerServing().get(), is( nullValue() ) );
   }//End Method 
   
   @Test public void shouldNotExtractColumnExclusion(){
      table.modifyColumnRow( 0, 0, "Typical Values" );
      table.modifyColumnRow( 1, 0, "100g" );
      table.modifyColumnRow( 2, 0, "-" );
      table.modifyColumnRow( 0, 1, "Energy (kcal/kj)" );
      table.modifyColumnRow( 1, 1, "200kcal" );
      table.modifyColumnRow( 2, 1, "400kcal" );
      
      systemUnderTest.run();
      assertThat( description.productDetail().nutrition().energyInKcal().valuePer100().get(), is( "200" ) );
      assertThat( description.productDetail().nutrition().energyInKcal().valuePerServing().get(), is( nullValue() ) );
   }//End Method 
   
   @Test public void shouldDetectSplitEnergyRows(){
      table.modifyColumnRow( 0, 1, "Energy" );
      table.modifyColumnRow( 1, 1, "45kj" );
      table.modifyColumnRow( 2, 1, "9kj" );
      table.modifyColumnRow( 0, 2, "-" );
      table.modifyColumnRow( 1, 2, "200kcal" );
      table.modifyColumnRow( 2, 2, "400kcal" );
      
      systemUnderTest.run();
      assertThat( description.productDetail().nutrition().energyInKcal().valuePer100().get(), is( "200" ) );
      assertThat( description.productDetail().nutrition().energyInKcal().valuePerServing().get(), is( "400" ) );
      assertThat( description.productDetail().nutrition().energyInKj().valuePer100().get(), is( "45" ) );
      assertThat( description.productDetail().nutrition().energyInKj().valuePerServing().get(), is( "9" ) );
   }//End Method
   
   @Test public void shouldDetectSplitEnergyRowsWithCombinedLabel(){
      table.modifyColumnRow( 0, 1, "Energy (kcal/kj)" );
      table.modifyColumnRow( 1, 1, "45kj" );
      table.modifyColumnRow( 2, 1, "9kj" );
      table.modifyColumnRow( 0, 2, "-" );
      table.modifyColumnRow( 1, 2, "200kcal" );
      table.modifyColumnRow( 2, 2, "400kcal" );
      
      systemUnderTest.run();
      assertThat( description.productDetail().nutrition().energyInKcal().valuePer100().get(), is( "200" ) );
      assertThat( description.productDetail().nutrition().energyInKcal().valuePerServing().get(), is( "400" ) );
      assertThat( description.productDetail().nutrition().energyInKj().valuePer100().get(), is( "45" ) );
      assertThat( description.productDetail().nutrition().energyInKj().valuePerServing().get(), is( "9" ) );
   }//End Method
   
}//End Class
