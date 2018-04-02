package uk.dangrew.nuts.graphics.goal;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class GoalCalculationViewTest {

   private GoalCalculationView systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      systemUnderTest = new GoalCalculationView();
//      systemUnderTest.show(  new CalorieGoalImpl( "Goal" )  );
      systemUnderTest.show(  new ProportionGoal( "Goal" )  );
   }//End Method

   @Ignore
   @Test public void manual() throws InterruptedException {
      TestApplication.launch( () -> systemUnderTest );
      
      Thread.sleep( 10000000 );
   }//End Method
   
   @Test public void shouldNotHardCast(){
      fail();
   }
   
   @Test public void shouldShowEachType(){
      fail();
   }//End Method

}//End Class
