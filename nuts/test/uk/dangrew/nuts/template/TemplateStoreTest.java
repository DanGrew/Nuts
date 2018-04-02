package uk.dangrew.nuts.template;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.CalorieGoalImpl;
import uk.dangrew.nuts.goal.Goal;

public class TemplateStoreTest {

   private Goal calorieGoal;
   private Template food;
   private TemplateStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food = new Template( "Meal" );
      calorieGoal = new CalorieGoalImpl( "Goal" );
      systemUnderTest = new TemplateStore();
   }//End Method

   @Test public void shouldStoreById() {
      systemUnderTest.setDefaultGoal( calorieGoal );
      
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      assertThat( food.goalAnalytics().goal().get(), is( calorieGoal ) );
   }//End Method
   
   @Test public void shouldNotOverwriteGoalWhenStored() {
      systemUnderTest.setDefaultGoal( calorieGoal );
      
      food.goalAnalytics().goal().set( new CalorieGoalImpl( "anything" ) );
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      assertThat( food.goalAnalytics().goal().get(), is( not( calorieGoal ) ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      systemUnderTest.setDefaultGoal( calorieGoal );
      
      Template newFood = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
      assertThat( newFood.goalAnalytics().goal().get(), is( calorieGoal ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.setDefaultGoal( calorieGoal );
      
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      systemUnderTest.removeConcept( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      assertThat( food.goalAnalytics().goal().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideDefaultGoal(){
      assertThat( systemUnderTest.defaultGoal(), is( nullValue() ) );
      systemUnderTest.setDefaultGoal( calorieGoal );
      assertThat( systemUnderTest.defaultGoal(), is( calorieGoal ) );
   }//End Method

}//End Class
