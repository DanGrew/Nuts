package uk.dangrew.nuts.persistence.setup;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.progress.SystemDateRange;
import uk.dangrew.nuts.store.Database;

public class DataSetupTest {

   private Goal goal;
   private Database database;
   private DataSetup systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      goal = new Goal( "Goal" );
      database = new Database();
      systemUnderTest = new DataSetup( database );
   }//End Method

   @Test public void shouldIgnoreDefaultGoalIfSet() {
      database.templates().setDefaultGoal( goal );
      systemUnderTest.configureDefaultGoal();
      assertThat( database.templates().defaultGoal(), is( goal ) );
   }//End Method
   
   @Test public void shouldCreateDefaultGoalIfNonePresent() {
      systemUnderTest.configureDefaultGoal();
      assertThat( database.templates().defaultGoal(), is( notNullValue() ) );
      assertThat( database.goals().objectList().get( 0 ), is( database.templates().defaultGoal() ) );
   }//End Method
   
   @Test public void shouldSetDefaultGoalToFirstInListIfNotSet() {
      database.goals().store( goal );
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

}//End Class
