package uk.dangrew.nuts.configuration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class NutsSettingsTest {

   private NutsSettings systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new NutsSettings();
   }//End Method

   @Test public void shouldProvideProperties() {
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         assertThat( systemUnderTest.showingPropertyFor( unit ), is( notNullValue() ) );
         assertThat( systemUnderTest.showingPropertyFor( unit ).get(), is( notNullValue() ) );
      }
   }//End Method
   
   @Test public void shouldProvideDefaults(){
      assertThat( systemUnderTest.showingPropertyFor( NutritionalUnit.Calories ).get(), is( true ) );
      assertThat( systemUnderTest.showingPropertyFor( NutritionalUnit.Carbohydrate ).get(), is( true ) );
      assertThat( systemUnderTest.showingPropertyFor( NutritionalUnit.Fat ).get(), is( true ) );
      assertThat( systemUnderTest.showingPropertyFor( NutritionalUnit.Protein ).get(), is( true ) );
      assertThat( systemUnderTest.showingPropertyFor( NutritionalUnit.Fibre ).get(), is( true ) );
   }//End Method
   
   @Test public void shouldProvideRegistrations(){
      assertThat( systemUnderTest.registrations(), is( notNullValue() ) );
   }//End Method

}//End Class
