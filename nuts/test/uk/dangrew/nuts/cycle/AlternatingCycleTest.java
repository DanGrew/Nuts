package uk.dangrew.nuts.cycle;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.goal.Goal;

public class AlternatingCycleTest {

   private Goal baseGoal;
   @Mock private AlternatingCycleGoalCalculator calculator;
   private AlternatingCycle systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      baseGoal = new Goal( "base" );
      systemUnderTest = new AlternatingCycle( calculator, baseGoal );
   }//End Method

   @Test public void shouldProvideCalculator(){
      assertThat( systemUnderTest.calculator(), is( calculator ) );
   }//End Method
   
   @Test public void shouldProvideBaseGoal() {
      assertThat( systemUnderTest.baseGoal(), is( baseGoal ) );
   }//End Method
   
   @Test public void shouldProvideNumberOfDeficits() {
      assertThat( systemUnderTest.numberOfDeficits().get(), is( AlternatingCycle.DEFAULT_NUMBER_OF_DEFICITS ) );
   }//End Method
   
   @Test public void shouldProvideInitialiseGoals(){
      verify( calculator ).initialiseGoals( systemUnderTest );
   }//End Method
   
}//End Class
