package uk.dangrew.nuts.persistence.setup;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.Goal;
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
      database.shoppingLists().createFood( "Anything" );
      systemUnderTest.configureDefaultShoppingList();
      assertThat( database.shoppingLists().objectList(), hasSize( 1 ) );
   }//End Method

}//End Class
