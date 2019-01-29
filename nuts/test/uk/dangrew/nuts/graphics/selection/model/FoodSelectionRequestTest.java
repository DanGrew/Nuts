package uk.dangrew.nuts.graphics.selection.model;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.meal.Meal;

public class FoodSelectionRequestTest {

   private Meal meal;
   private Goal goal;
   private FoodSelectionRequest systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      meal = new Meal( "Meal" );
      goal = new CalorieGoalImpl( "Goal" );
      systemUnderTest = new FoodSelectionRequest( meal, goal );
   }//End Method

   @Test public void shouldProvideMeal() {
      assertThat( systemUnderTest.meal(), is( meal ) );
   }//End Method
   
   @Test public void shouldProvideGoal() {
      assertThat( systemUnderTest.goal(), is( goal ) );
   }//End Method
   
   @Test public void shouldSupportNoGoal() {
      systemUnderTest = new FoodSelectionRequest( meal );
      assertThat( systemUnderTest.goal(), is( nullValue() ) );
   }//End Method

}//End Class
