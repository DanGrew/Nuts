package uk.dangrew.nuts.cycle;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.Goal;

public class AlternatingCycleGoalCalculatorTest {

   private Goal baseGoal;
   private AlternatingCycle cycle;
   private AlternatingCycleGoalCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      baseGoal = new Goal( "Base" );
      cycle = new AlternatingCycle( baseGoal );
      systemUnderTest = new AlternatingCycleGoalCalculator();
   }//End Method

   @Test public void shouldInitialiseGoalsWithDefaults() {
      double deficit = 300;
      baseGoal.calorieDeficit().set( deficit );
      
      systemUnderTest.initialiseGoals( cycle );
      assertThat( cycle.goals(), hasSize( 2 ) );
      
      assertThatBaseGoalIsFollowedWithDeficit( cycle.goals().get( 0 ), 2*deficit );
      assertThatBaseGoalIsFollowedWithDeficit( cycle.goals().get( 1 ), 0 );
   }//End Method
   
   @Test public void shouldRespondToNumberOfDeficitsAndResetGoals(){
      double deficit = 400;
      baseGoal.calorieDeficit().set( deficit );
      cycle.numberOfDeficits().set( 3 );
      
      systemUnderTest.initialiseGoals( cycle );
      assertThatBaseGoalIsFollowedWithDeficit( cycle.goals().get( 0 ), 4*deficit );
      assertThatBaseGoalIsFollowedWithDeficit( cycle.goals().get( 1 ), -2*deficit );
      
      cycle.numberOfDeficits().set( 5 );
      assertThatBaseGoalIsFollowedWithDeficit( cycle.goals().get( 0 ), 6*deficit );
      assertThatBaseGoalIsFollowedWithDeficit( cycle.goals().get( 1 ), -4*deficit );
   }//End Method
   
   @Test public void shouldRespondToDeficitChangeInBaseGoal(){
      systemUnderTest.initialiseGoals( cycle );
      assertThat( cycle.goals(), hasSize( 2 ) );
      
      double deficit = 400;
      baseGoal.calorieDeficit().set( deficit );
      
      assertThatBaseGoalIsFollowedWithDeficit( cycle.goals().get( 0 ), 2*deficit );
      assertThatBaseGoalIsFollowedWithDeficit( cycle.goals().get( 1 ), 0 );
   }//End Method
   
   private void assertThatBaseGoalIsFollowedWithDeficit( Goal goal, double deficit ){
      assertThat( goal.age().get(), is( baseGoal.age().get() ) );
      assertThat( goal.bmr().get(), is( baseGoal.bmr().get() ) );
      assertThat( goal.exerciseCalories().get(), is( baseGoal.exerciseCalories().get() ) );
      assertThat( goal.fatPerPound().get(), is( baseGoal.fatPerPound().get() ) );
      assertThat( goal.gender().get(), is( baseGoal.gender().get() ) );
      assertThat( goal.height().get(), is( baseGoal.height().get() ) );
      assertThat( goal.pal().get(), is( baseGoal.pal().get() ) );
      assertThat( goal.proteinPerPound().get(), is( baseGoal.proteinPerPound().get() ) );
      assertThat( goal.tee().get(), is( baseGoal.tee().get() ) );
      assertThat( goal.weight().get(), is( baseGoal.weight().get() ) );
      
      assertThat( goal.calorieDeficit().get(), is( deficit ) );
   }//End Method
   
}//End Class
