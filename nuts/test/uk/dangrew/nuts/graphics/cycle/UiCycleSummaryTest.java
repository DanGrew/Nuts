package uk.dangrew.nuts.graphics.cycle;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.cycle.Cycle;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.goal.calorie.DerivedCalorieGoal;
import uk.dangrew.nuts.manual.data.DataLocation;

public class UiCycleSummaryTest {

   private double startingCalories;
   
   private Cycle cycle;
   private CalorieGoal baseGoal;
   private DerivedCalorieGoal goal1;
   private DerivedCalorieGoal goal2;
   private DerivedCalorieGoal goal3;
   
   private UiCycleGoalsTableController controller;
   private UiCycleSummary systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      
      cycle = new Cycle( "Cycle" );
      cycle.goals().add( goal1 = new DerivedCalorieGoal( "D1" ) );
      cycle.goals().add( goal2 = new DerivedCalorieGoal( "D2" ) );
      cycle.goals().add( goal3 = new DerivedCalorieGoal( "D3" ) );
      cycle.setBaseGoal( baseGoal = new CalorieGoalImpl( "Base" ) );
      DataLocation.configureExampleGoal( baseGoal );
      
      PlatformImpl.runAndWait( () -> controller = new UiCycleGoalsTableController() );
      controller.focus().set( cycle );
      systemUnderTest = new UiCycleSummary( controller );
      
      assertThat( startingCalories = baseGoal.properties().calories().get(), is( closeTo( 2579.085, TestCommon.precision() ) ) );
      assertThat( systemUnderTest.totalCaloriesLabel().getText(), is( "" + startingCalories * 3 + " kcal" ) );
   }//End Method

   @Ignore
   @Test public void shouldUpdateWhenBaseCaloriesChange() {
      baseGoal.properties().calories().set( 1000.0 );
      assertThat( systemUnderTest.totalCaloriesLabel().getText(), is( "3000 kcal" ) );
   }//End Method
   
   @Ignore
   @Test public void shouldUpdateWhenBaseDeficitChange() {
      baseGoal.calorieDeficit().set( 200.0 );
      assertThat( systemUnderTest.totalCaloriesLabel().getText(), is( "" + baseGoal.properties().calories().get() * 3 + " kcal" ) );
   }//End Method
   
   @Test public void shouldUpdateWhenOffsetChanges() {
      //todo
   }//End Method

}//End Class
