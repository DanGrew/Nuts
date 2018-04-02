package uk.dangrew.nuts.goal.proportion;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ProportionGoalStoreTest {

   private ProportionGoal proportionGoal;
   private ProportionGoalStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      proportionGoal = new ProportionGoal( "Goal" );
      systemUnderTest = new ProportionGoalStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( proportionGoal.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( proportionGoal );
      assertThat( systemUnderTest.get( proportionGoal.properties().id() ), is( proportionGoal ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      ProportionGoal newFood = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( proportionGoal );
      assertThat( systemUnderTest.get( proportionGoal.properties().id() ), is( proportionGoal ) );
      systemUnderTest.removeConcept( proportionGoal );
      assertThat( systemUnderTest.get( proportionGoal.properties().id() ), is( nullValue() ) );
   }//End Method

}//End Class
