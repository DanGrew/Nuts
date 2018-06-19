package uk.dangrew.nuts.persistence.dayplan;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.json.JSONObject;
import org.junit.Test;

import uk.dangrew.jupa.file.protocol.WorkspaceJsonPersistingProtocol;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.persistence.fooditems.DatabaseIo;
import uk.dangrew.nuts.persistence.fooditems.FoodItemPersistence;
import uk.dangrew.nuts.persistence.goal.calorie.CalorieGoalPersistence;
import uk.dangrew.nuts.store.Database;

public class DayPlanPersistenceTest {
   
   private class ExpectedFoodPortion {
      private final String id;
      private final double portion;
      private final boolean consumed;
      
      public ExpectedFoodPortion( String id, double portion, boolean consumed ) {
         this.id = id;
         this.portion = portion;
         this.consumed = consumed;
      }//End Constructor
   }//End Class

   @Test public void shouldReadData() {
      Database database = new Database();
      new DatabaseIo( database )
               .withFoodItems( new WorkspaceJsonPersistingProtocol( "food-items.txt", getClass() ) )
               .withCalorieGoals( new WorkspaceJsonPersistingProtocol( "goals.txt", getClass() ) )
               .withDayPlans( new WorkspaceJsonPersistingProtocol( "dayplans.txt", getClass() ) )
               .read();
      database.resolver().resolve();
      
      DayPlan meal = database.dayPlans().objectList().get( 0 );
      assertMealProperties( 
               meal, "99987", "Meal1", 
               "single-goal-no-id-provided-unique",
               LocalDate.parse( "2017-08-19" ),
               2400, 2700, 300, false,
               new ExpectedFoodPortion( "12345", 100.0, false ),
               new ExpectedFoodPortion( "67890", 90.0, true ),
               new ExpectedFoodPortion( "3421", 50.0, false ) 
      );
      meal = database.dayPlans().objectList().get( 1 );
      assertMealProperties( 
               meal, "556676", "Meal2",
               "369b6651-633a-420b-b560-b1c1d3dfe1cb",
               LocalDate.parse( "2017-01-01" ),
               0, 0, 0, true,
               new ExpectedFoodPortion( "67890", 40.0, false ),
               new ExpectedFoodPortion( "3421", 90.0, false ),
               new ExpectedFoodPortion( "1324", 100.0, false )
      );
      meal = database.dayPlans().objectList().get( 2 );
      assertMealProperties( 
               meal, "88788861", "Meal4",
               "single-goal-no-id-provided-unique",
               LocalDate.parse( "2018-01-23" ),
               0, 0, 0, false,
               new ExpectedFoodPortion( "3421", 100.0, false )
      );
      
      //removed plan with no date
      assertThat( database.dayPlans().objectList(), hasSize( 3 ) );
   }//End Method
   
   @Test public void shouldWriteData(){
      Database database = new Database();
      
      FoodItem item1 = new FoodItem( "12345", "Food1" );
      item1.properties().setMacros( 45, 3.4, 98.1 );
      database.foodItems().store( item1 );
      
      FoodItem item2 = new FoodItem( "67890", "Food2" );
      item2.properties().setMacros( 2.11, 0.56, 123 );
      database.foodItems().store( item2 );
      
      FoodItem item3 = new FoodItem( "3421", "Food3" );
      item3.properties().setMacros( 2.3, 3.8, 8.6 );
      database.foodItems().store( item3 );
      
      FoodItem item4 = new FoodItem( "1324", "Food4" );
      item4.properties().setMacros( 0.1, 1.1, 0.3 );
      database.foodItems().store( item4 );
      
      CalorieGoal goal1 = new CalorieGoalImpl( "Goal 1" );
      database.calorieGoals().store( goal1 );
      
      CalorieGoal goal2 = new CalorieGoalImpl( "Goal 2" );
      database.calorieGoals().store( goal2 );
      
      DayPlan meal1 = new DayPlan( "99987", "Meal1" );
      meal1.portions().add( new FoodPortion( item1, 100.0 ) );
      meal1.portions().add( new FoodPortion( item2, 90.0 ) );
      meal1.portions().add( new FoodPortion( item3, 50.0 ) );
      meal1.goalAnalytics().goal().set( goal1 );
      meal1.setDate( LocalDate.now() );
      meal1.consumedCalories().set( 2400.0 );
      meal1.allowedCalories().set( 2700.0 );
      meal1.calorieBalance().set( 300.0 );
      database.dayPlans().store( meal1 );
      
      DayPlan meal2 = new DayPlan( "556676", "Meal2" );
      meal2.portions().add( new FoodPortion( item2, 40.0 ) );
      meal2.portions().add( new FoodPortion( item3, 90.0 ) );
      meal2.portions().add( new FoodPortion( item4, 100.0 ) );
      meal2.portions().add( new FoodPortion() );
      meal2.goalAnalytics().goal().set( goal2 );
      meal2.setDate( LocalDate.now() );
      meal2.consumed().add( meal2.portions().get( 1 ) );
      meal2.consumed().add( meal2.portions().get( 2 ) );
      meal2.consumed().add( meal2.portions().get( 3 ) );
      meal2.isBalanceReset().set( true );
      database.dayPlans().store( meal2 );
      
      DayPlan meal3 = new DayPlan( "8878886", "Meal3" );
      meal3.portions().add( new FoodPortion( item3, 100.0 ) );
      meal3.goalAnalytics().goal().set( goal1 );
      database.dayPlans().store( meal3 );
      
      DayPlan meal4 = new DayPlan( "565873", "Meal4" );
      meal4.goalAnalytics().goal().set( goal1 );
      meal4.setDate( LocalDate.now() );
      database.dayPlans().store( meal4 );
      
      FoodItemPersistence foodItemPersistence = new FoodItemPersistence( database );
      JSONObject foodItemJson = new JSONObject();
      foodItemPersistence.structure().build( foodItemJson );
      foodItemPersistence.writeHandles().parse( foodItemJson );
      
      DayPlanPersistence persistence = new DayPlanPersistence( database );
      JSONObject mealJson = new JSONObject();
      persistence.structure().build( mealJson );
      persistence.writeHandles().parse( mealJson );
      
      System.out.println( mealJson );
      
      CalorieGoalPersistence calorieGoalPersistence = new CalorieGoalPersistence( database.calorieGoals() );
      JSONObject goalJson = new JSONObject();
      calorieGoalPersistence.structure().build( goalJson );
      calorieGoalPersistence.writeHandles().parse( goalJson );
      
      
      database = new Database();
      foodItemPersistence = new FoodItemPersistence( database );
      
      assertThat( database.foodItems().objectList(), is( empty() ) );
      foodItemPersistence.readHandles().parse( foodItemJson );
      calorieGoalPersistence = new CalorieGoalPersistence( database.calorieGoals() );
      assertThat( database.calorieGoals().objectList(), is( empty() ) );
      calorieGoalPersistence.readHandles().parse( goalJson );
      persistence = new DayPlanPersistence( database );
      assertThat( database.dayPlans().objectList(), is( empty() ) );
      persistence.readHandles().parse( mealJson );

      database.resolver().resolve();
      
      assertThat( database.foodItems().objectList(), hasSize( 4 ) );
      assertThat( database.calorieGoals().objectList(), hasSize( 2 ) );
      assertThat( database.dayPlans().objectList(), hasSize( 3 ) );
      
      DayPlan meal = database.dayPlans().get( meal1.properties().id() );
      assertMealProperties( meal, meal1 );
      meal = database.dayPlans().get( meal2.properties().id() );
      assertMealProperties( meal, meal2 );
      meal = database.dayPlans().get( meal4.properties().id() );
      assertMealProperties( meal, meal4 );
      
      //Remove plans with missing date
      assertThat( database.dayPlans().objectList(), hasSize( 3 ) );
   }//End Method

   private void assertMealProperties(
            DayPlan meal, 
            String id, String name, String goalId, LocalDate date,
            double consumed, double allowed, double balance, boolean reset,
            ExpectedFoodPortion... portions
   ){
      assertThat( meal.properties().id(), is( id ) );
      assertThat( meal.properties().nameProperty().get(), is( name ) );
      assertThat( meal.goalAnalytics().goal().get().properties().id(), is( goalId ) );
      assertThat( meal.date(), is( date ) );
      assertThat( meal.portions(), hasSize( portions.length ) );
      assertThat( meal.consumedCalories().get(), is( consumed ) );
      assertThat( meal.allowedCalories().get(), is( allowed ) );
      assertThat( meal.calorieBalance().get(), is( balance ) );
      assertThat( meal.isBalanceReset().get(), is( reset ) );
      
      for ( int i = 0; i < portions.length; i++ ) {
         ExpectedFoodPortion expectedPortion = portions[ i ];
         FoodPortion portion = meal.portions().get( i );
         assertThat( expectedPortion.id, is( portion.food().get().properties().id() ) );
         assertThat( expectedPortion.portion, is( portion.portion().get() ) );
         assertThat( meal.consumed().contains( portion ), is( expectedPortion.consumed ) );
      }
   }//End Method
   
   private void assertMealProperties(
            DayPlan meal, DayPlan expected
   ){
      if ( expected.goalAnalytics().goal().get() != null ) {
         assertThat( meal.goalAnalytics().goal().get().properties().id(), is( expected.goalAnalytics().goal().get().properties().id() ) );
      } else {
         assertThat( meal.goalAnalytics().goal().get(), is( nullValue() ) );
      }
      assertThat( meal.date(), is( expected.date() ) );
      assertThat( meal.properties().id(), is( expected.properties().id() ) );
      assertThat( meal.properties().nameProperty().get(), is( expected.properties().nameProperty().get() ) );
      assertThat( meal.portions(), hasSize( expected.portions().size() ) );
      assertThat( meal.consumedCalories().get(), is( expected.consumedCalories().get() ) );
      assertThat( meal.allowedCalories().get(), is( expected.allowedCalories().get() ) );
      assertThat( meal.calorieBalance().get(), is( expected.calorieBalance().get() ) );
      assertThat( meal.isBalanceReset().get(), is( expected.isBalanceReset().get() ) );
      
      for ( int i = 0; i < expected.portions().size(); i++ ) {
         FoodPortion expectedPortion = expected.portions().get( i );
         FoodPortion portion = meal.portions().get( i );
         if ( expectedPortion.food().get() == null ) {
            assertThat( portion.food().get(), is( nullValue() ) );
         } else {
            assertThat( expectedPortion.food().get().properties().id(), is( portion.food().get().properties().id() ) );
         }
         assertThat( expectedPortion.portion().get(), is( portion.portion().get() ) );
         assertThat( expected.consumed().contains( expectedPortion ), is( meal.consumed().contains( portion ) ) );
      }
   }//End Method

}
