package uk.dangrew.nuts.goal.proportion;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class ProportionGoalTest {

   private ProportionGoal systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProportionGoal( "sut" );
   }//End Method

   @Test public void test() {
      fail( "Not yet implemented" );
   }//End Method

}//End Class
