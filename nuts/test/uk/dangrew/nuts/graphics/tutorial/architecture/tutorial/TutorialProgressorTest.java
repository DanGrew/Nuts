package uk.dangrew.nuts.graphics.tutorial.architecture.tutorial;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialProgressor;

public class TutorialProgressorTest {

   @Mock private Runnable callback;
   private BooleanProperty showingProperty;
   private TutorialProgressor systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      showingProperty = new SimpleBooleanProperty();
      systemUnderTest = new TutorialProgressor( showingProperty );
   }//End Method

   @Test public void shouldTriggerCallbackOnce() {
      systemUnderTest.oneTimeOnlyCallback( callback );
      showingProperty.set( true );
      showingProperty.set( false );
      verify( callback ).run();
      
      showingProperty.set( true );
      showingProperty.set( false );
      showingProperty.set( true );
      showingProperty.set( false );
      verifyNoMoreInteractions( callback );
   }//End Method
   
   @Test public void shouldTriggerDifferenceCallbacks() {
      systemUnderTest.oneTimeOnlyCallback( callback );
      showingProperty.set( true );
      showingProperty.set( false );
      verify( callback ).run();
      
      Runnable alternative = mock( Runnable.class );
      systemUnderTest.oneTimeOnlyCallback( alternative );
      
      showingProperty.set( true );
      showingProperty.set( false );
      verify( alternative ).run();
      verifyNoMoreInteractions( callback );
   }//End Method
   
   @Test public void shouldNotTriggerCallback() {
      systemUnderTest.oneTimeOnlyCallback( callback );
      showingProperty.set( true );
      showingProperty.set( true );
      verify( callback, never() ).run();
      systemUnderTest.oneTimeOnlyCallback( null );
      
      showingProperty.set( false );
      verify( callback, never() ).run();
   }//End Method

}//End Class
