package uk.dangrew.nuts.metric;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.measurement.NutrientMeasurement;
import uk.dangrew.nuts.measurement.NutrientValue;

public class NutrientValueTest {

   private NutrientValue systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new NutrientValue( 56 );
   }//End Method

   @Test public void shouldProvideValueInGrams() {
      assertThat( systemUnderTest.gramsProperty(), is( notNullValue() ) );
      assertThat( systemUnderTest.gramsProperty().get(), is( 56.0 ) );
      assertThat( systemUnderTest.inGrams(), is( 56.0 ) );
   }//End Method
   
   @Test public void shouldSetValue(){
      systemUnderTest.setValue( NutrientMeasurement.Grams, 45.98 );
      assertThat( systemUnderTest.gramsProperty().get(), is( 45.98 ) );
      assertThat( systemUnderTest.inGrams(), is( 45.98 ) );
   }//End Method
   
   @Test public void shouldSetGrams(){
      systemUnderTest.setGrams( 45.98 );
      assertThat( systemUnderTest.gramsProperty().get(), is( 45.98 ) );
      assertThat( systemUnderTest.inGrams(), is( 45.98 ) );
   }//End Method
   
}//End Class
