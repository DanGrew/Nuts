package uk.dangrew.nuts.graphics.tutorial.architecture;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.database.UiDatabaseManagerPane;
import uk.dangrew.nuts.graphics.tutorial.database.DatabaseTutorial;
import uk.dangrew.nuts.store.Database;

public class TutorialGlassTest {

   @Mock private Function< Runnable, Thread > threadSupplier;
   @Mock private Thread mockedThread;
   @Mock private Runnable callback;
   @Captor private ArgumentCaptor< Runnable > runnableCaptor;
   
   private DatabaseTutorial tutorial;
   @Mock private UiDatabaseManagerPane pane;
   @Mock private TutorialProgressor progressor;
   @Mock private TutorPopOver popover;
   @Mock private TutorHighlight highlight;
   private TutorialGlass systemUnderTest;
   
   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      when( threadSupplier.apply( Mockito.any() ) ).thenReturn( mockedThread );
      systemUnderTest = new TutorialGlass( threadSupplier, popover, highlight, progressor, pane );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      Database database = new Database();
      database.stockLists().createConcept( "" );
      TestApplication.launch( () -> { 
            tutorial = new DatabaseTutorial( pane = new UiDatabaseManagerPane( database ) );
            return tutorial.window();
      } );
      
      PlatformImpl.runLater( () -> tutorial.runTutorial() );
      
      
      Thread.sleep( 99999999 );
   }//End Method
   
   @Test public void shouldAttachCallbackForMessage() {
      systemUnderTest.tutorUser( new TutorMessageBuilder(), callback );
      verify( progressor ).oneTimeOnlyCallback( callback );
   }//End Method
   
   @Test public void shouldAttachCallbackForAction() {
      systemUnderTest.tutorAction( new TutorActionBuilder(), callback );
      verify( threadSupplier ).apply( runnableCaptor.capture() );
      verify( mockedThread ).start();
      runnableCaptor.getValue().run();
      verify( callback ).run();
   }//End Method
   
   @Test public void shouldShowPopupForMessage(){
      TutorMessageBuilder builder = new TutorMessageBuilder();
      systemUnderTest.tutorUser( builder, callback );
      PlatformImpl.runAndWait( callback );
      verify( popover ).show( builder );
   }//End Method
   
   @Test public void shouldHighlightSubject(){
      TutorMessageBuilder builder = new TutorMessageBuilder().highlighting( pane );
      systemUnderTest.tutorUser( builder, callback );
      PlatformImpl.runAndWait( callback );
      verify( highlight ).focus( pane );
   }//End Method
   
   @Test public void shouldNotHighlightWhenNoSubject(){
      TutorMessageBuilder builder = new TutorMessageBuilder();
      systemUnderTest.tutorUser( builder, callback );
      PlatformImpl.runAndWait( callback );
      verify( highlight, never() ).focus( any() );
   }//End Method
   
   @Test public void shouldRunAllActions(){
      List< Runnable > actions = Arrays.asList( 
               mock( Runnable.class ),
               mock( Runnable.class ),
               mock( Runnable.class ),
               mock( Runnable.class )
      );
      
      TutorActionBuilder builder = new TutorActionBuilder();
      actions.forEach( builder::nonGraphicalAction );
      
      systemUnderTest.tutorAction( builder, callback );
      verify( threadSupplier ).apply( runnableCaptor.capture() );
      runnableCaptor.getValue().run();
      
      actions.forEach( a -> verify( a ).run() );
   }//End Method
   
   @Test public void shouldHideMessage(){
      systemUnderTest.removeTutorMessage();
      verify( popover ).friendly_hide();
   }//End Method
   
   @Test public void shouldHideHighlight(){
      systemUnderTest.removeTutorHighlight();
      verify( highlight ).hide();
   }//End Method
}//End Class
