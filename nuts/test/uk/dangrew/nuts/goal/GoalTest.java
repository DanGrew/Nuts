package uk.dangrew.nuts.goal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.MacroRatioCalculator;

public class GoalTest {
   
   private FoodProperties properties;
   private FoodAnalytics analytics;
   @Spy private CalorieGoalCalculator calorieCalculator;
   @Spy private MacroGoalCalculator macroCalculator;
   @Spy private MacroRatioCalculator ratioCalculator;
   private Goal systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      properties = new FoodProperties( "Goal" );
      analytics = new FoodAnalytics();
      systemUnderTest = new Goal( properties, analytics, calorieCalculator, macroCalculator, ratioCalculator );
   }//End Method

   @Test public void shouldProvideProperties(){
      assertThat( systemUnderTest.properties(), is( properties ) );
   }//End Method
   
   @Test public void shouldProvideAnalytics(){
      assertThat( systemUnderTest.analytics(), is( analytics ) );
   }//End Method
   
   @Test public void shouldAssociateRatioCalculator(){
      verify( ratioCalculator ).associate( properties, analytics );
   }//End Method

   @Test public void shouldProvideMass() {
      assertThat( systemUnderTest.mass(), is( notNullValue() ) );
      assertThat( systemUnderTest.mass().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideMaintenanceCalories() {
      assertThat( systemUnderTest.maintenanceCalories(), is( notNullValue() ) );
      assertThat( systemUnderTest.maintenanceCalories().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideExerciseCalories() {
      assertThat( systemUnderTest.exerciseCalories(), is( notNullValue() ) );
      assertThat( systemUnderTest.exerciseCalories().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideCalorieDeficit() {
      assertThat( systemUnderTest.calorieDeficit(), is( notNullValue() ) );
      assertThat( systemUnderTest.calorieDeficit().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideProteinPerPound(){
      assertThat( systemUnderTest.proteinPerPound(), is( notNullValue() ) );
      assertThat( systemUnderTest.proteinPerPound().get(), is( Goal.RECOMMENDED_PROTEIN_PER_POUND ) );
   }//End Method
   
   @Test public void shouldProvideFatPerPound(){
      assertThat( systemUnderTest.fatPerPound(), is( notNullValue() ) );
      assertThat( systemUnderTest.fatPerPound().get(), is( Goal.RECOMMENDED_FAT_PER_POUND ) );
   }//End Method
   
   @Test public void shouldProvideCalorieGoalCalculator(){
      verify( calorieCalculator ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldProvideMacroGoalCalculator(){
      verify( macroCalculator ).associate( systemUnderTest );
   }//End Method
   
}//End Method
