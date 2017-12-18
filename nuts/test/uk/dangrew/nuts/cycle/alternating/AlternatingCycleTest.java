package uk.dangrew.nuts.cycle.alternating;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.system.Properties;

public class AlternatingCycleTest {

   @Mock private AlternatingCycleGoalCalculator calculator;
   private AlternatingCycle systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new AlternatingCycle( new Properties( "Cycle" ), calculator );
   }//End Method

   @Test public void shouldProvideNumberOfDeficits() {
      assertThat( systemUnderTest.numberOfDeficits().get(), is( AlternatingCycle.DEFAULT_NUMBER_OF_DEFICITS ) );
   }//End Method
   
   @Test public void shouldNotInitialiseGoals(){
      verify( calculator, never() ).initialiseGoals( systemUnderTest );
   }//End Method
   
   @Test public void shouldInitialiseGoalsWhenBaseGoalSet(){
      verify( calculator, never() ).initialiseGoals( systemUnderTest );
      systemUnderTest.setBaseGoal( new Goal( "goal" ) );
      verify( calculator ).initialiseGoals( systemUnderTest );
   }//End Method
   
   @Test public void shouldDuplicateWithoutBaseGoal(){
      AlternatingCycle duplicate = systemUnderTest.duplicate( "-copy" );
      assertThat( duplicate.baseGoal(), is( systemUnderTest.baseGoal() ) );
      assertThat( duplicate.numberOfDeficits().get(), is( systemUnderTest.numberOfDeficits().get() ) );
   }//End Method
   
   @Test public void shouldDuplicateWithBaseGoal(){
      systemUnderTest.setBaseGoal( new Goal( "name" ) );
      AlternatingCycle duplicate = systemUnderTest.duplicate( "-copy" );
      assertThat( duplicate.baseGoal(), is( systemUnderTest.baseGoal() ) );
      assertThat( duplicate.numberOfDeficits().get(), is( systemUnderTest.numberOfDeficits().get() ) );
   }//End Method
   
}//End Class
