package uk.dangrew.nuts.goal;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.dangrew.kode.TestCommon.precision;

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
      
      goal.tee().set( 1000.0 );
      assertThat( goal.properties().calories().get(), is( 1000.0 ) );
      
      goal.exerciseCalories().set( 500.0 );
      assertThat( goal.properties().calories().get(), is( 1500.0 ) );
      
      goal.calorieDeficit().set( 300.0 );
      assertThat( goal.properties().calories().get(), is( 1200.0 ) );
      
      goal.tee().set( 750.0 );
      assertThat( goal.properties().calories().get(), is( 950.0 ) );
   }//End Method
   
   @Test public void shouldCalculateBmrWithZeros(){
      assertThat( goal.bmr().get(), is( 0.0 ) );
      
      goal.age().set( 17.0 );
      assertThat( goal.bmr().get(), is( 299.0 ) );
      
      goal.weight().set( 150.0 );
      assertThat( goal.bmr().get(), is( closeTo( 1360.406, precision() ) ) );
      
      goal.height().set( 1.9 );
      assertThat( goal.bmr().get(), is( closeTo( 1865.806, precision() ) ) );
      
      goal.age().set( 0.0 );
      assertThat( goal.bmr().get(), is( closeTo( 1865.806, precision() ) ) );
   }//End Method
   
   @Test public void shouldCalculateBmr(){
      assertThat( goal.bmr().get(), is( 0.0 ) );
      
      goal.age().set( 17.0 );
      goal.weight().set( 150.0 );
      goal.height().set( 1.9 );
      assertThat( goal.bmr().get(), is( closeTo( 1865.806, precision() ) ) );
      
      goal.age().set( 18.0 );
      goal.weight().set( 160.0 );
      goal.height().set( 1.8 );
      assertThat( goal.bmr().get(), is( closeTo( 1721.476, precision() ) ) );
      
      goal.age().set( 29.0 );
      assertThat( goal.bmr().get(), is( closeTo( 1721.476, precision() ) ) );
      
      goal.age().set( 30.0 );
      goal.weight().set( 170.0 );
      goal.height().set( 1.75 );
      assertThat( goal.bmr().get(), is( closeTo( 1962.812, precision() ) ) );
      
      goal.age().set( 59.0 );
      assertThat( goal.bmr().get(), is( closeTo( 1962.812, precision() ) ) );

      goal.age().set( 60.0 );
      goal.weight().set( 140.0 );
      goal.height().set( 1.4 );
      assertThat( goal.bmr().get(), is( closeTo( 1737.333, precision() ) ) );
   }//End Method
   
   @Test public void shouldCalculateTeeWithZeros(){
      assertThat( goal.tee().get(), is( 0.0 ) );
      
      goal.bmr().set( 1600.0 );
      assertThat( goal.tee().get(), is( 2240.0 ) );
      
      goal.pal().set( 0.0 );
      assertThat( goal.tee().get(), is( 0.0 ) );
   }//End Method

}//End Class
