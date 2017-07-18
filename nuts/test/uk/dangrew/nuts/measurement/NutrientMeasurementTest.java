package uk.dangrew.nuts.measurement;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NutrientMeasurementTest {

   @Test public void shouldReturnValueForGrams(){
      assertThat( NutrientMeasurement.Grams.toSiGrams( 145 ), is( 145.0 ) );
   }//End Method

}//End Class
