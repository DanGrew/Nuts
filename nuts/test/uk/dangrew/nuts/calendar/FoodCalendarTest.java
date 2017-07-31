package uk.dangrew.nuts.calendar;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javafx.collections.ObservableList;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.goal.Goal;

public class FoodCalendarTest {

   private FoodItemStore store;
   private CalendarPeriod period;
   @Spy private DayPlans dayPlans;
   private FoodCalendar systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      store = new FoodItemStore( new Goal( "" ) );
      period = new CalendarPeriod();
      systemUnderTest = new FoodCalendar( period, dayPlans, store );
   }//End Method

   @Test public void shouldProvideCalendarPeriod() {
      assertThat( systemUnderTest.period(), is( period.days() ) );
   }//End Method
   
   @Test public void shouldProvideStockChanges(){
      assertThat( systemUnderTest.stockChanges(), is( instanceOf( ObservableList.class ) ) );
   }//End Method
   
   @Test public void shouldProvideDayPlans() {
      assertThat( systemUnderTest.dayPlans(), is( dayPlans ) );
   }//End Method
   
   @Test public void shouldAssociateDayPlans() {
      verify( dayPlans ).associate( period, store );
   }//End Method
   
}//End Class
