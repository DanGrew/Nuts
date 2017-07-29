package uk.dangrew.nuts.graphics.tools.dryweight;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class DryWeightPropertiesTest {

   private DryWeightProperties systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new DryWeightProperties();
   }//End Method

   @Test public void shouldProvideDryWeight() {
      assertThat( systemUnderTest.portionDryWeight(), is( notNullValue() ) );
      assertThat( systemUnderTest.portionDryWeight().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideCookedWeight() {
      assertThat( systemUnderTest.portionCookedWeight(), is( notNullValue() ) );
      assertThat( systemUnderTest.portionCookedWeight().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideDryToCookedScaleFactor() {
      assertThat( systemUnderTest.dryToCookedScaleFactor(), is( notNullValue() ) );
      assertThat( systemUnderTest.dryToCookedScaleFactor().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideCookedToDryScaleFactor() {
      assertThat( systemUnderTest.cookedToDryScaleFactor(), is( notNullValue() ) );
      assertThat( systemUnderTest.cookedToDryScaleFactor().get(), is( 0.0 ) );
   }//End Method 
   
   @Test public void shouldProvideCookedWeightCalories() {
      assertThat( systemUnderTest.portionCalories(), is( notNullValue() ) );
      assertThat( systemUnderTest.portionCalories().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideDesiredCookedCalories() {
      assertThat( systemUnderTest.desiredCalories(), is( notNullValue() ) );
      assertThat( systemUnderTest.desiredCalories().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideDesiredCookedWeight() {
      assertThat( systemUnderTest.desiredCookedWeight(), is( notNullValue() ) );
      assertThat( systemUnderTest.desiredCookedWeight().get(), is( 0.0 ) );
   }//End Method

   @Test public void shouldProvideCalculatedDryWeightForDesiredCookedCalories() {
      assertThat( systemUnderTest.calculatedDryWeightForDesiredCalories(), is( notNullValue() ) );
      assertThat( systemUnderTest.calculatedDryWeightForDesiredCalories().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideCalculatedDryWeightForDesiredCookedWeight() {
      assertThat( systemUnderTest.calculatedDryWeightForDesiredCookedWeight(), is( notNullValue() ) );
      assertThat( systemUnderTest.calculatedDryWeightForDesiredCookedWeight().get(), is( 0.0 ) );
   }//End Method
   
}//End Class
