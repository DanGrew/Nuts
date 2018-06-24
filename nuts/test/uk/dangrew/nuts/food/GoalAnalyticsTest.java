package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class GoalAnalyticsTest {

   private GoalAnalytics systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new GoalAnalytics();
   }//End Method

   @Test public void shouldProvideGoal() {
      assertThat( systemUnderTest.goal().get(), is( nullValue() ) );
   }//End Method
   
}//End Class
