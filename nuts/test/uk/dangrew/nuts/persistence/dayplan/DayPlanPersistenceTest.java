package uk.dangrew.nuts.persistence.dayplan;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.Collections;

import org.json.JSONObject;
import org.junit.Test;

import javafx.util.Pair;
import uk.dangrew.kode.TestCommon;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.persistence.fooditems.FoodItemPersistence;
import uk.dangrew.nuts.persistence.goal.GoalPersistence;
import uk.dangrew.nuts.persistence.meals.MealPersistence;
import uk.dangrew.nuts.persistence.template.TemplatePersistence;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class DayPlanPersistenceTest {

   @Test public void shouldReadData() {
      Database database = new Database();
      FoodItemPersistence foodItemPersistence = new FoodItemPersistence( database );
      DayPlanPersistence persistence = new DayPlanPersistence( database, database.dayPlans() );
      GoalPersistence goalPersistence = new GoalPersistence( database.goals() );
      
      String value = TestCommon.readFileIntoString( getClass(), "food-items.txt" );
      JSONObject json = new JSONObject( value );
      foodItemPersistence.readHandles().parse( json );
      
      value = TestCommon.readFileIntoString( getClass(), "goals.txt" );
      json = new JSONObject( value );
      goalPersistence.readHandles().parse( json );
      
      value = TestCommon.readFileIntoString( getClass(), "dayplans.txt" );
      json = new JSONObject( value );
      persistence.readHandles().parse( json );
      
      DayPlan meal = database.dayPlans().objectList().get( 0 );
      assertMealProperties( 
               meal, "99987", "Meal1", 
               "single-goal-no-id-provided-unique",
               LocalDate.parse( "2017-08-19" ),
               new Pair<>( "12345", 100.0 ),
               new Pair<>( "67890", 90.0 ),
               new Pair<>( "3421", 50.0 ) 
      );
      meal = database.dayPlans().objectList().get( 1 );
      assertMealProperties( 
               meal, "556676", "Meal2",
               "369b6651-633a-420b-b560-b1c1d3dfe1cb",
               LocalDate.parse( "2017-01-01" ),
               new Pair<>( "67890", 40.0 ),
               new Pair<>( "3421", 90.0 ),
               new Pair<>( "1324", 100.0 )
      );
      
      //removed plan with no date
      assertThat( database.dayPlans().objectList(), hasSize( 2 ) );
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
      
      Goal goal1 = new Goal( "Goal 1" );
      database.goals().store( goal1 );
      
      Goal goal2 = new Goal( "Goal 2" );
      database.goals().store( goal2 );
      
      DayPlan meal1 = new DayPlan( "99987", "Meal1" );
      meal1.portions().add( new FoodPortion( item1, 100.0 ) );
      meal1.portions().add( new FoodPortion( item2, 90.0 ) );
      meal1.portions().add( new FoodPortion( item3, 50.0 ) );
      meal1.goalAnalytics().goal().set( goal1 );
      meal1.setDate( LocalDate.now() );
      database.dayPlans().store( meal1 );
      
      DayPlan meal2 = new DayPlan( "556676", "Meal2" );
      meal2.portions().add( new FoodPortion( item2, 40.0 ) );
      meal2.portions().add( new FoodPortion( item3, 90.0 ) );
      meal2.portions().add( new FoodPortion( item4, 100.0 ) );
      meal2.portions().add( new FoodPortion() );
      meal2.goalAnalytics().goal().set( goal2 );
      meal2.setDate( LocalDate.now() );
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
      
      DayPlanPersistence persistence = new DayPlanPersistence( database, database.dayPlans() );
      JSONObject mealJson = new JSONObject();
      persistence.structure().build( mealJson );
      persistence.writeHandles().parse( mealJson );
      
      System.out.println( mealJson );
      
      GoalPersistence goalPersistence = new GoalPersistence( database.goals() );
      JSONObject goalJson = new JSONObject();
      goalPersistence.structure().build( goalJson );
      goalPersistence.writeHandles().parse( goalJson );
      
      
      database = new Database();
      foodItemPersistence = new FoodItemPersistence( database );
      
      assertThat( database.foodItems().objectList(), is( empty() ) );
      foodItemPersistence.readHandles().parse( foodItemJson );
      assertThat( database.foodItems().objectList(), hasSize( 4 ) );
      
      goalPersistence = new GoalPersistence( database.goals() );
      
      assertThat( database.goals().objectList(), is( empty() ) );
      goalPersistence.readHandles().parse( goalJson );
      assertThat( database.goals().objectList(), hasSize( 2 ) );
      
      persistence = new DayPlanPersistence( database, database.dayPlans() );
      
      assertThat( database.dayPlans().objectList(), is( empty() ) );
      persistence.readHandles().parse( mealJson );
      assertThat( database.dayPlans().objectList(), hasSize( 2 ) );
      
      DayPlan meal = database.dayPlans().get( meal1.properties().id() );
      assertMealProperties( meal, meal1 );
      meal = database.dayPlans().get( meal2.properties().id() );
      assertMealProperties( meal, meal2 );
      
      //Remove plans with missing date and no portions
      assertThat( database.dayPlans().objectList(), hasSize( 2 ) );
   }//End Method

   private void assertMealProperties(
            DayPlan meal, 
            String id, String name, String goalId, LocalDate date, 
            Pair< String, Double >... portions
   ){
      assertThat( meal.properties().id(), is( id ) );
      assertThat( meal.properties().nameProperty().get(), is( name ) );
      assertThat( meal.goalAnalytics().goal().get().properties().id(), is( goalId ) );
      assertThat( meal.date(), is( date ) );
      assertThat( meal.portions(), hasSize( portions.length ) );
      
      for ( int i = 0; i < portions.length; i++ ) {
         Pair< String, Double > expectedPortion = portions[ i ];
         FoodPortion portion = meal.portions().get( i );
         assertThat( expectedPortion.getKey(), is( portion.food().get().properties().id() ) );
         assertThat( expectedPortion.getValue(), is( portion.portion().get() ) );
      }
   }//End Method
   
   private void assertMealProperties(
            DayPlan meal, DayPlan expected
   ){
//      assertThat( meal.goalAnalytics().goal().get().properties().id(), is( expected.goalAnalytics().goal().get().properties().id() ) );
      assertThat( meal.date(), is( expected.date() ) );
      assertThat( meal.properties().id(), is( expected.properties().id() ) );
      assertThat( meal.properties().nameProperty().get(), is( expected.properties().nameProperty().get() ) );
      assertThat( meal.portions(), hasSize( expected.portions().size() ) );
      
      for ( int i = 0; i < expected.portions().size(); i++ ) {
         FoodPortion expectedPortion = expected.portions().get( i );
         FoodPortion portion = meal.portions().get( i );
         if ( expectedPortion.food().get() == null ) {
            assertThat( portion.food().get(), is( nullValue() ) );
         } else {
            assertThat( expectedPortion.food().get().properties().id(), is( portion.food().get().properties().id() ) );
         }
         assertThat( expectedPortion.portion().get(), is( portion.portion().get() ) );
      }
   }//End Method

}
