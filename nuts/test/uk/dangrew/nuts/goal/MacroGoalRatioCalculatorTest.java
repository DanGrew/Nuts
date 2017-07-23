package uk.dangrew.nuts.goal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class MacroGoalRatioCalculatorTest {

   private Goal goal;
   private FoodProperties properties;
   private GoalAnalytics analytics;
   private MacroGoalRatioCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      properties = new FoodProperties( "Props" );
      analytics = new GoalAnalytics();
      goal = new Goal( "Goal" );
      
      systemUnderTest = new MacroGoalRatioCalculator();
      systemUnderTest.associate( properties, analytics );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowAssociateAgain(){
      systemUnderTest.associate( properties, analytics );
   }//End Method
   
   @Test public void shouldCalculateProportionsWhenFoodSet(){
      goal.properties().setMacros( 300, 60, 200 );
      
      properties.carbohydrates().set( 45.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 40.0 );
      
      analytics.goal().set( goal );
      assertMacroProportions( 15, 25, 20 );
   }//End Method
   
   @Test public void shouldProvideMacroProportions() {
      analytics.goal().set( goal );
      
      assertMacroProportions( 0, 0, 0 );
      goal.properties().setMacros( 300, 60, 200 );
      
      properties.carbohydrates().set( 30.0 );
      assertMacroProportions( 10, 0, 0 );
      
      properties.fats().set( 30.0 );
      assertMacroProportions( 10, 50, 0 );
      
      properties.protein().set( 75.0 );
      assertMacroProportions( 10, 50, 37.5 );
      
      properties.fats().set( 180.0 );
      assertMacroProportions( 10, 300, 37.5 );
      
      goal.properties().setMacros( 0.0, 0.0, 0.0 );
      assertMacroProportions( 0, 0, 0 );
   }//End Method
   
   @Test public void shouldRespondToGoalChanges() {
      analytics.goal().set( goal );
      
      properties.carbohydrates().set( 20.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 10.0 );
      
      goal.properties().carbohydrates().set( 50.0 );
      assertMacroProportions( 40, 0, 0 );
      
      goal.properties().fats().set( 150.0 );
      assertMacroProportions( 40, 10, 0 );
      
      goal.properties().protein().set( 80.0 );
      assertMacroProportions( 40, 10, 12.5 );
      
      goal.properties().fats().set( 20.0 );
      assertMacroProportions( 40, 75, 12.5 );
   }//End Method
   
   @Test public void shouldNotCalculateProportionsWhenNoGoal(){
      assertMacroProportions( 0, 0, 0 );
      properties.carbohydrates().set( 100.0 );
      assertMacroProportions( 0, 0, 0 );
   }//End Method
   
   @Test public void shouldResetProportionsWhenGoalRemoved(){
      goal.properties().setMacros( 300, 60, 200 );
      properties.carbohydrates().set( 45.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 40.0 );
      
      assertMacroProportions( 0, 0, 0 );
      analytics.goal().set( goal );
      assertMacroProportions( 15, 25, 20 );
      
      analytics.goal().set( null );
      assertMacroProportions( 0, 0, 0 );
   }//End Method
   
   @Test public void shouldNotRespondToPreviousGoal(){
      analytics.goal().set( goal );
      
      goal.properties().setMacros( 300, 60, 200 );
      properties.carbohydrates().set( 45.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 40.0 );
      assertMacroProportions( 15, 25, 20 );
      
      analytics.goal().set( null );
      assertMacroProportions( 0, 0, 0 );
      properties.setMacros( 23, 456, 980 );
      goal.properties().setMacros( 100, 23, 987 );
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

}//End Class
