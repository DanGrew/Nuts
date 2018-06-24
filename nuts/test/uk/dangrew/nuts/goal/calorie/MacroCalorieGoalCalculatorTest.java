package uk.dangrew.nuts.goal.calorie;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class MacroCalorieGoalCalculatorTest {

   private CalorieGoal calorieGoal;
   private MacroCalorieGoalCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new MacroCalorieGoalCalculator();
      calorieGoal = new CalorieGoalImpl( 
               new FoodProperties( "Goal" ), 
               new FoodAnalytics(),
               new CalorieGoalCalculator(), 
               systemUnderTest ,
               new MacroRatioCalculator()
      );
      
      calorieGoal.weight().set( 200.0 );
      calorieGoal.tee().set( 1700.0 );
      calorieGoal.exerciseCalories().set( 1300.0 );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations() {
      systemUnderTest.associate( calorieGoal );
   }//End Method
   
   @Test public void shouldCalculateMacros(){
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Calories ).get(), is( 3000.0 ) );
      assertThat( calorieGoal.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 370.0 ) );
      assertThat( calorieGoal.properties().nutrition().of( NutritionalUnit.Fat ).get(), is( 80.0 ) );
      assertThat( calorieGoal.properties().protein().get(), is( 200.0 ) );
   }//End Method
   
   @Test public void shouldRespondToCalorieChange(){
      shouldCalculateMacros();
      calorieGoal.nutrition().of( NutritionalUnit.Calories ).set( 2000.0 );
      
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Calories ).get(), is( 2000.0 ) );
      assertThat( calorieGoal.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 120.0 ) );
      assertThat( calorieGoal.properties().nutrition().of( NutritionalUnit.Fat ).get(), is( 80.0 ) );
      assertThat( calorieGoal.properties().protein().get(), is( 200.0 ) );
   }//End Method
   
   @Test public void shouldRespondToMassChange(){
      shouldCalculateMacros();
      calorieGoal.weight().set( 160.0 );
      
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Calories ).get(), is( 3000.0 ) );
      assertThat( calorieGoal.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 446.0 ) );
      assertThat( calorieGoal.properties().nutrition().of( NutritionalUnit.Fat ).get(), is( 64.0 ) );
      assertThat( calorieGoal.properties().protein().get(), is( 160.0 ) );
   }//End Method
   
   @Test public void shouldRespondToProteinPerPoundChange(){
      shouldCalculateMacros();
      calorieGoal.proteinPerPound().set( 0.5 );
      
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Calories ).get(), is( 3000.0 ) );
      assertThat( calorieGoal.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 470.0 ) );
      assertThat( calorieGoal.properties().nutrition().of( NutritionalUnit.Fat ).get(), is( 80.0 ) );
      assertThat( calorieGoal.properties().protein().get(), is( 100.0 ) );
   }//End Method
   
   @Test public void shouldRespondToFatPerPoundChange(){
      shouldCalculateMacros();
      calorieGoal.fatPerPound().set( 0.3 );
      
      assertThat( calorieGoal.nutrition().of( NutritionalUnit.Calories ).get(), is( 3000.0 ) );
      assertThat( calorieGoal.properties().nutrition().of( NutritionalUnit.Carbohydrate ).get(), is( 415.0 ) );
      assertThat( calorieGoal.properties().nutrition().of( NutritionalUnit.Fat ).get(), is( 60.0 ) );
      assertThat( calorieGoal.properties().protein().get(), is( 200.0 ) );
   }//End Method
   
}//End Class
