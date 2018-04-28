package uk.dangrew.nuts.goal.proportion;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;

public class ProportionConfigurationTest {

   private ProportionConfiguration systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProportionConfiguration();
   }//End Method

   @Test public void shouldProvideCarbsTarget() {
      assertThat( systemUnderTest.carbohydrateProportionType().get(), is( nullValue() ) );
      assertThat( systemUnderTest.carbohydrateTargetValue().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideFatsTarget() {
      assertThat( systemUnderTest.fatProportionType().get(), is( nullValue() ) );
      assertThat( systemUnderTest.fatTargetValue().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideProteinTarget() {
      assertThat( systemUnderTest.proteinProportionType().get(), is( nullValue() ) );
      assertThat( systemUnderTest.proteinTargetValue().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideProperties(){
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideProperty( ProportionConfiguration::fiberProportionType, ProportionType.Calorie )
         .shouldProvideDoubleProperty( ProportionConfiguration::fiberTargetValue, 0.0 );;
   }//End Method
   
}//End Class
