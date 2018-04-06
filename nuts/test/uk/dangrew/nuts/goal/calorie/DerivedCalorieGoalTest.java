package uk.dangrew.nuts.goal.calorie;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.GoalAnalytics;
import uk.dangrew.nuts.food.MacroRatioCalculator;
import uk.dangrew.nuts.goal.GoalTypes;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.goal.calorie.DerivedCalorieGoal;
import uk.dangrew.nuts.manual.data.DataLocation;

public class DerivedCalorieGoalTest {

   @Spy private MacroRatioCalculator marcoRatioCalculator;
   private FoodProperties properties;
   private FoodAnalytics analytics;
   private CalorieGoal baseGoal;
   private DerivedCalorieGoal systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      baseGoal = new CalorieGoalImpl( "Base" );
      properties = new FoodProperties( "Goal" );
      analytics = new GoalAnalytics();
      systemUnderTest = new DerivedCalorieGoal( properties, analytics, marcoRatioCalculator );
      systemUnderTest.setBaseGoal( baseGoal );
   }//End Method
   
   @Test public void shouldProvideGoalRatios(){
      verify( marcoRatioCalculator ).associate( properties, analytics );
   }//End Method

   @Test public void shouldProvideGoal() {
      assertThat( systemUnderTest.baseGoal(), is( baseGoal ) );
   }//End Method
   
   @Test public void shouldProvideDerivedProperties(){
      assertThat( systemUnderTest.age(), is( baseGoal.age() ) );
      assertThat( systemUnderTest.bmr(), is( baseGoal.bmr() ) );
      assertThat( systemUnderTest.exerciseCalories(), is( baseGoal.exerciseCalories() ) );
      assertThat( systemUnderTest.fatPerPound(), is( baseGoal.fatPerPound() ) );
      assertThat( systemUnderTest.gender(), is( baseGoal.gender() ) );
      assertThat( systemUnderTest.height(), is( baseGoal.height() ) );
      assertThat( systemUnderTest.pal(), is( baseGoal.pal() ) );
      assertThat( systemUnderTest.proteinPerPound(), is( baseGoal.proteinPerPound() ) );
      assertThat( systemUnderTest.tee(), is( baseGoal.tee() ) );
      assertThat( systemUnderTest.weight(), is( baseGoal.weight() ) );
   }//End Method
   
   @Test public void shouldProvideMacrosWhenCaloriesChange(){
      DataLocation.configureExampleGoal( baseGoal );
      
      assertThat( systemUnderTest.properties().carbohydrates().get(), is( closeTo( 292.633, TestCommon.precision() ) ) );
      assertThat( systemUnderTest.properties().fats().get(), is( closeTo( 68.95, TestCommon.precision() ) ) );
      assertThat( systemUnderTest.properties().protein().get(), is( closeTo( 197.0, TestCommon.precision() ) ) );
      assertThat( systemUnderTest.properties().calories().get(), is( closeTo( 2579.085, TestCommon.precision() ) ) );
      
      systemUnderTest.calorieOffset().set( 100.0 );
      
      assertThat( systemUnderTest.properties().carbohydrates().get(), is( closeTo( 317.633, TestCommon.precision() ) ) );
      assertThat( systemUnderTest.properties().fats().get(), is( closeTo( 68.95, TestCommon.precision() ) ) );
      assertThat( systemUnderTest.properties().protein().get(), is( closeTo( 197.0, TestCommon.precision() ) ) );
      assertThat( systemUnderTest.properties().calories().get(), is( closeTo( 2679.085, TestCommon.precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideRecalculatedCaloriesWhenOffsetChanges(){
      DataLocation.configureExampleGoal( baseGoal );
      
      assertThat( systemUnderTest.calorieDeficit(), is( not( baseGoal.calorieDeficit() ) ) );
      assertThat( systemUnderTest.calorieDeficit().get(), is( baseGoal.calorieDeficit().get() ) );
      assertThat( systemUnderTest.calorieOffset().get(), is( 0.0 ) );
      assertThat( systemUnderTest.properties().calories().get(), is( closeTo( 2579.085, TestCommon.precision() ) ) );
      
      systemUnderTest.calorieOffset().set( 100.0 );
      assertThat( systemUnderTest.properties().calories().get(), is( closeTo( 2679.085, TestCommon.precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideRecalculatedCaloriesWhenBaseDeficitChanges(){
      DataLocation.configureExampleGoal( baseGoal );
      assertThat( systemUnderTest.properties().calories().get(), is( closeTo( 2579.085, TestCommon.precision() ) ) );
      
      baseGoal.calorieDeficit().set( 200.0 );
      assertThat( systemUnderTest.properties().calories().get(), is( closeTo( 3079.085, TestCommon.precision() ) ) );
   }//End Method
   
   @Test public void shouldProvideRecalculatedCaloriesWhenDeficitChanges(){
      DataLocation.configureExampleGoal( baseGoal );
      assertThat( systemUnderTest.properties().calories().get(), is( closeTo( 2579.085, TestCommon.precision() ) ) );
      
      systemUnderTest.calorieDeficit().set( 200.0 );
      assertThat( systemUnderTest.properties().calories().get(), is( closeTo( 2579.085, TestCommon.precision() ) ) );
   }//End Method
   
   @Test public void shouldDuplicateWithBaseGoal(){
      DerivedCalorieGoal duplicate = systemUnderTest.duplicate( "anything" );
      assertThat( duplicate.properties().nameProperty().get(), is( systemUnderTest.properties().nameProperty().get() + "anything" ) );
      assertThat( duplicate.properties().id(), is( not( systemUnderTest.properties().id() ) ) );
      assertThat( duplicate.baseGoal(), is( systemUnderTest.baseGoal() ) );
      assertThat( duplicate.calorieOffset().get(), is( systemUnderTest.calorieOffset().get() ) );
      assertThat( duplicate.calorieDeficit().get(), is( systemUnderTest.calorieDeficit().get() ) );
   }//End Method
   
   @Test public void shouldDuplicateWithoutBaseGoal(){
      systemUnderTest = new DerivedCalorieGoal( "No Base" );
      DerivedCalorieGoal duplicate = systemUnderTest.duplicate( "anything" );
      assertThat( duplicate.properties().nameProperty().get(), is( systemUnderTest.properties().nameProperty().get() + "anything" ) );
      assertThat( duplicate.properties().id(), is( not( systemUnderTest.properties().id() ) ) );
      assertThat( duplicate.baseGoal(), is( nullValue() ) );
      assertThat( duplicate.calorieOffset().get(), is( systemUnderTest.calorieOffset().get() ) );
      assertThat( duplicate.calorieDeficit().get(), is( systemUnderTest.calorieDeficit().get() ) );
   }//End Method
   
   @Test public void shouldProvideType(){
      assertThat( systemUnderTest.type(), is( GoalTypes.Calorie ) );
   }//End Method

}//End Class
