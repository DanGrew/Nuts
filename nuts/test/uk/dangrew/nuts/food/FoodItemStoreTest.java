package uk.dangrew.nuts.food;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.Goal;

public class FoodItemStoreTest {

   private Goal goal;
   private FoodItem food;
   private FoodItemStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food = new FoodItem( "Food" );
      goal = new Goal( "Goal" );
      systemUnderTest = new FoodItemStore( goal );
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      assertThat( food.goalAnalytics().goal().get(), is( goal ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      FoodItem newFood = systemUnderTest.createFood( "NewName" );
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
