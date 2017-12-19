package uk.dangrew.nuts.graphics.cycle;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.cycle.CycleType;
import uk.dangrew.nuts.goal.Goal;

public class CycleCreationResultTest {

   private Goal goal;
   private CycleCreationResult systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      goal = new Goal( "goal" );
      systemUnderTest = new CycleCreationResult( CycleType.Alternating, goal );
   }//End Method

   @Test public void shouldProvideType() {
      assertThat( systemUnderTest.type(), is( CycleType.Alternating ) );
   }//End Method
   
   @Test public void shouldProvideBaseGoal() {
      assertThat( systemUnderTest.baseGoal(), is( goal ) );
   }//End Method

}//End Class
