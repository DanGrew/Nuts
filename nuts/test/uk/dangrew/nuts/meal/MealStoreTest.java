package uk.dangrew.nuts.meal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.Goal;

public class MealStoreTest {

   private Goal goal;
   private Meal food;
   private MealStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food = new Meal( "Meal" );
      goal = new Goal( "Goal" );
      systemUnderTest = new MealStore( goal );
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      assertThat( food.goalAnalytics().goal().get(), is( goal ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      Meal newFood = systemUnderTest.createFood( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
      assertThat( newFood.goalAnalytics().goal().get(), is( goal ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      systemUnderTest.removeFood( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      assertThat( food.goalAnalytics().goal().get(), is( nullValue() ) );
   }//End Method

}//End Class
