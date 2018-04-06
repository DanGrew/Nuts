package uk.dangrew.nuts.cycle;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.system.Properties;

public class CycleTest {

   private CalorieGoal baseGoal;
   private Properties properties;
   private Cycle systemUnderTest;
   
   @Before public void initialiseSystemUnderTest() {
      baseGoal = new CalorieGoalImpl( "goal" );
      properties = new Properties( "id", "name" );
      systemUnderTest = new Cycle( properties );
   }//End Method

   @Test public void shouldProvideProperties() {
      assertThat( systemUnderTest.properties(), is( properties ) );
   }//End Method
   
   @Test public void shouldProvideBaseGoal() {
      assertThat( systemUnderTest.baseGoal(), is( nullValue() ) );
      systemUnderTest.setBaseGoal( baseGoal );
      assertThat( systemUnderTest.baseGoal(), is( baseGoal ) );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleBaseGoalSet() {
      systemUnderTest.setBaseGoal( baseGoal );
      systemUnderTest.setBaseGoal( baseGoal );
   }//End Method
   
   @Test public void shouldNotDuplicate(){
      assertThat( systemUnderTest.duplicate( "anything" ), is( systemUnderTest ) );
   }//End Method
   
   @Test public void shouldProvideGoals(){
      assertThat( systemUnderTest.goals(), is( empty() ) );
   }//End Method
   
}//End Class
