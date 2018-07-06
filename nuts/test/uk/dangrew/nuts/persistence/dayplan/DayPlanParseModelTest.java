package uk.dangrew.nuts.persistence.dayplan;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.store.Database;

public class DayPlanParseModelTest {

   private DayPlan dayPlan;
   private FoodItem item;
   
   private Database database;
   private DayPlanParseModel systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      dayPlan = database.dayPlans().createConcept( "DayPlan" );
      item = database.foodItems().createConcept( "Item" );
      
      systemUnderTest = new DayPlanParseModel( database );
   }//End Method

   @Test public void shouldAddResolutionToDatabase() {
      systemUnderTest.setId( dayPlan.properties().id() );
      systemUnderTest.setFoodId( item.properties().id() );
      systemUnderTest.setPortionValue( 125.0 );
      systemUnderTest.setConsumed( true );
      systemUnderTest.finishPortion();
      
      assertThat( dayPlan.portions(), is( empty() ) );
      database.resolver().resolve();
      assertThat( dayPlan.portions().get( 0 ).food().get().properties().nameProperty().get(), is( item.properties().nameProperty().get() ) );
      assertThat( dayPlan.portions().get( 0 ).portion().get(), is( 125.0 ) );
      assertThat( dayPlan.consumed().iterator().next().food().get().properties().nameProperty().get(), is( item.properties().nameProperty().get() ) );
      assertThat( dayPlan.consumed().iterator().next().portion().get(), is( 125.0 ) );
   }//End Method

}//End Class
