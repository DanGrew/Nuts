package uk.dangrew.nuts.food;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.nuts.measurement.NutrientMeasurement;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class FoodPropertiesTest {

   private static final String ID = "some id";
   private static final String NAME = "this is a name";
   
   @Spy private FoodAnalytics analytics;
   private FoodProperties systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new FoodProperties( ID, NAME, analytics );
   }//End Method
   
   @Test public void shouldCreateWithId(){
      systemUnderTest = new FoodProperties( "3487653", "skdjnvs." );
      assertThat( systemUnderTest.id(), is( "3487653" ) );
      assertThat( systemUnderTest.nameProperty().get(), is( "skdjnvs." ) );
   }//End Method
   
   @Test public void shouldAssociateWithAnalytics(){
      verify( analytics ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldProvideFoodId(){
      assertThat( systemUnderTest.id(), is( ID ) );
   }//End Method

   @Test public void shouldProvideName(){
      assertThat( systemUnderTest.nameProperty().get(), is( NAME ) );
   }//End Method
   
   @Test public void shouldProvideMacroNutrient() {
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ), is( notNullValue() ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).inGrams(), is( 0.0 ) );
      
      systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).setValue( NutrientMeasurement.Grams, 24.8 );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).inGrams(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldProvideCarbsIndividually(){
      assertThat( systemUnderTest.carbohydrates(), is( notNullValue() ) );
      assertThat( systemUnderTest.carbohydrates().inGrams(), is( 0.0 ) );
      systemUnderTest.carbohydrates().setValue( NutrientMeasurement.Grams, 24.8 );
      assertThat( systemUnderTest.carbohydrates().inGrams(), is( 24.8 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).inGrams(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldProvideFatIndividually(){
      assertThat( systemUnderTest.fats(), is( notNullValue() ) );
      assertThat( systemUnderTest.fats().inGrams(), is( 0.0 ) );
      systemUnderTest.fats().setValue( NutrientMeasurement.Grams, 24.8 );
      assertThat( systemUnderTest.fats().inGrams(), is( 24.8 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).inGrams(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldProvideProteinIndividually(){
      assertThat( systemUnderTest.protein(), is( notNullValue() ) );
      assertThat( systemUnderTest.protein().inGrams(), is( 0.0 ) );
      systemUnderTest.protein().setValue( NutrientMeasurement.Grams, 24.8 );
      assertThat( systemUnderTest.protein().inGrams(), is( 24.8 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).inGrams(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldProvideAnalytics() {
      assertThat( systemUnderTest.analytics(), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldSetMacrosTogether(){
      systemUnderTest.setMacros( 45, 67, 89 );
      assertThat( systemUnderTest.carbohydrates().inGrams(), is( 45.0 ) );
      assertThat( systemUnderTest.fats().inGrams(), is( 67.0 ) );
      assertThat( systemUnderTest.protein().inGrams(), is( 89.0 ) );
   }//End Method

}//End Class
