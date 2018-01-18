package uk.dangrew.nuts.graphics.cycle;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.GoalImpl;

public class CycleCreationResultTest {

   private Goal baseGoal;
   private CycleCreationResult systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      baseGoal = new GoalImpl( "Base" );
      systemUnderTest = new CycleCreationResult( baseGoal );
   }//End Method

   @Test public void shouldProvideBaseGoal() {
      assertThat( systemUnderTest.baseGoal(), is( baseGoal ) );
   }//End Method

}//End Class
