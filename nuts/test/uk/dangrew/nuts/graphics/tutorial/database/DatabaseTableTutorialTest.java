package uk.dangrew.nuts.graphics.tutorial.database;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorActionBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorMessageBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialGlass;

public class DatabaseTableTutorialTest {

   private Runnable[] instructions;
   private TutorMessageBuilder message;
   private TutorActionBuilder action;
   
   @Mock private DatabaseComponents components;
   @Mock private TutorialGlass glass;
   private DatabaseTableTutorial systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      instructions = new Runnable[]{ 
               mock( Runnable.class ), 
               mock( Runnable.class ), 
               mock( Runnable.class ) 
      };
      
      message = new TutorMessageBuilder();
      action = new TutorActionBuilder();
      
      systemUnderTest = new DatabaseTableTutorial( components, glass ) {
         @Override public Runnable[] defineInstructions() {
            return instructions;
         }//End Method
      };
   }//End Method

   @Test public void shouldProvideComponents() {
      assertThat( systemUnderTest.components(), is( components ) );
   }//End Method
   
   @Test public void shouldProvideGlass() {
      assertThat( systemUnderTest.glass(), is( glass ) );
   }//End Method
   
   @Test public void shouldTutorUser() {
      int invocations = 0;
      for ( Runnable i : instructions ) {
         systemUnderTest.tutorUser( message );
         verify( glass, times( ++invocations ) ).tutorUser( message );
         assertThat( message.callback().get(), is( i ) );
         assertThat( message.shouldHaveConfirmation(), is( true ) );
      }
   }//End Method
   
   @Test public void shouldTutorAction() {
      int invocations = 0;
      for ( Runnable i : instructions ) {
         systemUnderTest.tutorAction( action );
         verify( glass, times( ++invocations ) ).tutorAction( action );
         assertThat( action.callback().get(), is( i ) );
         assertThat( message.shouldHaveConfirmation(), is( false ) );
      }
   }//End Method
   
   @Test public void shouldRun() {
      systemUnderTest.run();
      verify( instructions[ 0 ] ).run();
   }//End Method

}//End Class
