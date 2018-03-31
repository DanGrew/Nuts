package uk.dangrew.nuts.persistence.setup;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.goal.CalorieGoal;
import uk.dangrew.nuts.goal.CalorieGoalImpl;
import uk.dangrew.nuts.progress.SystemDateRange;
import uk.dangrew.nuts.store.Database;

public class DataSetupTest {

   private CalorieGoal calorieGoal;
   private Database database;
   private DataSetup systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      calorieGoal = new CalorieGoalImpl( "Goal" );
      database = new Database();
      systemUnderTest = new DataSetup( database );
   }//End Method

   @Test public void shouldIgnoreDefaultGoalIfSet() {
      database.templates().setDefaultGoal( calorieGoal );
      systemUnderTest.configureDefaultGoal();
      assertThat( database.templates().defaultGoal(), is( calorieGoal ) );
   }//End Method
   
   @Test public void shouldCreateDefaultGoalIfNonePresent() {
      systemUnderTest.configureDefaultGoal();
      assertThat( database.templates().defaultGoal(), is( notNullValue() ) );
      assertThat( database.goals().objectList().get( 0 ), is( database.templates().defaultGoal() ) );
   }//End Method
   
   @Test public void shouldSetDefaultGoalToFirstInListIfNotSet() {
      database.goals().store( calorieGoal );
      systemUnderTest.configureDefaultGoal();
      assertThat( database.templates().defaultGoal(), is( notNullValue() ) );
      assertThat( database.goals().objectList().get( 0 ), is( database.templates().defaultGoal() ) );
   }//End Method
   
   @Test public void shouldCreateDefaultShoppingList() {
      systemUnderTest.configureDefaultShoppingList();
      assertThat( database.shoppingLists().objectList(), hasSize( 1 ) );
   }//End Method
   
   @Test public void shouldIgnoreShoppingListIfPResent() {
      database.shoppingLists().createConcept( "Anything" );
      systemUnderTest.configureDefaultShoppingList();
      assertThat( database.shoppingLists().objectList(), hasSize( 1 ) );
   }//End Method
   
   @Test public void shouldCreateDefaultStockList() {
      systemUnderTest.configureDefaultStockListAndConnect();
      assertThat( database.stockLists().objectList(), hasSize( 1 ) );
   }//End Method
   
   @Test public void shouldIgnoreStockListIfPResent() {
      database.stockLists().createConcept( "Anything" );
      systemUnderTest.configureDefaultStockListAndConnect();
      assertThat( database.stockLists().objectList(), hasSize( 1 ) );
   }//End Method
   
   @Test public void shouldConnectStockToDatabase(){
      FoodItem item = database.foodItems().createConcept( "Food1" );
      database.stockLists().createConcept( "Anything" );
      systemUnderTest.configureDefaultStockListAndConnect();
      assertThat( database.stockLists().objectList().get( 0 ).portionFor( item ), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldCreateDayPlansForDateRange(){
      SystemDateRange range = new SystemDateRange();
      
      DayPlan existing1 = new DayPlan( range.get().get( 0 ) );
      DayPlan existing2 = new DayPlan( range.get().get( 1 ) );
      DayPlan existing3 = new DayPlan( range.get().get( 5 ) );
      
      database.dayPlans().store( existing1 );
      database.dayPlans().store( existing2 );
      database.dayPlans().store( existing3 );
      
      systemUnderTest.configureDefaultDayPlans();
      assertThat( database.dayPlans().objectList(), hasSize( range.get().size() ) );
      assertThat( database.dayPlans().get( existing1.properties().id() ), is( existing1 ) );
      assertThat( database.dayPlans().get( existing2.properties().id() ), is( existing2 ) );
      assertThat( database.dayPlans().get( existing3.properties().id() ), is( existing3 ) );
      
      for ( DayPlan dayPlan : database.dayPlans().objectList() ) {
         assertThat( range.get().contains( dayPlan.date() ), is( true ) );
      }
   }//End Method
   
   @Test public void shouldUseCalorieBalance(){
      systemUnderTest.configureDefaultDayPlans();
      DayPlan test = database.dayPlans().objectList().get( 0 );
      test.consumedCalories().set( 100.0 );
      assertThat( test.calorieBalance().get(), is( 100.0 ) );
   }//End Method

}//End Class
