package uk.dangrew.nuts.graphics.cycle;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.goal.CalorieGoalImpl;

public class CycleCreationResultTest {

   private CalorieGoal baseGoal;
   private CycleCreationResult systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      baseGoal = new CalorieGoalImpl( "Base" );
      systemUnderTest = new CycleCreationResult( baseGoal );
   }//End Method

   @Test public void shouldProvideBaseGoal() {
      assertThat( systemUnderTest.baseGoal(), is( baseGoal ) );
   }//End Method

}//End Class
