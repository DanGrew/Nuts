package uk.dangrew.nuts.configuration;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class NutsSettingsRegistrationsTest {

   @Mock private NutritionalUnitShowingListener showingListener;
   private NutsSettings settings;
   private NutsSettingsRegistrations systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = ( settings = new NutsSettings() ).registrations();
   }//End Method

   @Test public void shouldRegisterForShowingUnits() {
      systemUnderTest.registerForUnitShowing( showingListener );
      settings.showingPropertyFor( NutritionalUnit.Fibre ).set( false );
      verify( showingListener ).hide( NutritionalUnit.Fibre );
      
      settings.showingPropertyFor( NutritionalUnit.Fibre ).set( false );
      verify( showingListener ).hide( NutritionalUnit.Fibre );
      
      settings.showingPropertyFor( NutritionalUnit.Fibre ).set( true );
      verify( showingListener ).show( NutritionalUnit.Fibre );
   }//End Method

}//End Class
