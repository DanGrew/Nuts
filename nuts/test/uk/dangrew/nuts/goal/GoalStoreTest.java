package uk.dangrew.nuts.goal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class GoalStoreTest {

   private Goal goal;
   private GoalStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      goal = new Goal( "Goal" );
      systemUnderTest = new GoalStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( goal.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( goal );
      assertThat( systemUnderTest.get( goal.properties().id() ), is( goal ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      Goal newFood = systemUnderTest.createFood( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( goal );
      assertThat( systemUnderTest.get( goal.properties().id() ), is( goal ) );
      systemUnderTest.removeFood( goal );
      assertThat( systemUnderTest.get( goal.properties().id() ), is( nullValue() ) );
   }//End Method

}//End Class
