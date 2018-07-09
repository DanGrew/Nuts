package uk.dangrew.nuts.apis.tesco.model.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.ModelVerifier;

public class CalculatedNutrientValueTest {

   private CalculatedNutrientValue systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new CalculatedNutrientValue();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideStringProperty( CalculatedNutrientValue::name )
         .shouldProvideStringProperty( CalculatedNutrientValue::valuePer100 )
         .shouldProvideStringProperty( CalculatedNutrientValue::valuePerServing );
   }//End Method
   
}//End Class


