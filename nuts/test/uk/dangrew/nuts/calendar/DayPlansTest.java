package uk.dangrew.nuts.calendar;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.observable.PrivatelyModifiableObservableMapImpl;
import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.goal.Goal;

public class DayPlansTest {

   private static final int TEST_DAYS = 5;
   private static final int TEST_FOODS = 7;
   
   private FoodItemStore store;
   private CalendarPeriod period;
   private DayPlans systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      period = new CalendarPeriod();
      for ( int i = 0; i < TEST_DAYS; i++ ) {
         period.modifiableDays().add( LocalDate.now().plusDays( i ) );
      }
      store = new FoodItemStore( new Goal( "anything" ) );
      for ( int i = 0; i < TEST_FOODS; i++ ) {
         store.store( new FoodItem( "Food " + i ) );
      }
      
      systemUnderTest = new DayPlans();
      systemUnderTest.associate( period, store );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations() {
      systemUnderTest.associate( period, store );
   }//End Method
   
   @Test public void shouldCreateDayPlanForEachDateInPeriod() {
      for ( int i = 0; i < TEST_DAYS; i++ ) {
         assertThat( systemUnderTest.dayPlanFor( LocalDate.now().plusDays( i ) ), is( notNullValue() ) );
      }
   }//End Method
   
   @Test public void shouldAddDayPlanForNewPeriodDay() {
      int TEST_DAY = 10;
      assertThat( systemUnderTest.dayPlanFor( LocalDate.now().plusDays( TEST_DAY ) ), is( nullValue() ) );
      period.modifiableDays().add( LocalDate.now().plusDays( TEST_DAY ) );
      assertThat( systemUnderTest.dayPlanFor( LocalDate.now().plusDays( TEST_DAY ) ), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldRemoveDayPlanForRemovedPeriodDay() {
      int TEST_DAY = 3;
      assertThat( systemUnderTest.dayPlanFor( LocalDate.now().plusDays( 3 ) ), is( notNullValue() ) );
      period.modifiableDays().remove( LocalDate.now().plusDays( TEST_DAY ) );
      assertThat( systemUnderTest.dayPlanFor( LocalDate.now().plusDays( TEST_DAY ) ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldNotDuplicatePlans(){
      DayPlan plan = systemUnderTest.dayPlanFor( LocalDate.now() );
      period.modifiableDays().add( LocalDate.now() );
      assertThat( systemUnderTest.dayPlanFor( LocalDate.now() ), is( plan ) );
   }//End Method
   
   @Test public void shouldAddEachFoodItemToEachPlan(){
      for ( int j = 0; j < TEST_DAYS; j++ ) {
         DayPlan plan = systemUnderTest.dayPlanFor( LocalDate.now().plusDays( j ) );
         for ( int i = 0; i < TEST_FOODS; i++ ) {
            FoodItem item = store.objectList().get( i );
            assertThat( plan.stockFor( item ), is( notNullValue() ) );
            assertThat( plan.stockFor( item ).get(), is( 0.0 ) );
         }
      }
   }//End Method
   
   @Test public void shouldAddNewFoodItemToEachPlan(){
      FoodItem item = new FoodItem( "something" );
      
      for ( int j = 0; j < TEST_DAYS; j++ ) {
         DayPlan plan = systemUnderTest.dayPlanFor( LocalDate.now().plusDays( j ) );
         assertThat( plan.stockFor( item ), is( nullValue() ) );
      }
      store.store( item );
      
      for ( int j = 0; j < TEST_DAYS; j++ ) {
         DayPlan plan = systemUnderTest.dayPlanFor( LocalDate.now().plusDays( j ) );
         assertThat( plan.stockFor( item ), is( notNullValue() ) );
         assertThat( plan.stockFor( item ).get(), is( 0.0 ) );
      }
   }//End Method
   
   @Test public void shouldRemoveFoodItemFromEachPlan(){
      FoodItem item = store.objectList().get( 3 );
      
      for ( int j = 0; j < TEST_DAYS; j++ ) {
         DayPlan plan = systemUnderTest.dayPlanFor( LocalDate.now().plusDays( j ) );
         assertThat( plan.stockFor( item ), is( notNullValue() ) );
         assertThat( plan.stockFor( item ).get(), is( 0.0 ) );
      }
      
      store.removeFood( item );
      for ( int j = 0; j < TEST_DAYS; j++ ) {
         DayPlan plan = systemUnderTest.dayPlanFor( LocalDate.now().plusDays( j ) );
         assertThat( plan.stockFor( item ), is( nullValue() ) );
      }
   }//End Method
   
   @Test public void shouldProvideNonModifiableMap(){
//      Left for now, not sure how it should work
//      assertThat( systemUnderTest.dayPlans(), is( instanceOf( PrivatelyModifiableObservableMapImpl.class ) ) );
   }//End Method
   
}//End Class
