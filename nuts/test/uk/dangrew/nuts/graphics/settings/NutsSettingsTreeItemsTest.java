package uk.dangrew.nuts.graphics.settings;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.settings.item.SettingsItem;
import uk.dangrew.kode.settings.item.SettingsRootItem;
import uk.dangrew.kode.settings.tree.SettingsController;
import uk.dangrew.kode.settings.tree.SettingsTreePane;
import uk.dangrew.kode.settings.window.SettingsWindowController;
import uk.dangrew.nuts.configuration.NutsSettings;

public class NutsSettingsTreeItemsTest {

   private NutsSettingsTreeItems systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new NutsSettingsTreeItems( new NutsSettings() );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> new SettingsTreePane( systemUnderTest, mock( SettingsWindowController.class ) ) );
      
      Thread.sleep( 9999999 );
   }//End Method
   
   @Test public void shouldBuildStructure(){
      SettingsItem root = new SettingsRootItem();
      systemUnderTest.build( mock( SettingsController.class ), root );
      
      assertThat( root.treeItem().getChildren().get( 0 ).getValue(), is( instanceOf( NutritionalUnitShowingItem.class ) ) );
   }//End Method

}//End Class
