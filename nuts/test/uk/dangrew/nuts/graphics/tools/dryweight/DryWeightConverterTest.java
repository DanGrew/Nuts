package uk.dangrew.nuts.graphics.tools.dryweight;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class DryWeightConverterTest {

   private DryWeightProperties properties;
   private DryWeightConverter systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      properties = new DryWeightProperties();
      systemUnderTest = new DryWeightConverter();
      systemUnderTest.associate( properties );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociation() {
      systemUnderTest.associate( properties );
   }//End Method
   
   @Test public void shouldCalculateCookedToDryScaleFactors(){
      assertThat( properties.cookedToDryScaleFactor().get(), is( 0.0 ) );
      
      properties.portionCookedWeight().set( 100.0 );
      assertThat( properties.cookedToDryScaleFactor().get(), is( 0.0 ) );
      
      properties.portionDryWeight().set( 50.0 );
      assertThat( properties.cookedToDryScaleFactor().get(), is( 0.5 ) );
      
      properties.portionCookedWeight().set( 200.0 );
      assertThat( properties.cookedToDryScaleFactor().get(), is( 0.25 ) );
      
      properties.portionCookedWeight().set( 0.0 );
      assertThat( properties.cookedToDryScaleFactor().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldCalculateDryToCookedScaleFactors(){
      assertThat( properties.dryToCookedScaleFactor().get(), is( 0.0 ) );
      
      properties.portionCookedWeight().set( 100.0 );
      assertThat( properties.dryToCookedScaleFactor().get(), is( 0.0 ) );
      
      properties.portionDryWeight().set( 50.0 );
      assertThat( properties.dryToCookedScaleFactor().get(), is( 2.0 ) );
      
      properties.portionCookedWeight().set( 200.0 );
      assertThat( properties.dryToCookedScaleFactor().get(), is( 4.0 ) );
      
      properties.portionCookedWeight().set( 0.0 );
      assertThat( properties.dryToCookedScaleFactor().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldCalculateDryWeightForDesiredCookedWeight(){
      assertThat( properties.calculatedDryWeightForDesiredCookedWeight().get(), is( 0.0 ) );
      
      properties.cookedToDryScaleFactor().set( 0.25 );
      assertThat( properties.calculatedDryWeightForDesiredCookedWeight().get(), is( 0.0 ) );
      
      properties.desiredCookedWeight().set( 200.0 );
      assertThat( properties.calculatedDryWeightForDesiredCookedWeight().get(), is( 50.0 ) );
      
      properties.cookedToDryScaleFactor().set( 0.75 );
      assertThat( properties.calculatedDryWeightForDesiredCookedWeight().get(), is( 150.0 ) );
   }//End Method
   
   @Test public void shouldCalculateDryWeightForDesiredCookedCalories(){
      assertThat( properties.calculatedDryWeightForDesiredCalories().get(), is( 0.0 ) );
      
      properties.portionDryWeight().set( 15.0 );
      assertThat( properties.calculatedDryWeightForDesiredCalories().get(), is( 0.0 ) );
      
      properties.portionCalories().set( 120.0 );
      assertThat( properties.calculatedDryWeightForDesiredCalories().get(), is( 0.0 ) );
      
      properties.desiredCalories().set( 200.0 );
      assertThat( properties.calculatedDryWeightForDesiredCalories().get(), is( 25.0 ) );
      
      properties.portionDryWeight().set( 18.0 );
      assertThat( properties.calculatedDryWeightForDesiredCalories().get(), is( 30.0 ) );
      
      properties.portionCalories().set( 90.0 );
      assertThat( properties.calculatedDryWeightForDesiredCalories().get(), is( 40.0 ) );
      
      properties.desiredCalories().set( 500.0 );
      assertThat( properties.calculatedDryWeightForDesiredCalories().get(), is( 100.0 ) );
      
      properties.portionDryWeight().set( 0.0 );
      assertThat( properties.calculatedDryWeightForDesiredCalories().get(), is( 0.0 ) );
      
      properties.portionCalories().set( 0.0 );
      assertThat( properties.calculatedDryWeightForDesiredCalories().get(), is( 0.0 ) );
      
      properties.desiredCalories().set( 0.0 );
      assertThat( properties.calculatedDryWeightForDesiredCalories().get(), is( 0.0 ) );
   }//End Method

}//End Class
