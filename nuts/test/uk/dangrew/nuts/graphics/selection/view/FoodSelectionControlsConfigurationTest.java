package uk.dangrew.nuts.graphics.selection.view;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.BuilderVerifier;
import uk.dangrew.nuts.graphics.selection.model.FoodFilters;

public class FoodSelectionControlsConfigurationTest {

   private FoodSelectionControlsConfiguration systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new FoodSelectionControlsConfiguration();
   }//End Method

   @Test public void shouldProvideDefaults() {
      assertThat( systemUnderTest.allowReverseSorting(), is( true ) );
      assertThat( systemUnderTest.filtersAllowed().containsAll( Arrays.asList( FoodFilters.values() ) ), is( true ) );
   }//End Method
   
   @Test public void shouldProvideProperties() {
      new BuilderVerifier<>()
         .buildBoolean( systemUnderTest::withoutReverseSorting, systemUnderTest::allowReverseSorting, false );
   }//End Method
   
   @Test public void shouldProvideFoodFilters(){
      assertThat( systemUnderTest.filtersAllowed().containsAll( Arrays.asList( FoodFilters.values() ) ), is( true ) );
      assertThat( systemUnderTest.withoutFilter( FoodFilters.Labels ), is( systemUnderTest ) );
      assertThat( systemUnderTest.filtersAllowed(), contains( FoodFilters.Default, FoodFilters.NameOnly, FoodFilters.Stock, FoodFilters.Selection ) );
      assertThat( systemUnderTest.withoutFilter( FoodFilters.Labels ), is( systemUnderTest ) );
      assertThat( systemUnderTest.withoutFilter( FoodFilters.NameOnly ), is( systemUnderTest ) );
      assertThat( systemUnderTest.filtersAllowed(), contains( FoodFilters.Default, FoodFilters.Stock, FoodFilters.Selection ) );
      
      assertThat( systemUnderTest.withAllFilters(), is( systemUnderTest ) );
      assertThat( systemUnderTest.filtersAllowed().containsAll( Arrays.asList( FoodFilters.values() ) ), is( true ) );
   }//End Method

}//End Class
