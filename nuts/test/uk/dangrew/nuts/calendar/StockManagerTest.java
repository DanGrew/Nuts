package uk.dangrew.nuts.calendar;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.meal.Meal;

public class StockManagerTest {

   private FoodItem item1;
   private FoodItem item2;
   private FoodItem item3;
   private FoodItem item4;
   private FoodItem item5;
   
   private Meal meal1;
   private Meal meal2;
   
   private FoodItemStore store;
   private FoodCalendar calendar;
   @Mock private StockCalculator calculator;
   private StockManager systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      store = new FoodItemStore( new Goal( "" ) );
      item1 = store.createFood( "Item1" );
      item2 = store.createFood( "Item2" );
      item3 = store.createFood( "Item3" );
      item4 = store.createFood( "Item4" );
      item5 = store.createFood( "Item5" );
      
      CalendarPeriod period = new CalendarPeriod();
      for ( int i = 0; i < 10; i++ ) {
         period.modifiableDays().add( LocalDate.now().plusDays( i ) );
      }
      calendar = new FoodCalendar( 
               period,
               new DayPlans(),
               store
      );
      
      meal1 = new Meal( "Meal1" );
      meal2 = new Meal( "Meal2" );
      
      systemUnderTest = new StockManager( calculator );
      systemUnderTest.associate( calendar, store );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations(){
      systemUnderTest.associate( calendar, store );
   }//End Method
   
   @Test public void shouldAssociateCalculator(){
      verify( calculator ).associate( calendar.dayPlans(), calendar.period() );
   }//End Method

   @Test public void shouldCalculateEmptyStockInitially(){
      store.objectList().forEach( f -> {
         verify( calculator ).recalculate( f );
      } );
   }//End Method
   
   @Test public void shouldRecalculateWhenMealSetForDayPlan(){
      verifyRecalculations( 1, 1, 1, 1, 1 );
      meal1.portions().addAll( 
               new FoodPortion( item1, 100 ),
               new FoodPortion( item4, 100 )
      );
      calendar.dayPlans().dayPlanFor( calendar.period().get( 3 ) ).plan().set( meal1 );
      verifyRecalculations( 2, 1, 1, 2, 1 );
   }//End Method
   
   @Test public void shouldRecalculateWhenMealRemovedForDayPlan(){
      verifyRecalculations( 1, 1, 1, 1, 1 );
      meal1.portions().addAll( 
               new FoodPortion( item1, 100 ),
               new FoodPortion( item4, 100 )
      );
      calendar.dayPlans().dayPlanFor( calendar.period().get( 3 ) ).plan().set( meal1 );
      verifyRecalculations( 2, 1, 1, 2, 1 );
      
      calendar.dayPlans().dayPlanFor( calendar.period().get( 3 ) ).plan().set( null );
      verifyRecalculations( 3, 1, 1, 3, 1 );
   }//End Method
   
   @Test public void shouldRecalculateWhenMealChangedForDayPlan(){
      verifyRecalculations( 1, 1, 1, 1, 1 );
      meal1.portions().addAll( 
               new FoodPortion( item1, 100 ),
               new FoodPortion( item4, 100 )
      );
      meal2.portions().addAll( 
               new FoodPortion( item1, 75 ),
               new FoodPortion( item3, 100 ),
               new FoodPortion( item5, 9 )
      );
      calendar.dayPlans().dayPlanFor( calendar.period().get( 3 ) ).plan().set( meal1 );
      verifyRecalculations( 2, 1, 1, 2, 1 );
      
      calendar.dayPlans().dayPlanFor( calendar.period().get( 3 ) ).plan().set( meal2 );
      verifyRecalculations( 3, 1, 2, 3, 2 );
      
      meal1.stockUsage().stock().put( item1, 40.0 );
      verifyRecalculations( 3, 1, 2, 3, 2 );
   }//End Method
   
   @Test public void shouldRecalculateWhenMealStockChanges(){
      calendar.dayPlans().dayPlanFor( calendar.period().get( 3 ) ).plan().set( meal1 );
      verifyRecalculations( 1, 1, 1, 1, 1 );
      
      meal1.stockUsage().stock().put( item1, 40.0 );
      verifyRecalculations( 2, 1, 1, 1, 1 );
      
      meal1.stockUsage().stock().put( item1, 50.0 );
      verifyRecalculations( 3, 1, 1, 1, 1 );
      
      meal1.stockUsage().stock().put( item5, 50.0 );
      verifyRecalculations( 3, 1, 1, 1, 2 );
   }//End Method
   
   @Test public void shouldRecalculateAllForNewDayPlan(){
      verifyRecalculations( 1, 1, 1, 1, 1 );
      LocalDate insertedDate = LocalDate.now().plusDays( 20 );
      DayPlan insertedPlan = new DayPlan( insertedDate );
      calendar.dayPlans().dayPlans().put( insertedDate, insertedPlan );
      verifyRecalculations( 2, 2, 2, 2, 2 );
      
      insertedPlan.plan().set( meal1 );
      verifyRecalculations( 2, 2, 2, 2, 2 );
      
      meal1.stockUsage().stock().put( item1, 40.0 );
      verifyRecalculations( 3, 2, 2, 2, 2 );
   }//End Method
   
   @Test public void shouldRecalculateDayPlanAfterRemoved(){
      verifyRecalculations( 1, 1, 1, 1, 1 );
      DayPlan removed = calendar.dayPlans().dayPlans().remove( LocalDate.now() );
      verifyRecalculations( 2, 2, 2, 2, 2 );
      
      removed.plan().set( meal1 );
      meal1.stockUsage().stock().put( item1, 40.0 );
      verifyRecalculations( 2, 2, 2, 2, 2 );
   }//End Method
   
   /**
    * Verifies that each of the {@link FoodItem}s is recalculated for the given number of times.
    * @param f1 the first {@link FoodItem} recalculation count.
    * @param f2 the second {@link FoodItem} recalculation count.
    * @param f3 the third {@link FoodItem} recalculation count.
    * @param f4 the fourth {@link FoodItem} recalculation count.
    * @param f5 the fifth {@link FoodItem} recalculation count.
    */
   private void verifyRecalculations( int f1, int f2, int f3, int f4, int f5 ) {
      verify( calculator, times( f1 ) ).recalculate( item1 );
      verify( calculator, times( f2 ) ).recalculate( item2 );
      verify( calculator, times( f3 ) ).recalculate( item3 );
      verify( calculator, times( f4 ) ).recalculate( item4 );
      verify( calculator, times( f5 ) ).recalculate( item5 );
   }//End Method

}//End Class
