package uk.dangrew.nuts.graphics.main;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.settings.tree.SettingsTreePane;
import uk.dangrew.kode.settings.window.SettingsWindowController;
import uk.dangrew.nuts.configuration.NutsSettings;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialWindow;
import uk.dangrew.nuts.persistence.fooditems.DatabaseIo;

public class CoreInterfaceOperationsTest {

   @Mock private SettingsWindowController windowController;
   @Mock private TutorialWindow tutorialWindow;
   @Mock private DatabaseIo databaseIo;
   private CoreInterfaceOperations systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      systemUnderTest = new CoreInterfaceOperations( tutorialWindow, databaseIo, windowController, new NutsSettings() );
   }//End Method

   @Test public void shouldReadAutomatically() {
      verify( databaseIo ).read();
   }//End Method
   
   @Test public void shouldSaveOnCommand() {
      systemUnderTest.save();
      verify( databaseIo ).write();
   }//End Method
   
   @Test public void shouldShowTutorial() {
      systemUnderTest.showTutorial();
      verify( tutorialWindow ).show();
   }//End Method
   
   @Test public void shouldAssociateSettingsWindow(){
      verify( windowController ).associateWithSettingsTreePane( any( SettingsTreePane.class ) );
   }//End Method

}//End Class
