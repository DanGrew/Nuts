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

import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.nutrients.Nutrition;
import uk.dangrew.nuts.nutrients.NutritionalUnit;
import uk.dangrew.nuts.system.Properties;

public class GoalAnalyticsCalculatorTest {

   private Function< Goal, GoalCalculator > calculatorRetriever;
   @Mock private GoalCalculator calculator;
   
   private CalorieGoal calorieGoal;
   private ProportionGoal proportionGoal;
   private Properties properties;
   private Nutrition nutrition;
   private GoalAnalytics analytics;
   private GoalAnalyticsCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      calculatorRetriever = g -> calculator;
      
      properties = new Properties( "Props" );
      nutrition = new Nutrition();
      analytics = new GoalAnalytics();
      calorieGoal = new CalorieGoalImpl( "Calorie" );
      proportionGoal = new ProportionGoal( "Proportion" );
      
      systemUnderTest = new GoalAnalyticsCalculator( calculatorRetriever );
      systemUnderTest.associate( nutrition, analytics );
   }//End Method
   
   private void assertThatCalculationTriggered( Goal goal, int times ){
      verify( calculator, Mockito.times( times ) ).calculate( nutrition, analytics, goal );
   }//End Method

   @Test public void shouldCalculateProportionsWhenFoodSet(){
      calorieGoal.nutrition().setMacroNutrients( 300, 60, 200 );
      analytics.goal().set( calorieGoal );
      assertThatCalculationTriggered( calorieGoal, 1 );
   }//End Method
   
   @Test public void shouldProvideMacroProportions() {
      analytics.goal().set( calorieGoal );
      
      assertThatCalculationTriggered( calorieGoal, 1 );
      calorieGoal.nutrition().setMacroNutrients( 300, 60, 200 );
      
      nutrition.of( NutritionalUnit.Carbohydrate ).set( 30.0 );
      assertThatCalculationTriggered( calorieGoal, 5 );
      
      nutrition.of( NutritionalUnit.Fat ).set( 30.0 );
      assertThatCalculationTriggered( calorieGoal, 6 );
      
      nutrition.of( NutritionalUnit.Protein ).set( 75.0 );
      assertThatCalculationTriggered( calorieGoal, 7 );
      
      nutrition.of( NutritionalUnit.Fat ).set( 180.0 );
      assertThatCalculationTriggered( calorieGoal, 8 );
      
      calorieGoal.nutrition().setMacroNutrients( 0.0, 0.0, 0.0 );
      assertThatCalculationTriggered( calorieGoal, 11 );
      
      nutrition.of( NutritionalUnit.Calories ).set( 1800.0 );
      assertThatCalculationTriggered( calorieGoal, 12 );
      
      nutrition.of( NutritionalUnit.Fibre ).set( 0.08 );
      assertThatCalculationTriggered( calorieGoal, 13 );
   }//End Method
   
   @Test public void shouldRespondToGoalChanges() {
      analytics.goal().set( calorieGoal );
      
      nutrition.of( NutritionalUnit.Carbohydrate ).set( 20.0 );
      nutrition.of( NutritionalUnit.Fat ).set( 15.0 );
      nutrition.of( NutritionalUnit.Protein ).set( 10.0 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Carbohydrate ).set( 50.0 );
      assertThatCalculationTriggered( calorieGoal, 5 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Fat ).set( 150.0 );
      assertThatCalculationTriggered( calorieGoal, 6 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Protein ).set( 80.0 );
      assertThatCalculationTriggered( calorieGoal, 7 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Fat ).set( 20.0 );
      assertThatCalculationTriggered( calorieGoal, 8 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Fibre ).set( 0.01 );
      assertThatCalculationTriggered( calorieGoal, 9 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Calories ).set( 20.0 );
      //calculator triggered
      assertThatCalculationTriggered( calorieGoal, 13 );
   }//End Method
   
   @Test public void shouldNotCalculateProportionsWhenNoGoal(){
      assertThatCalculationTriggered( calorieGoal, 0 );
      nutrition.of( NutritionalUnit.Carbohydrate ).set( 100.0 );
      assertThatCalculationTriggered( calorieGoal, 0 );
   }//End Method
   
   @Test public void shouldResetProportionsWhenGoalRemoved(){
      calorieGoal.nutrition().setMacroNutrients( 300, 60, 200 );
      analytics.of( NutritionalUnit.Carbohydrate ).set( 45.0 );
      analytics.of( NutritionalUnit.Fat ).set( 15.0 );
      analytics.of( NutritionalUnit.Protein ).set( 40.0 );
      analytics.of( NutritionalUnit.Fibre ).set( 2.0 );
      
      assertThatCalculationTriggered( calorieGoal, 0 );
      analytics.goal().set( calorieGoal );
      assertThatCalculationTriggered( calorieGoal, 1 );
      
      analytics.goal().set( null );
      assertThatCalculationTriggered( calorieGoal, 1 );
      assertMacroProportions( 0, 0, 0, 0 );
   }//End Method
   
   @Test public void shouldNotRespondToPreviousGoal(){
      analytics.goal().set( calorieGoal );
      
      calorieGoal.nutrition().setMacroNutrients( 300, 60, 200 );
      nutrition.of( NutritionalUnit.Carbohydrate ).set( 45.0 );
      nutrition.of( NutritionalUnit.Fat ).set( 15.0 );
      nutrition.of( NutritionalUnit.Protein ).set( 40.0 );
      nutrition.of( NutritionalUnit.Fibre ).set( 2.0 );
      assertThatCalculationTriggered( calorieGoal, 8 );
      
      analytics.goal().set( null );
      assertThatCalculationTriggered( calorieGoal, 8 );
      nutrition.setMacroNutrients( 23, 456, 980 );
      nutrition.of( NutritionalUnit.Fibre ).set( 200.0 );
      calorieGoal.nutrition().setMacroNutrients( 100, 23, 987 );
      assertThatCalculationTriggered( calorieGoal, 8 );
   }//End Method
   
   @Test public void shouldRespondToPropertyChanges() {
      analytics.goal().set( calorieGoal );
      calorieGoal.nutrition().setMacroNutrients( 100, 60, 200 );
      calorieGoal.nutrition().of( NutritionalUnit.Fibre ).set( 45.4 );
      assertThatCalculationTriggered( calorieGoal, 5 );
   }//End Method
   
   private void assertMacroProportions( double c, double f, double p, double i ) {
      assertThat( analytics.of( NutritionalUnit.Carbohydrate ).get(), is( c ) );
      assertThat( analytics.of( NutritionalUnit.Fat ).get(), is( f ) );
      assertThat( analytics.of( NutritionalUnit.Protein ).get(), is( p ) );
      assertThat( analytics.of( NutritionalUnit.Fibre ).get(), is( i ) );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociation(){
      systemUnderTest.associate( nutrition, analytics );
   }//End Method
   
}//End Class
