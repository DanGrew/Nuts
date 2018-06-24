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
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.goal.proportion.ProportionGoal;
import uk.dangrew.nuts.nutrients.MacroNutrient;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

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
      calorieGoal.properties().nutrition().setMacroNutrients( 300, 60, 200 );
      analytics.goal().set( calorieGoal );
      assertThatCalculationTriggered( calorieGoal, 1 );
   }//End Method
   
   @Test public void shouldProvideMacroProportions() {
      analytics.goal().set( calorieGoal );
      
      assertThatCalculationTriggered( calorieGoal, 1 );
      calorieGoal.properties().nutrition().setMacroNutrients( 300, 60, 200 );
      
      properties.nutrition().of( NutritionalUnit.Carbohydrate ).set( 30.0 );
      assertThatCalculationTriggered( calorieGoal, 5 );
      
      properties.nutrition().of( NutritionalUnit.Fat ).set( 30.0 );
      assertThatCalculationTriggered( calorieGoal, 6 );
      
      properties.nutrition().of( NutritionalUnit.Protein ).set( 75.0 );
      assertThatCalculationTriggered( calorieGoal, 7 );
      
      properties.nutrition().of( NutritionalUnit.Fat ).set( 180.0 );
      assertThatCalculationTriggered( calorieGoal, 8 );
      
      calorieGoal.properties().nutrition().setMacroNutrients( 0.0, 0.0, 0.0 );
      assertThatCalculationTriggered( calorieGoal, 11 );
      
      properties.nutrition().of( NutritionalUnit.Calories ).set( 1800.0 );
      assertThatCalculationTriggered( calorieGoal, 12 );
      
      properties.fiber().set( 0.08 );
      assertThatCalculationTriggered( calorieGoal, 13 );
   }//End Method
   
   @Test public void shouldRespondToGoalChanges() {
      analytics.goal().set( calorieGoal );
      
      properties.nutrition().of( NutritionalUnit.Carbohydrate ).set( 20.0 );
      properties.nutrition().of( NutritionalUnit.Fat ).set( 15.0 );
      properties.nutrition().of( NutritionalUnit.Protein ).set( 10.0 );
      
      calorieGoal.properties().nutrition().of( NutritionalUnit.Carbohydrate ).set( 50.0 );
      assertThatCalculationTriggered( calorieGoal, 5 );
      
      calorieGoal.properties().nutrition().of( NutritionalUnit.Fat ).set( 150.0 );
      assertThatCalculationTriggered( calorieGoal, 6 );
      
      calorieGoal.properties().nutrition().of( NutritionalUnit.Protein ).set( 80.0 );
      assertThatCalculationTriggered( calorieGoal, 7 );
      
      calorieGoal.properties().nutrition().of( NutritionalUnit.Fat ).set( 20.0 );
      assertThatCalculationTriggered( calorieGoal, 8 );
      
      calorieGoal.properties().fiber().set( 0.01 );
      assertThatCalculationTriggered( calorieGoal, 9 );
      
      calorieGoal.nutrition().of( NutritionalUnit.Calories ).set( 20.0 );
      //calculator triggered
      assertThatCalculationTriggered( calorieGoal, 13 );
   }//End Method
   
   @Test public void shouldNotCalculateProportionsWhenNoGoal(){
      assertThatCalculationTriggered( calorieGoal, 0 );
      properties.nutrition().of( NutritionalUnit.Carbohydrate ).set( 100.0 );
      assertThatCalculationTriggered( calorieGoal, 0 );
   }//End Method
   
   @Test public void shouldResetProportionsWhenGoalRemoved(){
      calorieGoal.properties().nutrition().setMacroNutrients( 300, 60, 200 );
      analytics.carbohydratesRatioProperty().set( 45.0 );
      analytics.fatsRatioProperty().set( 15.0 );
      analytics.proteinRatioProperty().set( 40.0 );
      analytics.fiberRatioProperty().set( 2.0 );
      
      assertThatCalculationTriggered( calorieGoal, 0 );
      analytics.goal().set( calorieGoal );
      assertThatCalculationTriggered( calorieGoal, 1 );
      
      analytics.goal().set( null );
      assertThatCalculationTriggered( calorieGoal, 1 );
      assertMacroProportions( 0, 0, 0, 0 );
   }//End Method
   
   @Test public void shouldNotRespondToPreviousGoal(){
      analytics.goal().set( calorieGoal );
      
      calorieGoal.properties().nutrition().setMacroNutrients( 300, 60, 200 );
      properties.nutrition().of( NutritionalUnit.Carbohydrate ).set( 45.0 );
      properties.nutrition().of( NutritionalUnit.Fat ).set( 15.0 );
      properties.nutrition().of( NutritionalUnit.Protein ).set( 40.0 );
      properties.fiber().set( 2.0 );
      assertThatCalculationTriggered( calorieGoal, 8 );
      
      analytics.goal().set( null );
      assertThatCalculationTriggered( calorieGoal, 8 );
      properties.nutrition().setMacroNutrients( 23, 456, 980 );
      properties.fiber().set( 200.0 );
      calorieGoal.properties().nutrition().setMacroNutrients( 100, 23, 987 );
      assertThatCalculationTriggered( calorieGoal, 8 );
   }//End Method
   
   @Test public void shouldRespondToPropertyChanges() {
      analytics.goal().set( calorieGoal );
      calorieGoal.properties().nutrition().setMacroNutrients( 100, 60, 200 );
      calorieGoal.properties().fiber().set( 45.4 );
      assertThatCalculationTriggered( calorieGoal, 5 );
   }//End Method
   
   private void assertMacroProportions( double c, double f, double p, double i ) {
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Carbohydrates ).get(), is( c ) );
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Fats ).get(), is( f ) );
      assertThat( analytics.nutrientRatioFor( MacroNutrient.Protein ).get(), is( p ) );
      
      assertThat( analytics.carbohydratesRatioProperty().get(), is( c ) );
      assertThat( analytics.fatsRatioProperty().get(), is( f ) );
      assertThat( analytics.proteinRatioProperty().get(), is( p ) );
      assertThat( analytics.fiberRatioProperty().get(), is( i ) );
      
      assertThat( analytics.carbohydratesRatio(), is( c ) );
      assertThat( analytics.fatsRatio(), is( f ) );
      assertThat( analytics.proteinRatio(), is( p ) );  
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociation(){
      systemUnderTest.associate( properties, analytics );
   }//End Method
   
}//End Class
