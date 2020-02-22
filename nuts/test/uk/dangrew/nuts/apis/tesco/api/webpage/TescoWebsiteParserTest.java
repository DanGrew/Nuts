package uk.dangrew.nuts.apis.tesco.api.webpage;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.api.NutritionAsserter;
import uk.dangrew.nuts.apis.tesco.model.nuts.TescoFoodDescription;
import uk.dangrew.nuts.manual.data.TescoExamples;

public class TescoWebsiteParserTest {

   private TescoWebsiteParser systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TescoWebsiteParser();
   }//End Method

   @Ignore
   @Test public void shouldParseFromWebsiteRealIntegrationTest() {
      TescoFoodDescription description = TescoExamples.cravendaleMilk();
      
      systemUnderTest.parseNutritionFor( description );
     
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
   
   @Test public void shouldIgnoreMissingDescription(){
      systemUnderTest.parseNutritionFor( null );
   }//End Method
   
   @Test public void shouldIgnoreInvalidDescription(){
      systemUnderTest.parseNutritionFor( new TescoFoodDescription( "anything" ) );
   }//End Method
   
   @Test public void shouldSupportNonNutritionalProducts(){
      TescoFoodDescription description = new TescoFoodDescription( "Alberto Balsam Coconut And Lychee Shampoo 350Ml" );
      description.productDetail().tpncs().add( "291181564" );
      
      systemUnderTest.parseNutritionFor( description );
     
      new NutritionAsserter()
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method

   @Ignore
   @Test public void variation254852996() {
      TescoFoodDescription description = new TescoFoodDescription( "Weetabix Cereal 24 Pack" );
      description.productDetail().tpncs().add( "254852996" );
      
      systemUnderTest.parseNutritionFor( description );
     
      new NutritionAsserter()
         .per100Header( "Typical Values Per 100g" )
         .perServingHeader( "Per 2 biscuit serving" )
         .energyInKcalName( "Energy (kcal)" )
         .energyInKcalValuePer100( "362" )
         .energyInKcalValuePerServing( "136" )
         .energyInKjName( "Energy (kj)" )
         .energyInKjValuePer100( "1531" )
         .energyInKjValuePerServing( "574" )
         .fatName( "Fat" )
         .fatValuePer100( "2.0" )
         .fatValuePerServing( "0.8" )
         .saturatesName( "of which saturates" )
         .saturatesValuePer100( "0.6" )
         .saturatesValuePerServing( "0.2" )
         .carbohydratesName( "Carbohydrates" )
         .carbohydratesValuePer100( "69" )
         .carbohydratesValuePerServing( "26" )
         .fibreName( "Fibre" )
         .fibreValuePer100( "10" )
         .fibreValuePerServing( "3.8" )
         .sugarsName( "of which sugars" )
         .sugarsValuePer100( "4.4" )
         .sugarsValuePerServing( "1.7" )
         .proteinName( "Protein" )
         .proteinValuePer100( "12" )
         .proteinValuePerServing( "4.5" )
         .saltName( "Salt" )
         .saltValuePer100( "0.28" )
         .saltValuePerServing( "0.10" )
         .thiaminName( "Thiamin (B1)" )
         .thiaminValuePer100( "0.94" )
         .thiaminValuePerServing( "0.35" )
         .riboflavinName( "Riboflavin (B2)" )
         .riboflavinValuePer100( "1.2" )
         .riboflavinValuePerServing( "0.45" )
         .niacinName( "Niacin" )
         .niacinValuePer100( "14" )
         .niacinValuePerServing( "5.3" )
         .folicAcidName( "Folic Acid" )
         .folicAcidValuePer100( "170" )
         .folicAcidValuePerServing( "64" )
         .ironName( "Iron" )
         .ironValuePer100( "12" )
         .ironValuePerServing( "4.5" )
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method

   @Ignore
   @Test public void variation278371514() {
      TescoFoodDescription description = new TescoFoodDescription( "Mcvities Milk Chocolate Hobnobs 262G" );
      description.productDetail().tpncs().add( "278371514" );
      
      systemUnderTest.parseNutritionFor( description );
     
      new NutritionAsserter()
         .per100Header( "Typical Values Per 100g" )
         .perServingHeader( "Per Biscuit (18.8g)" )
         .energyInKcalName( "Energy (kcal)" )
         .energyInKcalValuePer100( "491" )
         .energyInKcalValuePerServing( "92" )
         .energyInKjName( "Energy (kJ)" )
         .energyInKjValuePer100( "2056" )
         .energyInKjValuePerServing( "387" )
         .fatName( "Fat" )
         .fatValuePer100( "23.3" )
         .fatValuePerServing( "4.4" )
         .saturatesName( "of which saturates" )
         .saturatesValuePer100( "11.5" )
         .saturatesValuePerServing( "2.2" )
         .carbohydratesName( "Carbohydrate" )
         .carbohydratesValuePer100( "60.8" )
         .carbohydratesValuePerServing( "11.4" )
         .sugarsName( "of which sugars" )
         .sugarsValuePer100( "33.0" )
         .sugarsValuePerServing( "6.2" )
         .fibreName( "Fibre" )
         .fibreValuePer100( "5.1" )
         .fibreValuePerServing( "1.0" )
         .proteinName( "Protein" )
         .proteinValuePer100( "7.1" )
         .proteinValuePerServing( "1.3" )
         .saltName( "Salt" )
         .saltValuePer100( "0.7" )
         .saltValuePerServing( "0.1" )
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method

   @Ignore
   @Test public void variation274811214() {
      TescoFoodDescription description = new TescoFoodDescription( "Hovis Original 7 Seeds Bread 800G" );
      description.productDetail().tpncs().add( "274811214" );
      
      systemUnderTest.parseNutritionFor( description );
     
      new NutritionAsserter()
         .per100Header( "Typical Values Per 100g" )
         .perServingHeader( "Per Slice 44g" )
         .energyInKcalName( "Energy" )
         .energyInKcalValuePer100( "278" )
         .energyInKcalValuePerServing( "122" )
         .energyInKjName( "Energy" )
         .energyInKjValuePer100( "1171" )
         .energyInKjValuePerServing( "515" )
         .fatName( "Fat" )
         .fatValuePer100( "6.6" )
         .fatValuePerServing( "2.9" )
         .saturatesName( "of which saturates" )
         .saturatesValuePer100( "0.6" )
         .saturatesValuePerServing( "0.3" )
         .carbohydratesName( "Carbohydrates" )
         .carbohydratesValuePer100( "42.0" )
         .carbohydratesValuePerServing( "18.5" )
         .sugarsName( "of which sugars" )
         .sugarsValuePer100( "3.8" )
         .sugarsValuePerServing( "1.7" )
         .fibreName( "Fibre" )
         .fibreValuePer100( "5.3" )
         .fibreValuePerServing( "2.3" )
         .proteinName( "Protein" )
         .proteinValuePer100( "10.0" )
         .proteinValuePerServing( "4.4" )
         .saltName( "Salt" )
         .saltValuePer100( "0.88" )
         .saltValuePerServing( "0.39" )
         .omega3Name( "Omega 3 (ALA)" )
         .omega3ValuePer100( "2.18" )
         .omega3ValuePerServing( "0.96" )
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method
   
   @Ignore
   @Test public void variation296056855() {
      TescoFoodDescription description = new TescoFoodDescription( "Boswell Farms Beef Mince 1Kg 20% Fat" );
      description.productDetail().tpncs().add( "296056855" );
      
      systemUnderTest.parseNutritionFor( description );
     
      new NutritionAsserter()
         .per100Header( "100g raw as sold" )
         .energyInKcalName( "Energy" )
         .energyInKcalValuePer100( "252" )
         .energyInKjName( "Energy" )
         .energyInKjValuePer100( "1045" )
         .fatName( "Fat" )
         .fatValuePer100( "19.8" )
         .saturatesName( "Saturates" )
         .saturatesValuePer100( "9.8" )
         .carbohydratesName( "Carbohydrate" )
         .carbohydratesValuePer100( "0" )
         .sugarsName( "Sugars" )
         .sugarsValuePer100( "0" )
         .fibreName( "Fibre" )
         .fibreValuePer100( "0" )
         .proteinName( "Protein" )
         .proteinValuePer100( "18.4" )
         .saltName( "Salt" )
         .saltValuePer100( "0.4" )
         .assertThatValuesAreCorrect( description.productDetail().nutrition() );
   }//End Method
}//End Class
