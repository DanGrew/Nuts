package uk.dangrew.nuts.food;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.model.ModelVerifier;
import uk.dangrew.nuts.nutrients.MacroNutrient;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class FoodPropertiesTest {

   private static final String ID = "some id";
   private static final String NAME = "this is a name";
   
   private FoodProperties systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new FoodProperties( ID, NAME );
   }//End Method
   
   @Test public void shouldCreateWithId(){
      systemUnderTest = new FoodProperties( "3487653", "skdjnvs." );
      assertThat( systemUnderTest.id(), is( "3487653" ) );
      assertThat( systemUnderTest.nameProperty().get(), is( "skdjnvs." ) );
   }//End Method
   
   @Test public void shouldProvideFoodId(){
      assertThat( systemUnderTest.id(), is( ID ) );
   }//End Method

   @Test public void shouldProvideName(){
      assertThat( systemUnderTest.nameProperty().get(), is( NAME ) );
   }//End Method
   
   @Test public void shouldProvideMacroNutrient() {
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ), is( notNullValue() ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 0.0 ) );
      
      systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).set( 24.8 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldProvideCarbsIndividually(){
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ), is( notNullValue() ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 0.0 ) );
      systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).set( 24.8 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 24.8 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldProvideFatIndividually(){
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ), is( notNullValue() ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get(), is( 0.0 ) );
      systemUnderTest.nutrition().of( NutritionalUnit.Fat ).set( 24.8 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get(), is( 24.8 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldProvideProteinIndividually(){
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ), is( notNullValue() ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get(), is( 0.0 ) );
      systemUnderTest.nutrition().of( NutritionalUnit.Protein ).set( 24.8 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get(), is( 24.8 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldSetMacrosTogether(){
      systemUnderTest.nutrition().setMacroNutrients( 45, 67, 89 );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 45.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Fat ).get(), is( 67.0 ) );
      assertThat( systemUnderTest.nutrition().of( NutritionalUnit.Protein ).get(), is( 89.0 ) );
   }//End Method
   
}//End Class
