package uk.dangrew.nuts.table.food;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.meal.MealStore;
import uk.dangrew.sd.graphics.launch.TestApplication;

public class FoodTableWithControlsTest {

   private MealStore store;
   private FoodTableWithControls< Meal > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      store = new MealStore( new Goal( "Goal" ) );
      systemUnderTest = new FoodTableWithControls<>( store );
   }//End Method

   @Test public void shouldProvideTable() {
      assertThat( systemUnderTest.table(), is( notNullValue() ) );
      assertThat( systemUnderTest.getCenter(), is( systemUnderTest.table() ) );
   }//End Method
   
   @Test public void shouldProvideControls() {
      assertThat( systemUnderTest.controls(), is( notNullValue() ) );
      assertThat( systemUnderTest.getRight(), is( systemUnderTest.controls() ) );
   }//End Method

}//End Class
