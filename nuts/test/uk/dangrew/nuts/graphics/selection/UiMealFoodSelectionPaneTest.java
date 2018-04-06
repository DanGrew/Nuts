package uk.dangrew.nuts.graphics.selection;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiMealFoodSelectionPaneTest {

   private Database database;
   @Mock private FoodSelectionWindowStageControls stageControls;
   private UiMealFoodSelectionPane systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      database = new Database();
      database.stockLists().createConcept( "Stock" );
      
      systemUnderTest = new UiMealFoodSelectionPane( database, stageControls );
   }//End Method

   @Test public void shouldSyncMealProperties() {
      Meal meal = new Meal( "Meal" );
      meal.portions().add( new FoodPortion( new FoodItem( "Food1" ), 100 ) );
      meal.portions().add( new FoodPortion( new FoodItem( "Food2" ), 45 ) );
      
      systemUnderTest.liveSelectionProperties().portions().add( new FoodPortion() );
      systemUnderTest.selectForMeal( meal );
      assertThat( systemUnderTest.liveSelectionProperties().properties().nameProperty().get(), is( meal.properties().nameProperty().get() ) );
      assertThat( systemUnderTest.liveSelectionProperties().portions(), contains( meal.portions().get( 0 ), meal.portions().get( 1 ) ) );
   }//End Method
   
   @Test public void shouldSyncTemplateProperties() {
      Template meal = new Template( "Template" );
      meal.portions().add( new FoodPortion( new FoodItem( "Food1" ), 100 ) );
      meal.portions().add( new FoodPortion( new FoodItem( "Food2" ), 45 ) );
      meal.goalAnalytics().goal().set( new CalorieGoalImpl( "Goal" ) );
      
      systemUnderTest.liveSelectionProperties().portions().add( new FoodPortion() );
      systemUnderTest.selectForTemplate( meal );
      assertThat( systemUnderTest.liveSelectionProperties().properties().nameProperty().get(), is( meal.properties().nameProperty().get() ) );
      assertThat( systemUnderTest.liveSelectionProperties().portions(), contains( meal.portions().get( 0 ), meal.portions().get( 1 ) ) );
      assertThat( systemUnderTest.liveSelectionProperties().goalAnalytics().goal().get(), is( meal.goalAnalytics().goal().get() ) );
   }//End Method
   
   @Test public void shouldClearBetweenSelections(){
      Template meal = new Template( "Template" );
      meal.portions().add( new FoodPortion( new FoodItem( "Food1" ), 100 ) );
      meal.portions().add( new FoodPortion( new FoodItem( "Food2" ), 45 ) );
      meal.goalAnalytics().goal().set( new CalorieGoalImpl( "Goal" ) );
      
      systemUnderTest.liveSelectionProperties().portions().add( new FoodPortion() );
      systemUnderTest.selectForTemplate( meal );
      assertThat( systemUnderTest.liveSelectionProperties().properties().nameProperty().get(), is( meal.properties().nameProperty().get() ) );
      assertThat( systemUnderTest.liveSelectionProperties().portions(), contains( meal.portions().get( 0 ), meal.portions().get( 1 ) ) );
      assertThat( systemUnderTest.liveSelectionProperties().goalAnalytics().goal().get(), is( meal.goalAnalytics().goal().get() ) );
      
      systemUnderTest.selectForMeal( new Meal( "empty" ) );
      
      assertThat( systemUnderTest.liveSelectionProperties().goalAnalytics().goal().get(), is( nullValue() ) );
      assertThat( systemUnderTest.liveSelectionProperties().portions(), is( empty() ) );
   }//End Method
}//End Class
