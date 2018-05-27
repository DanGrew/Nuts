package uk.dangrew.nuts.graphics.tutorial.architecture;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.graphics.tutorial.architecture.FriendlyMessageGenerator;

public class FriendlyMessageGeneratorTest {

   @Mock private Random random;
   private FriendlyMessageGenerator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new FriendlyMessageGenerator( random );
   }//End Method

   @Test public void shouldProvideResponses() {
      when( random.nextInt( systemUnderTest.friendlyConfirmationAnswerCount() ) )
         .thenReturn( 0, 1, 2, 3, 4, 5, 6, 7, 8 );
      
      for ( int i = 0; i < systemUnderTest.friendlyConfirmationAnswerCount(); i++ ) {
         assertThat( systemUnderTest.friendlyConfirmation(), is( systemUnderTest.friendlyConfirmationAnswer( i ) ) );
      }
   }//End Method

}//End Class
