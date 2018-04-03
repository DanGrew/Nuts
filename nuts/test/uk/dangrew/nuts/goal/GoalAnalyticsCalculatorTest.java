package uk.dangrew.nuts.goal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.nutrients.MacroNutrient;

public class GoalAnalyticsCalculatorTest {

   private Function< Goal, GoalCalculator > calculatorRetriever;
   @Mock private GoalCalculator calculator;
   
   private CalorieGoal calorieGoal;
   private ProportionGoal proportionGoal;
   private FoodProperties properties;
   private GoalAnalytics analytics;
   private GoalAnalyticsCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      calculatorRetriever = g -> calculator;
      
      properties = new FoodProperties( "Props" );
      analytics = new GoalAnalytics();
      calorieGoal = new CalorieGoalImpl( "Calorie" );
      proportionGoal = new ProportionGoal( "Proportion" );
      
      systemUnderTest = new GoalAnalyticsCalculator( calculatorRetriever );
      systemUnderTest.associate( properties, analytics );
   }//End Method
   
   private void assertThatCalculationTriggered( Goal goal, int times ){
      verify( calculator, Mockito.times( times ) ).calculate( properties, analytics, goal );
   }//End Method

   @Test public void shouldCalculateProportionsWhenFoodSet(){
      calorieGoal.properties().setMacros( 300, 60, 200 );
      
      properties.carbohydrates().set( 45.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 40.0 );
      
      analytics.goal().set( calorieGoal );
      assertThatCalculationTriggered( calorieGoal, 1 );
   }//End Method
   
   @Test public void shouldProvideMacroProportions() {
      analytics.goal().set( calorieGoal );
      
      assertThatCalculationTriggered( calorieGoal, 1 );
      calorieGoal.properties().setMacros( 300, 60, 200 );
      
      properties.carbohydrates().set( 30.0 );
      assertThatCalculationTriggered( calorieGoal, 5 );
      
      properties.fats().set( 30.0 );
      assertThatCalculationTriggered( calorieGoal, 6 );
      
      properties.protein().set( 75.0 );
      assertThatCalculationTriggered( calorieGoal, 7 );
      
      properties.fats().set( 180.0 );
      assertThatCalculationTriggered( calorieGoal, 8 );
      
      calorieGoal.properties().setMacros( 0.0, 0.0, 0.0 );
      assertThatCalculationTriggered( calorieGoal, 11 );
      
      properties.calories().set( 1800.0 );
      assertThatCalculationTriggered( calorieGoal, 12 );
   }//End Method
   
   @Test public void shouldRespondToGoalChanges() {
      analytics.goal().set( calorieGoal );
      
      properties.carbohydrates().set( 20.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 10.0 );
      
      calorieGoal.properties().carbohydrates().set( 50.0 );
      assertThatCalculationTriggered( calorieGoal, 5 );
      
      calorieGoal.properties().fats().set( 150.0 );
      assertThatCalculationTriggered( calorieGoal, 6 );
      
      calorieGoal.properties().protein().set( 80.0 );
      assertThatCalculationTriggered( calorieGoal, 7 );
      
      calorieGoal.properties().fats().set( 20.0 );
      assertThatCalculationTriggered( calorieGoal, 8 );
      
      calorieGoal.properties().calories().set( 20.0 );
      //calculator triggered
      assertThatCalculationTriggered( calorieGoal, 12 );
   }//End Method
   
   @Test public void shouldNotCalculateProportionsWhenNoGoal(){
      assertThatCalculationTriggered( calorieGoal, 0 );
      properties.carbohydrates().set( 100.0 );
      assertThatCalculationTriggered( calorieGoal, 0 );
   }//End Method
   
   @Test public void shouldResetProportionsWhenGoalRemoved(){
      calorieGoal.properties().setMacros( 300, 60, 200 );
      properties.carbohydrates().set( 45.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 40.0 );
      
      assertThatCalculationTriggered( calorieGoal, 0 );
      analytics.goal().set( calorieGoal );
      assertThatCalculationTriggered( calorieGoal, 1 );
      
      analytics.goal().set( null );
      assertThatCalculationTriggered( calorieGoal, 1 );
      assertMacroProportions( 0, 0, 0 );
   }//End Method
   
   @Test public void shouldNotRespondToPreviousGoal(){
      analytics.goal().set( calorieGoal );
      
      calorieGoal.properties().setMacros( 300, 60, 200 );
      properties.carbohydrates().set( 45.0 );
      properties.fats().set( 15.0 );
      properties.protein().set( 40.0 );
      assertThatCalculationTriggered( calorieGoal, 7 );
      
      analytics.goal().set( null );
      assertThatCalculationTriggered( calorieGoal, 7 );
      properties.setMacros( 23, 456, 980 );
      calorieGoal.properties().setMacros( 100, 23, 987 );
      assertThatCalculationTriggered( calorieGoal, 7 );
   }//End Method
   
   @Test public void shouldRespondToPropertyChanges() {
      analytics.goal().set( calorieGoal );
      calorieGoal.properties().setMacros( 100, 60, 200 );
      assertThatCalculationTriggered( calorieGoal, 4 );
   }//End Method
   
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

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociation(){
      systemUnderTest.associate( properties, analytics );
   }//End Method

}//End Class
