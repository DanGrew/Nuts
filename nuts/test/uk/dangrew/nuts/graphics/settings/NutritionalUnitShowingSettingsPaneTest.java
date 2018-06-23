package uk.dangrew.nuts.graphics.settings;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javafx.scene.control.CheckBox;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class NutritionalUnitShowingSettingsPaneTest {

   private NutsSettings settings;
   private NutritionalUnitShowingSettingsPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      settings = new NutsSettings();
      systemUnderTest = new NutritionalUnitShowingSettingsPane( settings );
   }//End Method

   @Test public void shouldProvideBoxesForProperties() {
      for ( NutritionalUnit unit : NutritionalUnit.values() ) {
         CheckBox box = systemUnderTest.checkBoxFor( settings.showingPropertyFor( unit ) );
         
         assertThat( box.isSelected(), is( settings.showingPropertyFor( unit ).get() ) );
         box.setSelected( false );
         assertThat( box.isSelected(), is( settings.showingPropertyFor( unit ).get() ) );
         settings.showingPropertyFor( unit ).set( true );
         assertThat( box.isSelected(), is( settings.showingPropertyFor( unit ).get() ) );
      }
   }//End Method

}//End Class
