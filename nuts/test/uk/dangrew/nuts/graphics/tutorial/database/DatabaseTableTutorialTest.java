package uk.dangrew.nuts.graphics.tutorial.database;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorActionBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorMessageBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.TutorialGlass;

public class DatabaseTableTutorialTest {

   private Runnable[] instructions;
   @Mock private TutorMessageBuilder message;
   @Mock private TutorActionBuilder action;
   
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
      for ( Runnable i : instructions ) {
         systemUnderTest.tutorUser( message );
         verify( glass ).tutorUser( message, i );
      }
   }//End Method
   
   @Test public void shouldTutorAction() {
      for ( Runnable i : instructions ) {
         systemUnderTest.tutorAction( action );
         verify( glass ).tutorAction( action, i );
      }
   }//End Method
   
   @Test public void shouldRun() {
      systemUnderTest.run();
      verify( instructions[ 0 ] ).run();
   }//End Method

}//End Class
