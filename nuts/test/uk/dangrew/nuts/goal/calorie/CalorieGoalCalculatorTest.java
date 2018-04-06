package uk.dangrew.nuts.goal.calorie;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.dangrew.kode.TestCommon.precision;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalCalculator;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.goal.calorie.Gender;
import uk.dangrew.nuts.goal.calorie.MacroCalorieGoalCalculator;

public class CalorieGoalCalculatorTest {

   private CalorieGoal calorieGoal;
   private CalorieGoalCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new CalorieGoalCalculator();
      calorieGoal = new CalorieGoalImpl( 
               new FoodProperties( "Goal" ), 
               new FoodAnalytics(),
               systemUnderTest,
               new MacroCalorieGoalCalculator(),
               new MacroRatioCalculator()
      );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations(){
      systemUnderTest.associate( calorieGoal );
   }//End Method

   @Test public void shouldCalculateGoal() {
      assertThat( calorieGoal.properties().calories().get(), is( 0.0 ) );
      
      calorieGoal.tee().set( 1000.0 );
      assertThat( calorieGoal.properties().calories().get(), is( 1000.0 ) );
      
      calorieGoal.exerciseCalories().set( 500.0 );
      assertThat( calorieGoal.properties().calories().get(), is( 1500.0 ) );
      
      calorieGoal.calorieDeficit().set( 300.0 );
      assertThat( calorieGoal.properties().calories().get(), is( 1200.0 ) );
      
      calorieGoal.tee().set( 750.0 );
      assertThat( calorieGoal.properties().calories().get(), is( 950.0 ) );
   }//End Method
   
   @Test public void shouldCalculateMaleBmrWithZeros(){
      assertThat( calorieGoal.bmr().get(), is( 0.0 ) );
      calorieGoal.gender().set( Gender.Male );
      
      calorieGoal.age().set( 17.0 );
      assertThat( calorieGoal.bmr().get(), is( 299.0 ) );
      
      calorieGoal.weight().set( 150.0 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1360.406, precision() ) ) );
      
      calorieGoal.height().set( 1.9 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1865.806, precision() ) ) );
      
      calorieGoal.age().set( 0.0 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1865.806, precision() ) ) );
   }//End Method
   
   @Test public void shouldCalculateMaleBmr(){
      assertThat( calorieGoal.bmr().get(), is( 0.0 ) );
      calorieGoal.gender().set( Gender.Male );
      
      calorieGoal.age().set( 17.0 );
      calorieGoal.weight().set( 150.0 );
      calorieGoal.height().set( 1.9 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1865.806, precision() ) ) );
      
      calorieGoal.age().set( 18.0 );
      calorieGoal.weight().set( 160.0 );
      calorieGoal.height().set( 1.8 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1721.476, precision() ) ) );
      
      calorieGoal.age().set( 29.0 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1721.476, precision() ) ) );
      
      calorieGoal.age().set( 30.0 );
      calorieGoal.weight().set( 170.0 );
      calorieGoal.height().set( 1.75 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1688.812, precision() ) ) );
      
      calorieGoal.age().set( 59.0 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1688.812, precision() ) ) );

      calorieGoal.age().set( 60.0 );
      calorieGoal.weight().set( 140.0 );
      calorieGoal.height().set( 1.4 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1225.333, precision() ) ) );
   }//End Method
   
   @Test public void shouldCalculateFemaleBmrWithZeros(){
      assertThat( calorieGoal.bmr().get(), is( 0.0 ) );
      calorieGoal.gender().set( Gender.Female );
      
      calorieGoal.age().set( 17.0 );
      assertThat( calorieGoal.bmr().get(), is( 462.0 ) );
      
      calorieGoal.weight().set( 150.0 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1101.565, precision() ) ) );
      
      calorieGoal.height().set( 1.9 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1574.665, precision() ) ) );
      
      calorieGoal.age().set( 0.0 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1574.665, precision() ) ) );
   }//End Method
   
   @Test public void shouldCalculateFemaleBmr(){
      assertThat( calorieGoal.bmr().get(), is( 0.0 ) );
      calorieGoal.gender().set( Gender.Female );
      
      calorieGoal.age().set( 17.0 );
      calorieGoal.weight().set( 150.0 );
      calorieGoal.height().set( 1.9 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1574.665, precision() ) ) );
      
      calorieGoal.age().set( 18.0 );
      calorieGoal.weight().set( 160.0 );
      calorieGoal.height().set( 1.8 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1579.777, precision() ) ) );
      
      calorieGoal.age().set( 29.0 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1579.777, precision() ) ) );
      
      calorieGoal.age().set( 30.0 );
      calorieGoal.weight().set( 170.0 );
      calorieGoal.height().set( 1.75 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1497.665, precision() ) ) );
      
      calorieGoal.age().set( 59.0 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1497.665, precision() ) ) );

      calorieGoal.age().set( 60.0 );
      calorieGoal.weight().set( 140.0 );
      calorieGoal.height().set( 1.4 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1141.144, precision() ) ) );
   }//End Method
   
   @Test public void shoudlCalculateBmrWhenGenderSet(){
      calorieGoal.age().set( 17.0 );
      calorieGoal.weight().set( 150.0 );
      calorieGoal.height().set( 1.9 );
      
      assertThat( calorieGoal.bmr().get(), is( 0.0 ) );
      
      calorieGoal.gender().set( Gender.Female );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1574.665, precision() ) ) );
      
      calorieGoal.gender().set( Gender.Male );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1865.806, precision() ) ) );
      
      calorieGoal.gender().set( null );
      assertThat( calorieGoal.bmr().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shoudlCalculateBmrWhenAgeSet(){
      calorieGoal.gender().set( Gender.Female );
      calorieGoal.age().set( 17.0 );
      calorieGoal.weight().set( 150.0 );
      calorieGoal.height().set( 1.9 );
      
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1574.665, precision() ) ) );
      
      calorieGoal.age().set( 34.0 );
      assertThat( calorieGoal.bmr().get(), is( closeTo( 1498.757, precision() ) ) );
   }//End Method
   
   @Test public void shouldCalculateTeeWithZeros(){
      assertThat( calorieGoal.tee().get(), is( 0.0 ) );
      
      calorieGoal.bmr().set( 1600.0 );
      assertThat( calorieGoal.tee().get(), is( 2240.0 ) );
      
      calorieGoal.pal().set( 0.0 );
      assertThat( calorieGoal.tee().get(), is( 0.0 ) );
   }//End Method

}//End Class
