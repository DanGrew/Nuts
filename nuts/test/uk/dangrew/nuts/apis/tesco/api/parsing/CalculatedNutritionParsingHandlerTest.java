package uk.dangrew.nuts.apis.tesco.api.parsing;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.apis.tesco.model.api.CalculatedNutrition;

public class CalculatedNutritionParsingHandlerTest {

   private static final String TEST_STRING = "sjfbvdhbfv";
   private static final String TEST_STRING_2 = "'s;idjfblvjhad";
   
   private CalculatedNutrition nutrition;
   private CalculatedNutritionParsingHandler systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      nutrition = new CalculatedNutrition();
      systemUnderTest = new CalculatedNutritionParsingHandler();
      systemUnderTest.setCurrentNutrition( nutrition );
   }//End Method

   @Test public void shouldSetHeaders() {
      assertThat( nutrition.per100Header().get(), is( nullValue() ) );
      assertThat( nutrition.perServingHeader().get(), is( nullValue() ) );
      
      systemUnderTest.setPer100Header( TEST_STRING );
      assertThat( nutrition.per100Header().get(), is( TEST_STRING ) );
      assertThat( nutrition.perServingHeader().get(), is( nullValue() ) );
      
      systemUnderTest.setPerServingHeader( TEST_STRING_2 );
      assertThat( nutrition.per100Header().get(), is( TEST_STRING ) );
      assertThat( nutrition.perServingHeader().get(), is( TEST_STRING_2 ) );
   }//End Method
   
   @Test public void shouldClearPropertiesBetweenObjects() {
      systemUnderTest.setName( "Fat (anything)" );
      systemUnderTest.setValuePer100( "45.6g" );
      systemUnderTest.setValuePerServing( "1.2g" );
      systemUnderTest.startedCalculatedNutrientsObject();
      
      systemUnderTest.finishedCalculatedNutrientsObject();
      assertThat( nutrition.fat().name().get(), is( nullValue() ) );
      assertThat( nutrition.fat().valuePer100().get(), is( nullValue() ) );
      assertThat( nutrition.fat().valuePerServing().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldExtractEnergyWhenCombined() {
      systemUnderTest.setName( "Energy (kj/kcal)" );
      systemUnderTest.setValuePer100( "45kcal/342kj" );
      systemUnderTest.setValuePerServing( "98kcal/556kj" );
      
      systemUnderTest.finishedCalculatedNutrientsObject();
      assertThat( nutrition.energyInKcal().name().get(), is( "Energy (kj/kcal)" ) );
      assertThat( nutrition.energyInKcal().valuePer100().get(), is( "45" ) );
      assertThat( nutrition.energyInKcal().valuePerServing().get(), is( "98" ) );
      
      assertThat( nutrition.energyInKj().name().get(), is( "Energy (kj/kcal)" ) );
      assertThat( nutrition.energyInKj().valuePer100().get(), is( "342" ) );
      assertThat( nutrition.energyInKj().valuePerServing().get(), is( "556" ) );
   }//End Method
   
   @Test public void shouldHandleApiEnergyOddity() {
      systemUnderTest.setName( "Energy (kcal)" );
      systemUnderTest.setValuePer100( "3700.00kj900." );
      systemUnderTest.setValuePerServing( "3701.00kj901." );
      
      systemUnderTest.finishedCalculatedNutrientsObject();
      assertThat( nutrition.energyInKcal().name().get(), is( "Energy (kcal)" ) );
      assertThat( nutrition.energyInKcal().valuePer100().get(), is( "900" ) );
      assertThat( nutrition.energyInKcal().valuePerServing().get(), is( "901" ) );
      
      systemUnderTest.setName( "Energy (kj)" );
      systemUnderTest.setValuePer100( "3700.00kj900." );
      systemUnderTest.setValuePerServing( "3701.00kj901." );
      systemUnderTest.finishedCalculatedNutrientsObject();
      assertThat( nutrition.energyInKj().name().get(), is( "Energy (kj)" ) );
      assertThat( nutrition.energyInKj().valuePer100().get(), is( "3700.00" ) );
      assertThat( nutrition.energyInKj().valuePerServing().get(), is( "3701.00" ) );
   }//End Method
   
   @Test public void shouldUseNutritionTypesToSetValues() {
      systemUnderTest.setName( "Fat (anything)" );
      systemUnderTest.setValuePer100( "45.6g" );
      systemUnderTest.setValuePerServing( "1.2g" );
      
      systemUnderTest.finishedCalculatedNutrientsObject();
      assertThat( nutrition.fat().name().get(), is( "Fat (anything)" ) );
      assertThat( nutrition.fat().valuePer100().get(), is( "45.6" ) );
      assertThat( nutrition.fat().valuePerServing().get(), is( "1.2" ) );
   }//End Method
   
   @Test public void shouldUseNutritionTypesToSetValuesForEnergy() {
      systemUnderTest.setName( "Energy (kcal)" );
      systemUnderTest.setValuePer100( "45.6g" );
      systemUnderTest.setValuePerServing( "1.2g" );
      
      systemUnderTest.finishedCalculatedNutrientsObject();
      assertThat( nutrition.energyInKcal().name().get(), is( "Energy (kcal)" ) );
      assertThat( nutrition.energyInKcal().valuePer100().get(), is( "45.6" ) );
      assertThat( nutrition.energyInKcal().valuePerServing().get(), is( "1.2" ) );
   }//End Method
   
   @Test public void shouldIgnoreInvalidNutritionType(){
      systemUnderTest.setName( "anything" );
      systemUnderTest.finishedCalculatedNutrientsObject();
   }//End Method

}//End Class
