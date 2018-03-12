package uk.dangrew.nuts.apis.tesco.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.item.TescoFoodDescription;
import uk.dangrew.nuts.manual.data.TescoExamples;

public class TescoWebsiteParserTest {

   private TescoWebsiteParser systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TescoWebsiteParser();
   }//End Method

   @Test public void shouldParseFromWebsiteRealIntegrationTest() {
      TescoFoodDescription description = TescoExamples.cravendaleMilk();
      
      systemUnderTest.parseNutritionFor( description );
     
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
         .calciumValuePer100( "122" )
         .calciumValuePerServing( "243" )
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method
   
//   @Test public void shouldIgnoreMissingDescription(){
//      fail();
//   }//End Method
//   
//   @Test public void shouldIgnoreInvalidDescription(){
//      fail();
//   }//End Method
//   
//   @Test public void shouldHandleMissingTableTag(){
//      fail();
//   }//End Method
//   
//   @Test public void shouldHandleMissingHeaders(){
//      fail();
//   }//End Method
//   
//   @Test public void shouldHandleMissingRows(){
//      fail();
//   }//End Method
//   
//   @Test public void shouldSupportNonNutritionalProducts(){
//      fail();
//      //shampoo hung - Alberto Balsam Coconut And Lychee Shampoo 350Ml
//   }//End Method
//   
//   @Ignore
//   @Test public void shouldHandleInvalidUrl(){
//      fail();
//      //254142016 - doesnt exist - handle
//   }//End Method
   
   @Test public void variation254852996() {
      TescoFoodDescription description = new TescoFoodDescription( "Weetabix Cereal 24 Pack" );
      description.productDetail().tpncs().add( "254852996" );
      
      systemUnderTest.parseNutritionFor( description );
     
      new NutritionAsserter()
         .per100Header( "Typical Values Per 100g" )
         .perServingHeader( "Per 2 biscuit serving" )
         .energyInKcalValuePer100( "362" )
         .energyInKcalValuePerServing( "136" )
         .energyInKjValuePer100( "1531" )
         .energyInKjValuePerServing( "574" )
         .fatValuePer100( "2.0" )
         .fatValuePerServing( "0.8" )
         .saturatesValuePer100( "0.6" )
         .saturatesValuePerServing( "0.2" )
         .carbohydratesValuePer100( "69" )
         .carbohydratesValuePerServing( "26" )
         .fibreValuePer100( "10" )
         .fibreValuePerServing( "3.8" )
         .sugarsValuePer100( "4.4" )
         .sugarsValuePerServing( "1.7" )
         .proteinValuePer100( "12" )
         .proteinValuePerServing( "4.5" )
         .saltValuePer100( "0.28" )
         .saltValuePerServing( "0.10" )
         .thiaminValuePer100( "0.94" )
         .thiaminValuePerServing( "0.35" )
         .riboflavinValuePer100( "1.2" )
         .riboflavinValuePerServing( "0.45" )
         .niacinValuePer100( "14" )
         .niacinValuePerServing( "5.3" )
         .folicAcidValuePer100( "170" )
         .folicAcidValuePerServing( "64" )
         .ironValuePer100( "12" )
         .ironValuePerServing( "4.5" )
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method
   
   @Test public void variation278371514() {
      TescoFoodDescription description = new TescoFoodDescription( "Mcvities Milk Chocolate Hobnobs 262G" );
      description.productDetail().tpncs().add( "278371514" );
      
      systemUnderTest.parseNutritionFor( description );
     
      new NutritionAsserter()
         .per100Header( "Typical Values Per 100g" )
         .perServingHeader( "Per Biscuit (18.8g)" )
         .energyInKcalValuePer100( "491" )
         .energyInKcalValuePerServing( "92" )
         .energyInKjValuePer100( "2056" )
         .energyInKjValuePerServing( "387" )
         .fatValuePer100( "23.3" )
         .fatValuePerServing( "4.4" )
         .saturatesValuePer100( "11.5" )
         .saturatesValuePerServing( "2.2" )
         .carbohydratesValuePer100( "60.8" )
         .carbohydratesValuePerServing( "11.4" )
         .sugarsValuePer100( "33.0" )
         .sugarsValuePerServing( "6.2" )
         .fibreValuePer100( "5.1" )
         .fibreValuePerServing( "1.0" )
         .proteinValuePer100( "7.1" )
         .proteinValuePerServing( "1.3" )
         .saltValuePer100( "0.7" )
         .saltValuePerServing( "0.1" )
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method
   
   @Test public void variation274811214() {
      TescoFoodDescription description = new TescoFoodDescription( "Hovis Original 7 Seeds Bread 800G" );
      description.productDetail().tpncs().add( "274811214" );
      
      systemUnderTest.parseNutritionFor( description );
     
      new NutritionAsserter()
         .per100Header( "Typical Values Per 100g" )
         .perServingHeader( "Per Slice 44g" )
         .energyInKcalValuePer100( "278" )
         .energyInKcalValuePerServing( "122" )
         .energyInKjValuePer100( "1171" )
         .energyInKjValuePerServing( "515" )
         .fatValuePer100( "6.6" )
         .fatValuePerServing( "2.9" )
         .saturatesValuePer100( "0.6" )
         .saturatesValuePerServing( "0.3" )
         .carbohydratesValuePer100( "42.0" )
         .carbohydratesValuePerServing( "18.5" )
         .sugarsValuePer100( "3.8" )
         .sugarsValuePerServing( "1.7" )
         .fibreValuePer100( "5.3" )
         .fibreValuePerServing( "2.3" )
         .proteinValuePer100( "10.0" )
         .proteinValuePerServing( "4.4" )
         .saltValuePer100( "0.88" )
         .saltValuePerServing( "0.39" )
         .omega3ValuePer100( "2.18" )
         .omega3ValuePerServing( "0.96" )
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method
   
   @Test public void variation296056855() {
      TescoFoodDescription description = new TescoFoodDescription( "Boswell Farms Beef Mince 1Kg 20% Fat" );
      description.productDetail().tpncs().add( "296056855" );
      
      systemUnderTest.parseNutritionFor( description );
     
      new NutritionAsserter()
         .per100Header( "100g raw as sold" )
         .energyInKcalValuePer100( "252" )
         .energyInKjValuePer100( "1045" )
         .fatValuePer100( "19.8" )
         .saturatesValuePer100( "9.8" )
         .carbohydratesValuePer100( "0" )
         .sugarsValuePer100( "0" )
         .fibreValuePer100( "0" )
         .proteinValuePer100( "18.4" )
         .saltValuePer100( "0.4" )
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method
}//End Class
