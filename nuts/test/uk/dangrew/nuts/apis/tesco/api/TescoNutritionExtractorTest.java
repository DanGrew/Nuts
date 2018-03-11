package uk.dangrew.nuts.apis.tesco.api;

import static org.junit.Assert.fail;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;

public class TescoNutritionExtractorTest {

   private TescoFoodDescription description;
   
   private CalculatedNutritionParsingHandler model;
   private TescoWebpageNutritionTable table;
   private TescoNutritionExtractor systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      model = new CalculatedNutritionParsingHandler();
      
      description = new TescoFoodDescription( "Cravendale Filtered Whole Milk 2 Litre" );
      description.productDetail().tpncs().add( "257265436" );
      TescoWebsiteParser websiteParser = new TescoWebsiteParser();
      Document document = websiteParser.connectToProductPage( description );
      table = new TescoWebpageNutritionTable( document );
      systemUnderTest = new TescoNutritionExtractor();
   }//End Method

//   @Test public void shouldExtractFromTableIntoModel() {
//      fail( "Not yet implemented" );
//   }//End Method
   
   @Test public void integrationTestWithRealData(){
      model.setCurrentNutrition( description.productDetail().nutrition() );
      systemUnderTest.extract( table, model );
      new NutritionAsserter()
         .per100Header( "Typical Values Per 100ml" )
         .perServingHeader( "Per 200ml" )
         .energyInKcalValuePer100( "65" )
         .energyInKcalValuePerServing( "130" )
         .energyInKjValuePer100( "271" )
         .energyInKjValuePerServing( "542" )
         .fatValuePer100( "3.6" )
         .fatValuePerServing( "7.2" )
         .saturatesValuePer100( "2.3" )
         .saturatesValuePerServing( "4.6" )
         .carbohydratesValuePer100( "4.7" )
         .carbohydratesValuePerServing( "9.4" )
         .sugarsValuePer100( "4.7" )
         .sugarsValuePerServing( "9.4" )
         .proteinValuePer100( "3.4" )
         .proteinValuePerServing( "6.8" )
         .saltValuePer100( "0.1" )
         .saltValuePerServing( "0.2" )
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method

}//End Class
