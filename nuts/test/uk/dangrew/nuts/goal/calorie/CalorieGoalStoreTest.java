package uk.dangrew.nuts.goal.calorie;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.calorie.CalorieGoal;
import uk.dangrew.nuts.goal.calorie.CalorieGoalImpl;
import uk.dangrew.nuts.goal.calorie.CalorieGoalStore;

public class CalorieGoalStoreTest {

   private CalorieGoal calorieGoal;
   private CalorieGoalStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      calorieGoal = new CalorieGoalImpl( "Goal" );
      systemUnderTest = new CalorieGoalStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( calorieGoal.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( calorieGoal );
      assertThat( systemUnderTest.get( calorieGoal.properties().id() ), is( calorieGoal ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      CalorieGoal newFood = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( calorieGoal );
      assertThat( systemUnderTest.get( calorieGoal.properties().id() ), is( calorieGoal ) );
      systemUnderTest.removeConcept( calorieGoal );
      assertThat( systemUnderTest.get( calorieGoal.properties().id() ), is( nullValue() ) );
   }//End Method

}//End Class
