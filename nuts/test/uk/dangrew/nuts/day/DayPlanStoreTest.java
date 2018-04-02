package uk.dangrew.nuts.day;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class DayPlanStoreTest {

   private DayPlan plan;
   private DayPlanStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      plan = new DayPlan( LocalDate.now().plusDays( 1000 ) );
      systemUnderTest = new DayPlanStore();
   }//End Method

   @Test public void shouldStoreById() {
      assertThat( systemUnderTest.get( plan.properties().id() ), is( nullValue() ) );
      systemUnderTest.store( plan );
      assertThat( systemUnderTest.get( plan.properties().id() ), is( plan ) );
   }//End Method
   
   @Test public void shouldCreateNew() {
      DayPlan newFood = systemUnderTest.createConcept( "NewName" );
      assertThat( systemUnderTest.get( newFood.properties().id() ), is( newFood ) );
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( plan );
      assertThat( systemUnderTest.get( plan.properties().id() ), is( plan ) );
      systemUnderTest.removeConcept( plan );
      assertThat( systemUnderTest.get( plan.properties().id() ), is( nullValue() ) );
      assertThat( plan.goalAnalytics().goal().get(), is( nullValue() ) );
   }//End Method
   
}//End Class
