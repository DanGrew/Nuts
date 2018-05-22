package uk.dangrew.nuts.apis.tesco.model.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;

public class CalculatedNutritionTest {

   private CalculatedNutrition systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new CalculatedNutrition();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideStringProperty( CalculatedNutrition::per100Header )
         .shouldProvideStringProperty( CalculatedNutrition::perServingHeader )
         .shouldProvideObject( CalculatedNutrition::energyInKj )
         .shouldProvideObject( CalculatedNutrition::energyInKcal )
         .shouldProvideObject( CalculatedNutrition::fat )
         .shouldProvideObject( CalculatedNutrition::saturates )
         .shouldProvideObject( CalculatedNutrition::carbohydrates )
         .shouldProvideObject( CalculatedNutrition::sugars )
         .shouldProvideObject( CalculatedNutrition::fibre )
         .shouldProvideObject( CalculatedNutrition::protein )
         .shouldProvideObject( CalculatedNutrition::salt )
         .shouldProvideObject( CalculatedNutrition::calcium )
         .shouldProvideObject( CalculatedNutrition::thiamin )
         .shouldProvideObject( CalculatedNutrition::riboflavin )
         .shouldProvideObject( CalculatedNutrition::niacin )
         .shouldProvideObject( CalculatedNutrition::folicAcid )
         .shouldProvideObject( CalculatedNutrition::iron )
         .shouldProvideObject( CalculatedNutrition::omega3 );
   }//End Method
   
   @Test public void shouldIdentifyWhenPopulatedHeadings(){
      assertThat( systemUnderTest.isPopulated(), is( false ) );
      systemUnderTest.per100Header().set( "anything" );
      assertThat( systemUnderTest.isPopulated(), is( true ) );
      systemUnderTest.per100Header().set( null );
      assertThat( systemUnderTest.isPopulated(), is( false ) );
      systemUnderTest.perServingHeader().set( "anything" );
      assertThat( systemUnderTest.isPopulated(), is( true ) );
      systemUnderTest.perServingHeader().set( null );
      assertThat( systemUnderTest.isPopulated(), is( false ) );
   }//End Method
   
}//End Class

