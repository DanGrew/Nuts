package uk.dangrew.nuts.goal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.MacroRatioCalculator;

public class MacroGoalCalculatorTest {

   private Goal goal;
   private MacroGoalCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new MacroGoalCalculator();
      goal = new Goal( 
               new FoodProperties( "Goal" ), 
               new FoodAnalytics(),
               new CalorieGoalCalculator(), 
               systemUnderTest ,
               new MacroRatioCalculator()
      );
      
      goal.mass().set( 200.0 );
      goal.maintenanceCalories().set( 1700.0 );
      goal.exerciseCalories().set( 1300.0 );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations() {
      systemUnderTest.associate( goal );
   }//End Method
   
   @Test public void shouldCalculateMacros(){
      assertThat( goal.properties().calories().get(), is( 3000.0 ) );
      assertThat( goal.properties().carbohydrates().inGrams(), is( 370.0 ) );
      assertThat( goal.properties().fats().inGrams(), is( 80.0 ) );
      assertThat( goal.properties().protein().inGrams(), is( 200.0 ) );
   }//End Method
   
   @Test public void shouldRespondToCalorieChange(){
      shouldCalculateMacros();
      goal.properties().calories().set( 2000.0 );
      
      assertThat( goal.properties().calories().get(), is( 2000.0 ) );
      assertThat( goal.properties().carbohydrates().inGrams(), is( 120.0 ) );
      assertThat( goal.properties().fats().inGrams(), is( 80.0 ) );
      assertThat( goal.properties().protein().inGrams(), is( 200.0 ) );
   }//End Method
   
   @Test public void shouldRespondToMassChange(){
      shouldCalculateMacros();
      goal.mass().set( 160.0 );
      
      assertThat( goal.properties().calories().get(), is( 3000.0 ) );
      assertThat( goal.properties().carbohydrates().inGrams(), is( 446.0 ) );
      assertThat( goal.properties().fats().inGrams(), is( 64.0 ) );
      assertThat( goal.properties().protein().inGrams(), is( 160.0 ) );
   }//End Method
   
   @Test public void shouldRespondToProteinPerPoundChange(){
      shouldCalculateMacros();
      goal.proteinPerPound().set( 0.5 );
      
      assertThat( goal.properties().calories().get(), is( 3000.0 ) );
      assertThat( goal.properties().carbohydrates().inGrams(), is( 470.0 ) );
      assertThat( goal.properties().fats().inGrams(), is( 80.0 ) );
      assertThat( goal.properties().protein().inGrams(), is( 100.0 ) );
   }//End Method
   
   @Test public void shouldRespondToFatPerPoundChange(){
      shouldCalculateMacros();
      goal.fatPerPound().set( 0.3 );
      
      assertThat( goal.properties().calories().get(), is( 3000.0 ) );
      assertThat( goal.properties().carbohydrates().inGrams(), is( 415.0 ) );
      assertThat( goal.properties().fats().inGrams(), is( 60.0 ) );
      assertThat( goal.properties().protein().inGrams(), is( 200.0 ) );
   }//End Method
   
}//End Class
