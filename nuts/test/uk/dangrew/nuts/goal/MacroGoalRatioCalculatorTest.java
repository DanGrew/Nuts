package uk.dangrew.nuts.goal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class MacroGoalRatioCalculatorTest {

   private CalorieGoal calorieGoal;
   private Nutrition nutrition;
   private GoalAnalytics analytics;
   private MacroGoalRatioCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      nutrition = new Nutrition();
      analytics = new GoalAnalytics();
      calorieGoal = new CalorieGoalImpl( "Goal" );
      
      systemUnderTest = new MacroGoalRatioCalculator();
   }//End Method
   
   private void triggerCalculation(){
      systemUnderTest.calculate( nutrition, analytics, calorieGoal );
   }//End Method

   @Test public void shouldCalculateProportionsWhenFoodSet(){
      calorieGoal.nutrition().setMacroNutrients( 300, 60, 200 );
      
      nutrition.of( NutritionalUnit.Carbohydrate ).set( 45.0 );
      nutrition.of( NutritionalUnit.Fat ).set( 15.0 );
      nutrition.of( NutritionalUnit.Protein ).set( 40.0 );
      
      analytics.goal().set( calorieGoal );
      assertMacroProportions( 15, 25, 20 );
   }//End Method
   
   @Test public void shouldProvideMacroProportions() {
      analytics.goal().set( calorieGoal );
      
      assertMacroProportions( 0, 0, 0 );
      calorieGoal.nutrition().setMacroNutrients( 300, 60, 200 );
      
      nutrition.of( NutritionalUnit.Carbohydrate ).set( 30.0 );
      assertMacroProportions( 10, 0, 0 );
      
      nutrition.of( NutritionalUnit.Fat ).set( 30.0 );
      assertMacroProportions( 10, 50, 0 );
      
      nutrition.of( NutritionalUnit.Protein ).set( 75.0 );
      assertMacroProportions( 10, 50, 37.5 );
      
      nutrition.of( NutritionalUnit.Fat ).set( 180.0 );
      assertMacroProportions( 10, 300, 37.5 );
      
      calorieGoal.nutrition().setMacroNutrients( 0.0, 0.0, 0.0 );
      assertMacroProportions( 0, 0, 0 );
   }//End Method
   
   @Test public void shouldRespondToGoalChanges() {
      analytics.goal().set( calorieGoal );
      
      nutrition.of( NutritionalUnit.Carbohydrate ).set( 20.0 );
      nutrition.of( NutritionalUnit.Fat ).set( 15.0 );
      nutrition.of( NutritionalUnit.Protein ).set( 10.0 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Carbohydrate ).set( 50.0 );
      assertMacroProportions( 40, 0, 0 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Fat ).set( 150.0 );
      assertMacroProportions( 40, 10, 0 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Protein ).set( 80.0 );
      assertMacroProportions( 40, 10, 12.5 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Fat ).set( 20.0 );
      assertMacroProportions( 40, 75, 12.5 );
   }//End Method
   
   @Test public void shouldNotCalculateProportionsWhenNoGoal(){
      assertMacroProportions( 0, 0, 0 );
      nutrition.of( NutritionalUnit.Carbohydrate ).set( 100.0 );
      assertMacroProportions( 0, 0, 0 );
   }//End Method
   
   /**
    * Convenience method to assert that the {@link FoodAnalytics} reports the correct {@link MacroNutrient}s.
    * @param c the {@link MacroNutrient#Carbohydrates}.
    * @param f the {@link MacroNutrient#Fats}.
    * @param p the {@link MacroNutrient#Protein}.
    */
   private void assertMacroProportions( double c, double f, double p ) {
      triggerCalculation();
      
      assertThat( analytics.of( NutritionalUnit.Carbohydrate ).get(), is( c ) );
      assertThat( analytics.of( NutritionalUnit.Fat ).get(), is( f ) );
      assertThat( analytics.of( NutritionalUnit.Protein ).get(), is( p ) );
   }//End Method
   
   @Test public void shouldCalculateCalorieRatio(){
      analytics.goal().set( calorieGoal );
      
      triggerCalculation();
      assertThat( analytics.of( NutritionalUnit.Calories ).get(), is( 0.0 ) );
      
      calorieGoal.nutrition().of( NutritionalUnit.Calories ).set( 600.0 );
      triggerCalculation();
      assertThat( analytics.of( NutritionalUnit.Calories ).get(), is( 0.0 ) );
      
      nutrition.of( NutritionalUnit.Calories ).set( 150.0 );
      triggerCalculation();
      assertThat( analytics.of( NutritionalUnit.Calories ).get(), is( 25.0 ) );
      
      calorieGoal.nutrition().of( NutritionalUnit.Calories ).set( 1000.0 );
      triggerCalculation();
      assertThat( analytics.of( NutritionalUnit.Calories ).get(), is( 15.0 ) );
   }//End Method
   
}//End Class
