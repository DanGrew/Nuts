package uk.dangrew.nuts.food;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.ModelVerifier;
import uk.dangrew.nuts.nutrients.MacroNutrient;

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
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ), is( notNullValue() ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 0.0 ) );
      
      systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).set( 24.8 );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldProvideCarbsIndividually(){
      assertThat( systemUnderTest.carbohydrates(), is( notNullValue() ) );
      assertThat( systemUnderTest.carbohydrates().get(), is( 0.0 ) );
      systemUnderTest.carbohydrates().set( 24.8 );
      assertThat( systemUnderTest.carbohydrates().get(), is( 24.8 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Carbohydrates ).get(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldProvideFatIndividually(){
      assertThat( systemUnderTest.fats(), is( notNullValue() ) );
      assertThat( systemUnderTest.fats().get(), is( 0.0 ) );
      systemUnderTest.fats().set( 24.8 );
      assertThat( systemUnderTest.fats().get(), is( 24.8 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Fats ).get(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldProvideProteinIndividually(){
      assertThat( systemUnderTest.protein(), is( notNullValue() ) );
      assertThat( systemUnderTest.protein().get(), is( 0.0 ) );
      systemUnderTest.protein().set( 24.8 );
      assertThat( systemUnderTest.protein().get(), is( 24.8 ) );
      assertThat( systemUnderTest.nutritionFor( MacroNutrient.Protein ).get(), is( 24.8 ) );
   }//End Method
   
   @Test public void shouldProvideCalories(){
      assertThat( systemUnderTest.calories(), is( notNullValue() ) );
      assertThat( systemUnderTest.calories().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldSetMacrosTogether(){
      systemUnderTest.setMacros( 45, 67, 89 );
      assertThat( systemUnderTest.carbohydrates().get(), is( 45.0 ) );
      assertThat( systemUnderTest.fats().get(), is( 67.0 ) );
      assertThat( systemUnderTest.protein().get(), is( 89.0 ) );
   }//End Method
   
   @Test public void shouldProvideProperties(){
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideDoubleProperty( FoodProperties::fiber, 0.0 );
   }//End Method

}//End Class
