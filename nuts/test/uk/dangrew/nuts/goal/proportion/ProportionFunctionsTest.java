package uk.dangrew.nuts.goal.proportion;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.goal.proportion.ProportionFunctions.CalorieProportionFunction;
import uk.dangrew.nuts.goal.proportion.ProportionFunctions.PercentageOfCaloriesProportionFunction;
import uk.dangrew.nuts.goal.proportion.ProportionFunctions.PercentageOfWeightProportionFunction;
import uk.dangrew.nuts.goal.proportion.ProportionFunctions.WeightProportionFunction;

public class ProportionFunctionsTest {

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
   }//End Method

   @Test public void shouldCalculateWeightProgress() {
      WeightProportionFunction sut = new WeightProportionFunction();
      assertThat( sut.calculateProgress( 0, 0, 0, 50, 100 ), is( 50.0 ) );
      assertThat( sut.calculateProgress( 0, 0, 0, 25, 100 ), is( 25.0 ) );
      assertThat( sut.calculateProgress( 0, 0, 0, 100, 100 ), is( 100.0 ) );
      assertThat( sut.calculateProgress( 0, 0, 0, 120, 100 ), is( 120.0 ) );
      assertThat( sut.calculateProgress( 0, 0, 0, 18, 20 ), is( 90.0 ) );
   }//End Method

   @Test public void shouldCalculateCalorieProgress() {
      CalorieProportionFunction sut = new CalorieProportionFunction();
      assertThat( sut.calculateProgress( 0, 0, 50, 0, 100 ), is( 50.0 ) );
      assertThat( sut.calculateProgress( 0, 0, 25, 0,100 ), is( 25.0 ) );
      assertThat( sut.calculateProgress( 0, 0, 100, 0,100 ), is( 100.0 ) );
      assertThat( sut.calculateProgress( 0, 0, 120, 0,100 ), is( 120.0 ) );
      assertThat( sut.calculateProgress( 0, 0, 18, 0,20 ), is( 90.0 ) );
   }//End Method
   
   @Test public void shouldCalculatePercentageOfWeightProgress() {
      PercentageOfWeightProportionFunction sut = new PercentageOfWeightProportionFunction();
      assertThat( sut.calculateProgress( 0, 0, 0, 0, 20 ), is( 0.0 ) );
      assertThat( sut.calculateProgress( 0, 100, 0, 0, 20 ), is( 0.0 ) );
      assertThat( sut.calculateProgress( 0, 50, 0, 10, 20 ), is( 100.0 ) );
      assertThat( sut.calculateProgress( 0, 50, 0, 15, 20 ), is( 150.0 ) );
      assertThat( sut.calculateProgress( 0, 200, 0, 5, 25 ), is( 10.0 ) );
   }//End Method
   
   @Test public void shouldCalculatePercentageOfCaloriesProgress() {
      PercentageOfCaloriesProportionFunction sut = new PercentageOfCaloriesProportionFunction();
      assertThat( sut.calculateProgress( 0, 0, 0, 0, 20 ), is( 0.0 ) );
      assertThat( sut.calculateProgress( 100, 0, 0, 0, 20 ), is( 0.0 ) );
      assertThat( sut.calculateProgress( 50, 0, 10, 0, 20 ), is( 100.0 ) );
      assertThat( sut.calculateProgress( 50, 0, 15, 0, 20 ), is( 150.0 ) );
      assertThat( sut.calculateProgress( 200, 0, 5, 0, 25 ), is( 10.0 ) );
   }//End Method
}//End Class
