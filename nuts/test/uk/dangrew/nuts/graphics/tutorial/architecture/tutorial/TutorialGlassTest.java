package uk.dangrew.nuts.graphics.tutorial.architecture.tutorial;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
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

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.database.UiDatabaseManagerPane;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorActionBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorHighlight;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorMessageBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorPopOver;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutorial.TutorialGlass;
import uk.dangrew.nuts.graphics.tutorial.database.components.DatabaseTutorial;
import uk.dangrew.nuts.store.Database;

public class TutorialGlassTest {

   @Mock private Function< Runnable, Thread > threadSupplier;
   @Mock private Thread mockedThread;
   @Captor private ArgumentCaptor< Runnable > runnableCaptor;
   
   private DatabaseTutorial tutorial;
   @Mock private UiDatabaseManagerPane pane;
   
   private Pane glass;
   @Mock private TutorPopOver popover;
   @Mock private TutorHighlight highlight;
   private TutorialGlass systemUnderTest;
   
   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      glass = new Pane();
      when( threadSupplier.apply( Mockito.any() ) ).thenReturn( mockedThread );
      systemUnderTest = new TutorialGlass( glass, threadSupplier, popover, highlight );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      Database database = new Database();
      database.stockLists().createConcept( "" );
      TestApplication.launch( () -> { 
            tutorial = new DatabaseTutorial();
            return tutorial.window();
      } );
      
      Thread.sleep( 99999999 );
   }//End Method
   
   @Test public void shouldShowPopupForMessage(){
      TutorMessageBuilder builder = new TutorMessageBuilder().withRespectTo( new BorderPane() );
      systemUnderTest.tutorUser( builder );
      PlatformImpl.runAndWait( () -> {} );
      verify( popover ).show( builder );
   }//End Method
   
   @Test public void shouldNotShowPopupWhenNoFocus(){
      TutorMessageBuilder builder = new TutorMessageBuilder();
      systemUnderTest.tutorUser( builder );
      PlatformImpl.runAndWait( () -> {} );
      verify( popover, never() ).show( builder );
   }//End Method
   
   @Test public void shouldHighlightSubject(){
      TutorMessageBuilder builder = new TutorMessageBuilder().highlighting( pane );
      systemUnderTest.tutorUser( builder );
      PlatformImpl.runAndWait( () -> {} );
      verify( highlight ).focus( pane, Color.RED );
   }//End Method
   
   @Test public void shouldNotHighlightWhenNoSubject(){
      TutorMessageBuilder builder = new TutorMessageBuilder();
      systemUnderTest.tutorUser( builder );
      PlatformImpl.runAndWait( () -> {} );
      verify( highlight, never() ).focus( any(), any() );
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
      
      systemUnderTest.tutorAction( builder );
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
   
   @Test public void shouldReplaceUnderlyingContent(){
      systemUnderTest.replaceUnderlyingContent( pane );
      assertThat( systemUnderTest.getChildren(), contains( pane, glass ) );
      assertThat( glass.getChildren(), contains( highlight ) );
   }//End Method
   
   @Test public void shouldClearMessageAndHighlighting(){
      systemUnderTest.clearMessageAndHighlight();
      verify( popover ).friendly_hide();
      verify( highlight ).hide();
   }//End Method
}//End Class
