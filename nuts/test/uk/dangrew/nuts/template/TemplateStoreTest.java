package uk.dangrew.nuts.template;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.goal.Goal;

public class TemplateStoreTest {

   private Goal goal;
   private Template food;
   private TemplateStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food = new Template( "Meal" );
      goal = new Goal( "Goal" );
      systemUnderTest = new TemplateStore();
   }//End Method

   @Test public void shouldStoreById() {
      systemUnderTest.setDefaultGoal( goal );
      
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      assertThat( food.goalAnalytics().goal().get(), is( goal ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      systemUnderTest.setDefaultGoal( goal );
      
      Template newFood = systemUnderTest.createFood( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
      assertThat( newFood.goalAnalytics().goal().get(), is( goal ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.setDefaultGoal( goal );
      
      systemUnderTest.store( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( food ) );
      systemUnderTest.removeFood( food );
      assertThat( systemUnderTest.get( food.properties().id() ), is( nullValue() ) );
      assertThat( food.goalAnalytics().goal().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideDefaultGoal(){
      assertThat( systemUnderTest.defaultGoal(), is( nullValue() ) );
      systemUnderTest.setDefaultGoal( goal );
      assertThat( systemUnderTest.defaultGoal(), is( goal ) );
   }//End Method

}//End Class
