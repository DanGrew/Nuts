package uk.dangrew.nuts.goal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import uk.dangrew.nuts.food.FoodAnalytics;
import uk.dangrew.nuts.food.FoodProperties;
import uk.dangrew.nuts.food.MacroRatioCalculator;

public class CalorieGoalImplTest {
   
   private FoodProperties properties;
   private FoodAnalytics analytics;
   @Spy private CalorieGoalCalculator calorieCalculator;
   @Spy private MacroCalorieGoalCalculator macroCalculator;
   @Spy private MacroRatioCalculator ratioCalculator;
   private CalorieGoalImpl systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      properties = new FoodProperties( "Goal" );
      analytics = new FoodAnalytics();
      systemUnderTest = new CalorieGoalImpl( properties, analytics, calorieCalculator, macroCalculator, ratioCalculator );
   }//End Method

   @Test public void shouldProvideProperties(){
      assertThat( systemUnderTest.properties(), is( properties ) );
   }//End Method
   
   @Test public void shouldProvideAnalytics(){
      assertThat( systemUnderTest.foodAnalytics(), is( analytics ) );
   }//End Method
   
   @Test public void shouldAssociateRatioCalculator(){
      verify( ratioCalculator ).associate( properties, analytics );
   }//End Method

   @Test public void shouldProvideWeight() {
      assertThat( systemUnderTest.weight(), is( notNullValue() ) );
      assertThat( systemUnderTest.weight().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideMaintenanceCalories() {
      assertThat( systemUnderTest.tee(), is( notNullValue() ) );
      assertThat( systemUnderTest.tee().get(), is( 0.0 ) );
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
      assertThat( systemUnderTest.proteinPerPound().get(), is( CalorieGoalImpl.RECOMMENDED_PROTEIN_PER_POUND ) );
   }//End Method
   
   @Test public void shouldProvideFatPerPound(){
      assertThat( systemUnderTest.fatPerPound(), is( notNullValue() ) );
      assertThat( systemUnderTest.fatPerPound().get(), is( CalorieGoalImpl.RECOMMENDED_FAT_PER_POUND ) );
   }//End Method
   
   @Test public void shouldProvideCalorieGoalCalculator(){
      verify( calorieCalculator ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldProvideMacroGoalCalculator(){
      verify( macroCalculator ).associate( systemUnderTest );
   }//End Method
   
   @Test public void shouldProvideAge(){
      assertThat( systemUnderTest.age(), is( notNullValue() ) );
      assertThat( systemUnderTest.age().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideHeight(){
      assertThat( systemUnderTest.height(), is( notNullValue() ) );
      assertThat( systemUnderTest.height().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideBmr(){
      assertThat( systemUnderTest.bmr(), is( notNullValue() ) );
      assertThat( systemUnderTest.bmr().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvidePal(){
      assertThat( systemUnderTest.pal(), is( notNullValue() ) );
      assertThat( systemUnderTest.pal().get(), is( CalorieGoalImpl.RECOMMENDED_ACTIVITY_LEVEL ) );
   }//End Method
   
   @Test public void shouldProvideGender(){
      assertThat( systemUnderTest.pal(), is( notNullValue() ) );
      assertThat( systemUnderTest.gender().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldNotDuplicate(){
      assertThat( systemUnderTest.duplicate( "anythign" ), is( systemUnderTest ) );
   }//End Method
   
}//End Method
