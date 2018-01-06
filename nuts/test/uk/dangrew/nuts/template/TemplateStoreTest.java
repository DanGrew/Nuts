package uk.dangrew.nuts.template;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.goal.GoalImpl;

public class TemplateStoreTest {

   private Goal goal;
   private Template food;
   private TemplateStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food = new Template( "Meal" );
      goal = new GoalImpl( "Goal" );
      systemUnderTest = new TemplateStore();
   }//End Method

   @Test public void shouldStoreById() {
      systemUnderTest.setDefaultGoal( goal );
      
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      assertThat( food.goalAnalytics().goal().get(), is( goal ) );
   }//End Method
   
   @Test public void shouldNotOverwriteGoalWhenStored() {
      systemUnderTest.setDefaultGoal( goal );
      
      food.goalAnalytics().goal().set( new GoalImpl( "anything" ) );
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      assertThat( food.goalAnalytics().goal().get(), is( not( goal ) ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      systemUnderTest.setDefaultGoal( goal );
      
      Template newFood = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
      assertThat( newFood.goalAnalytics().goal().get(), is( goal ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.setDefaultGoal( goal );
      
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      systemUnderTest.removeConcept( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      assertThat( food.goalAnalytics().goal().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideDefaultGoal(){
      assertThat( systemUnderTest.defaultGoal(), is( nullValue() ) );
      systemUnderTest.setDefaultGoal( goal );
      assertThat( systemUnderTest.defaultGoal(), is( goal ) );
   }//End Method

}//End Class
