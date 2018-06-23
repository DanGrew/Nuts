package uk.dangrew.nuts.graphics.settings;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import uk.dangrew.kode.settings.tree.SettingsController;
import uk.dangrew.nuts.configuration.NutsSettings;

public class NutsSettingsTreeItemTypeTest {

   @Test public void shouldProvideSettingsItem() {
      NutsSettings settings = new NutsSettings();
      SettingsController controller = mock( SettingsController.class );
      
      for ( NutsSettingsTreeItemType type : NutsSettingsTreeItemType.values() ) {
         assertThat( type.construct( controller, settings ), is( notNullValue() ) );
      }
   }//End Method

}//End Class
