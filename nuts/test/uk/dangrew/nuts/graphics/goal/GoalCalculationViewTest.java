package uk.dangrew.nuts.graphics.goal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class GoalCalculationViewTest {

   private GoalCalculationView systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      systemUnderTest = new GoalCalculationView( new Goal( "Goal" ) );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 10000000 );
   }//End Method

}//End Class
