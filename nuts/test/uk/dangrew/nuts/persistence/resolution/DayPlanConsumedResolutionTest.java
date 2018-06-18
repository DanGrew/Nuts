package uk.dangrew.nuts.persistence.resolution;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.store.Database;

public class DayPlanConsumedResolutionTest {

   private Database database;
   private DayPlan subject;
   private FoodItem item;;
   
   private DayPlanConsumedResolution systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      item = database.foodItems().createConcept( "Item" );
      subject = database.dayPlans().createConcept( "Plan" );
      systemUnderTest = new DayPlanConsumedResolution( 
               database.dayPlans(), 
               subject.properties().id(), 
               item.properties().id(), 
               121.0,
               true
      );
   }//End Method

   @Test public void shouldApplyPortion() {
      systemUnderTest.resolve( database );
      assertThat( subject.portions().get( 0 ).food().get(), is( item ) );
      assertThat( subject.portions().get( 0 ).portion().get(), is( 121.0 ) );
      assertThat( subject.portions(), hasSize( 1 ) );
      assertThat( subject.consumed().iterator().next().food().get(), is( item ) );
      assertThat( subject.consumed().iterator().next().portion().get(), is( 121.0 ) );
      assertThat( subject.consumed(), hasSize( 1 ) );
   }//End Method

}//End Class
