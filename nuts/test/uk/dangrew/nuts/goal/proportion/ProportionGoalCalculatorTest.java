package uk.dangrew.nuts.goal.proportion;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;

public class ProportionGoalCalculatorTest {

   private ProportionGoal goal;
   private FoodProperties properties;
   private GoalAnalytics analytics;
   private ProportionGoalCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      properties = new FoodProperties( "Properties" );
      analytics = new GoalAnalytics();
      goal = new ProportionGoal( "Goal" );
      systemUnderTest = new ProportionGoalCalculator();
      systemUnderTest.associate( properties, analytics );
      analytics.proportionGoal().set( goal );
   }//End Method

   @Test public void shouldProvideNoProgressForCalories() {
      assertThat( analytics.caloriesRatio(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideKetoFlexibleCalories() {
      goal.configuration().carbohydrateProportionType().set( ProportionType.Weight );
      goal.configuration().carbohydrateTargetValue().set( 20.0 );
      
      goal.configuration().fatsProportionType().set( ProportionType.PercentageOfCalories );
      goal.configuration().fatsTargetValue().set( 75.0 );
      
      goal.configuration().proteinProportionType().set( ProportionType.PercentageOfCalories );
      goal.configuration().proteinTargetValue().set( 25.0 );
      
      assertRatios( 0, 0, 0 );
      
      properties.carbohydrates().set( 5.0 );
      assertRatios( 25, 0.0, 0.0 );
      properties.carbohydrates().set( 10.0 );
      assertRatios( 50, 0.0, 0.0 );
      properties.carbohydrates().set( 20.0 );
      assertRatios( 100, 0.0, 0.0 );
      properties.carbohydrates().set( 25.0 );
      assertRatios( 125, 0.0, 0.0 );
      
      properties.carbohydrates().set( 10.0 );
      properties.fats().set( 12.0 );
      //148kcal
      assertRatios( 50, 97.3, 0.0 );
      properties.protein().set( 4.0 );
      //164kcal
      assertRatios( 50, 87.8, 39.0 );
      properties.carbohydrates().set( 0.0 );
      //124kcal - 108:16 
      assertRatios( 0.0, 116.1, 51.6 );
      properties.fats().set( 5.33 );
      //216:16kcal
      assertRatios( 0.0, 100.0, 100.0 );
      
      properties.fats().set( 22.0 );
      properties.protein().set( 26.0 );
      assertRatios( 0.0, 87.4, 137.7 );
      
      properties.fats().set( 26.0 );
      assertRatios( 0.0, 92.3, 123.1 );
      
      properties.carbohydrates().set( 17.0 );
      //406kcal
      assertRatios( 85, 76.8, 102.5 );
   }//End Method
   
   @Test public void shouldCalculateProgressForCarbs(){
      goal.configuration().carbohydrateProportionType().set( ProportionType.Calorie );
      goal.configuration().carbohydrateTargetValue().set( 500.0 );
      
      assertRatios( 0, 0, 0 );
      properties.carbohydrates().set( 50.0 );
      assertRatios( 40.0, 0, 0 );
      properties.carbohydrates().set( 125.0 );
      assertRatios( 100.0, 0, 0 );
      properties.carbohydrates().set( 250.0 );
      assertRatios( 200.0, 0, 0 );
      
      goal.configuration().carbohydrateProportionType().set( ProportionType.Weight );
      goal.configuration().carbohydrateTargetValue().set( 500.0 );
      
      properties.carbohydrates().set( 50.0 );
      assertRatios( 10.0, 0, 0 );
      properties.carbohydrates().set( 500.0 );
      assertRatios( 100.0, 0, 0 );
      properties.carbohydrates().set( 2000.0 );
      assertRatios( 400.0, 0, 0 );
      
      goal.configuration().carbohydrateProportionType().set( ProportionType.PercentageOfCalories );
      goal.configuration().carbohydrateTargetValue().set( 20.0 );
      
      properties.setMacros( 10, 20, 45 );
      assertRatios( 50.0, 0, 0 );
      properties.setMacros( 20, 20, 35 );
      assertRatios( 100.0, 0, 0 );
      properties.setMacros( 40, 20, 15 );
      assertRatios( 200.0, 0, 0 );
      
      goal.configuration().carbohydrateProportionType().set( ProportionType.PercentageOfWeight );
      goal.configuration().carbohydrateTargetValue().set( 20.0 );
      
      properties.setMacros( 10, 35, 55 );
      assertRatios( 50.0, 0, 0 );
      properties.setMacros( 20, 25, 55 );
      assertRatios( 100.0, 0, 0 );
      properties.setMacros( 40, 25, 35 );
      assertRatios( 200.0, 0, 0 );
   }//End Method
   
   @Test public void shouldCalculateProgressForFats(){
      goal.configuration().fatsProportionType().set( ProportionType.Calorie );
      goal.configuration().fatsTargetValue().set( 450.0 );
      
      assertRatios( 0, 0, 0 );
      properties.fats().set( 25.0 );
      assertRatios( 0, 50.0, 0 );
      properties.fats().set( 50.0 );
      assertRatios( 0, 100.0, 0 );
      properties.fats().set( 100.0 );
      assertRatios( 0, 200.0, 0 );
      
      goal.configuration().fatsProportionType().set( ProportionType.Weight );
      goal.configuration().fatsTargetValue().set( 500.0 );
      
      properties.fats().set( 50.0 );
      assertRatios( 0, 10.0, 0 );
      properties.fats().set( 500.0 );
      assertRatios( 0, 100.0, 0 );
      properties.fats().set( 2000.0 );
      assertRatios( 0, 400.0, 0 );
      
      goal.configuration().fatsProportionType().set( ProportionType.PercentageOfCalories );
      goal.configuration().fatsTargetValue().set( 20.0 );
      
      properties.setMacros( 100, 10, 102.5 );
      assertRatios( 0, 50.0, 0 );
      properties.setMacros( 100, 20, 80 );
      assertRatios( 0, 100.0, 0 );
      properties.setMacros( 100, 40, 35 );
      assertRatios( 0, 200.0, 0 );
      
      goal.configuration().fatsProportionType().set( ProportionType.PercentageOfWeight );
      goal.configuration().fatsTargetValue().set( 20.0 );
      
      properties.setMacros( 35, 10, 55 );
      assertRatios( 0.0, 50.0, 0 );
      properties.setMacros( 25, 20, 55 );
      assertRatios( 0, 100.0, 0 );
      properties.setMacros( 25, 40, 35 );
      assertRatios( 0, 200.0, 0 );
   }//End Method
   
   @Test public void shouldCalculateProgressForProtein(){
      goal.configuration().proteinProportionType().set( ProportionType.Calorie );
      goal.configuration().proteinTargetValue().set( 500.0 );
      
      assertRatios( 0, 0, 0 );
      properties.protein().set( 50.0 );
      assertRatios( 0, 0, 40 );
      properties.protein().set( 125.0 );
      assertRatios( 0, 0, 100 );
      properties.protein().set( 250.0 );
      assertRatios( 0, 0, 200 );
      
      goal.configuration().proteinProportionType().set( ProportionType.Weight );
      goal.configuration().proteinTargetValue().set( 500.0 );
      
      properties.protein().set( 50.0 );
      assertRatios( 0, 0, 10 );
      properties.protein().set( 500.0 );
      assertRatios( 0, 0, 100 );
      properties.protein().set( 2000.0 );
      assertRatios( 0, 0, 400 );
      
      goal.configuration().proteinProportionType().set( ProportionType.PercentageOfCalories );
      goal.configuration().proteinTargetValue().set( 20.0 );
      
      properties.setMacros( 45, 20, 10 );
      assertRatios( 0, 0, 50 );
      properties.setMacros( 35, 20, 20 );
      assertRatios( 0, 0, 100 );
      properties.setMacros( 15, 20, 40 );
      assertRatios( 0, 0, 200 );
      
      goal.configuration().proteinProportionType().set( ProportionType.PercentageOfWeight );
      goal.configuration().proteinTargetValue().set( 20.0 );
      
      properties.setMacros( 55, 35, 10 );
      assertRatios( 0, 0, 50 );
      properties.setMacros( 55, 25, 20 );
      assertRatios( 0, 0, 100 );
      properties.setMacros( 35, 25, 40 );
      assertRatios( 0, 0, 200 );
   }//End Method
   
   @Test public void shouldDetachListenersWhenGoalChanges(){
      fail();
   }//End Method
   
   @Test public void shouldClearRatiosOnNullGoalSet(){
      fail();
   }//End Method
   
   @Test public void shouldRegiaterForChangesToGoalProperties(){
      fail();
   }//End Method
   
   private void assertRatios( double c, double f, double p ) {
      System.out.println( "C: " + analytics.carbohydratesRatio() + " F: " + analytics.fatsRatio() + " P: " + analytics.proteinRatio() );
      assertThat( analytics.carbohydratesRatio(), is( closeTo( c, 0.1 ) ) );
      assertThat( analytics.fatsRatio(), is( closeTo( f, 0.1 ) ) );
      assertThat( analytics.proteinRatio(), is( closeTo( p, 0.1 ) ) );
   }//End Method

}//End Class
