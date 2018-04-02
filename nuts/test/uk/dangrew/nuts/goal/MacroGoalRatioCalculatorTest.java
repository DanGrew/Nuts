package uk.dangrew.nuts.goal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class MacroGoalRatioCalculatorTest {

   private CalorieGoal calorieGoal;
   private FoodProperties properties;
   private GoalAnalytics analytics;
   private MacroGoalRatioCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      properties = new FoodProperties( "Props" );
      analytics = new GoalAnalytics();
      calorieGoal = new CalorieGoalImpl( "Goal" );
      
      systemUnderTest = new MacroGoalRatioCalculator();
      systemUnderTest.associate( properties, analytics );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowAssociateAgain(){
      systemUnderTest.associate( properties, analytics );
   }//End Method
   
   @Test public void shouldCalculateProportionsWhenFoodSet(){
      calorieGoal.properties().setMacros( 300, 60, 200 );
      
      properties.carbohydrates().set( 45.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 40.0 );
      
      analytics.goal().set( calorieGoal );
      assertMacroProportions( 15, 25, 20 );
   }//End Method
   
   @Test public void shouldProvideMacroProportions() {
      analytics.goal().set( calorieGoal );
      
      assertMacroProportions( 0, 0, 0 );
      calorieGoal.properties().setMacros( 300, 60, 200 );
      
      properties.carbohydrates().set( 30.0 );
      assertMacroProportions( 10, 0, 0 );
      
      properties.fats().set( 30.0 );
      assertMacroProportions( 10, 50, 0 );
      
      properties.protein().set( 75.0 );
      assertMacroProportions( 10, 50, 37.5 );
      
      properties.fats().set( 180.0 );
      assertMacroProportions( 10, 300, 37.5 );
      
      calorieGoal.properties().setMacros( 0.0, 0.0, 0.0 );
      assertMacroProportions( 0, 0, 0 );
   }//End Method
   
   @Test public void shouldRespondToGoalChanges() {
      analytics.goal().set( calorieGoal );
      
      properties.carbohydrates().set( 20.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 10.0 );
      
      calorieGoal.properties().carbohydrates().set( 50.0 );
      assertMacroProportions( 40, 0, 0 );
      
      calorieGoal.properties().fats().set( 150.0 );
      assertMacroProportions( 40, 10, 0 );
      
      calorieGoal.properties().protein().set( 80.0 );
      assertMacroProportions( 40, 10, 12.5 );
      
      calorieGoal.properties().fats().set( 20.0 );
      assertMacroProportions( 40, 75, 12.5 );
   }//End Method
   
   @Test public void shouldNotCalculateProportionsWhenNoGoal(){
      assertMacroProportions( 0, 0, 0 );
      properties.carbohydrates().set( 100.0 );
      assertMacroProportions( 0, 0, 0 );
   }//End Method
   
   @Test public void shouldResetProportionsWhenGoalRemoved(){
      calorieGoal.properties().setMacros( 300, 60, 200 );
      properties.carbohydrates().set( 45.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 40.0 );
      
      assertMacroProportions( 0, 0, 0 );
      analytics.goal().set( calorieGoal );
      assertMacroProportions( 15, 25, 20 );
      
      analytics.goal().set( null );
      assertMacroProportions( 0, 0, 0 );
   }//End Method
   
   @Test public void shouldNotRespondToPreviousGoal(){
      analytics.goal().set( calorieGoal );
      
      calorieGoal.properties().setMacros( 300, 60, 200 );
      properties.carbohydrates().set( 45.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 40.0 );
      assertMacroProportions( 15, 25, 20 );
      
      analytics.goal().set( null );
      assertMacroProportions( 0, 0, 0 );
      properties.setMacros( 23, 456, 980 );
      calorieGoal.properties().setMacros( 100, 23, 987 );
      assertMacroProportions( 0, 0, 0 );
   }//End Method
   
   /**
    * Convenience method to assert that the {@link FoodAnalytics} reports the correct {@link MacroNutrient}s.
    * @param c the {@link MacroNutrient#Carbohydrates}.
    * @param f the {@link MacroNutrient#Fats}.
    * @param p the {@link MacroNutrient#Protein}.
    */
   private void assertMacroProportions( double c, double f, double p ) {
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Carbohydrates ).get(), is( c ) );
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Fats ).get(), is( f ) );
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Protein ).get(), is( p ) );
      
      assertThat( analytics.carbohydratesRatioProperty().get(), is( c ) );
      assertThat( analytics.fatsRatioProperty().get(), is( f ) );
      assertThat( analytics.proteinRatioProperty().get(), is( p ) );
      
      assertThat( analytics.carbohydratesRatio(), is( c ) );
      assertThat( analytics.fatsRatio(), is( f ) );
      assertThat( analytics.proteinRatio(), is( p ) );  
   }//End Method
   
   @Test public void shouldCalculateCalorieRatio(){
      analytics.goal().set( calorieGoal );
      
      assertThat( analytics.caloriesRatio(), is( 0.0 ) );
      calorieGoal.properties().calories().set( 600.0 );
      assertThat( analytics.caloriesRatio(), is( 0.0 ) );
      properties.calories().set( 150.0 );
      assertThat( analytics.caloriesRatio(), is( 25.0 ) );
      calorieGoal.properties().calories().set( 1000.0 );
      assertThat( analytics.caloriesRatio(), is( 15.0 ) );
   }//End Method
   
   @Test public void shouldNotRespondToPreviousForCalorieRatio(){
      analytics.goal().set( calorieGoal );
      calorieGoal.properties().calories().set( 600.0 );
      properties.calories().set( 150.0 );
      assertThat( analytics.caloriesRatio(), is( 25.0 ) );
      
      analytics.goal().set( new CalorieGoalImpl( "anything" ) );
      assertThat( analytics.caloriesRatio(), is( 0.0 ) );
      
      calorieGoal.properties().calories().set( 1000.0 );
      assertThat( analytics.caloriesRatio(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldRespondToPropertyChanges() {
      analytics.goal().set( calorieGoal );
      calorieGoal.properties().setMacros( 100, 60, 200 );
      assertMacroProportions( 0, 0, 0 );
      
      properties.carbohydrates().set( 20.0 );
      assertThat( analytics.carbohydratesRatio(), is( 20.0 ) );
      
      properties.fats().set( 15.0 );
      assertThat( analytics.fatsRatio(), is( 25.0 ) );
      
      properties.protein().set( 10.0 );
      assertThat( analytics.proteinRatio(), is( 5.0 ) );
   }//End Method
   
   @Test public void shouldNotHardCast(){
      fail();
   }

}//End Class
