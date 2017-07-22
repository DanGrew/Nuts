package uk.dangrew.nuts.goal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.MacroRatioCalculator;

public class CalorieGoalCalculatorTest {

   private Goal goal;
   private CalorieGoalCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new CalorieGoalCalculator();
      goal = new Goal( 
               new FoodProperties( "Goal" ), 
               new FoodAnalytics(),
               systemUnderTest,
               new MacroGoalCalculator(),
               new MacroRatioCalculator()
      );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations(){
      systemUnderTest.associate( goal );
   }//End Method

   @Test public void shouldCalculateGoal() {
      assertThat( goal.properties().calories().get(), is( 0.0 ) );
      
      goal.maintenanceCalories().set( 1000.0 );
      assertThat( goal.properties().calories().get(), is( 1000.0 ) );
      
      goal.exerciseCalories().set( 500.0 );
      assertThat( goal.properties().calories().get(), is( 1500.0 ) );
      
      goal.calorieDeficit().set( 300.0 );
      assertThat( goal.properties().calories().get(), is( 1200.0 ) );
      
      goal.maintenanceCalories().set( 750.0 );
      assertThat( goal.properties().calories().get(), is( 950.0 ) );
   }//End Method

}//End Class
