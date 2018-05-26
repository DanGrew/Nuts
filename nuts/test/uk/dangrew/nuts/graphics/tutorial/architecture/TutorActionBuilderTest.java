package uk.dangrew.nuts.graphics.tutorial.architecture;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.model.BuilderVerifier;

public class TutorActionBuilderTest {

   private TutorActionBuilder systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new TutorActionBuilder();
   }//End Method

   @Test public void shouldProvideActionsAssociatedWithBuilt() {
      assertThat( 
               systemUnderTest
                  .nonGraphicalAction( () -> {} )
                  .graphicalNonBlockingAction( () -> {} )
                  .graphicalBlockingAction( () -> {} )
                  .pauseFor( 10 )
                  .actions(),
                hasSize( 4 )
       );
   }//End Method
   
   @Test public void shouldBuild(){
      new BuilderVerifier<>()
         .buildWithOptional( systemUnderTest::callingBackTo, systemUnderTest::callback, () -> {} );
   }//End Method

}
