package uk.dangrew.nuts.calendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodItemStore;
import uk.dangrew.nuts.goal.Goal;
import uk.dangrew.nuts.meal.Meal;

public class StockCalculatorTest {

   private FoodItem item1;
   private FoodItem item2;
   
   private Meal meal1;
   private Meal meal2;
   
   private FoodItemStore store;
   private FoodCalendar calendar;
   private DayPlans plans;
   private StockCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      store = new FoodItemStore( new Goal( "" ) );
      item1 = store.createFood( "Item1" );
      item2 = store.createFood( "Item2" );
      
      CalendarPeriod period = new CalendarPeriod();
      for ( int i = 0; i < 10; i++ ) {
         period.modifiableDays().add( LocalDate.now().plusDays( i ) );
      }
      calendar = new FoodCalendar( 
               period,
               plans = new DayPlans(),
               store
      );
      
      meal1 = new Meal( "Meal1" );
      meal2 = new Meal( "Meal2" );
      
      systemUnderTest = new StockCalculator();
      systemUnderTest.associate( calendar.dayPlans(), calendar.period() );
   }//End Method

   @Test( expected = IllegalStateException.class ) public void shouldNotAllowMultipleAssociations(){
      systemUnderTest.associate( calendar.dayPlans(), calendar.period() );
   }//End Method
   
   @Test public void shouldCalculateNoStockInitially() {
      systemUnderTest.recalculate( item1 );
      calendar.period().forEach( d -> {
         assertThat( plans.dayPlanFor( d ).stockFor( item1 ).get(), is( 0.0 ) );
      } );
   }//End Method
   
   @Test public void shouldTakeStockFromMeal() {
      plans.dayPlanFor( calendar.period().get( 4 ) ).plan().set( meal1 );
      meal1.stockUsage().stock().put( item1, 100.0 );
      
      systemUnderTest.recalculate( item1 );
      assertStockForItem( item1, 0, 0, 0, 0, -100, -100, -100, -100, -100, -100 );
      
      meal1.stockUsage().stock().put( item1, 50.0 );
      systemUnderTest.recalculate( item1 );
      assertStockForItem( item1, 0, 0, 0, 0, -50, -50, -50, -50, -50, -50 );
      
      meal1.stockUsage().stock().put( item2, 400.0 );
      systemUnderTest.recalculate( item1 );
      systemUnderTest.recalculate( item2 );
      assertStockForItem( item1, 0, 0, 0, 0, -50, -50, -50, -50, -50, -50 );
      assertStockForItem( item2, 0, 0, 0, 0, -400, -400, -400, -400, -400, -400 );
   }//End Method
   
   @Test public void shouldTakeStockFromMultipleMeals() {
      plans.dayPlanFor( calendar.period().get( 4 ) ).plan().set( meal1 );
      meal1.stockUsage().stock().put( item1, 100.0 );
      
      systemUnderTest.recalculate( item1 );
      assertStockForItem( item1, 0, 0, 0, 0, -100, -100, -100, -100, -100, -100 );
      
      plans.dayPlanFor( calendar.period().get( 6 ) ).plan().set( meal2 );
      meal2.stockUsage().stock().put( item1, 50.0 );
      
      systemUnderTest.recalculate( item1 );
      assertStockForItem( item1, 0, 0, 0, 0, -100, -100, -150, -150, -150, -150 );
   }//End Method
   
   @Test public void shouldAccountForMealChange() {
      plans.dayPlanFor( calendar.period().get( 4 ) ).plan().set( meal1 );
      meal1.stockUsage().stock().put( item1, 100.0 );
      
      systemUnderTest.recalculate( item1 );
      assertStockForItem( item1, 0, 0, 0, 0, -100, -100, -100, -100, -100, -100 );
      
      plans.dayPlanFor( calendar.period().get( 4 ) ).plan().set( meal2 );
      meal2.stockUsage().stock().put( item1, 50.0 );
      
      systemUnderTest.recalculate( item1 );
      assertStockForItem( item1, 0, 0, 0, 0, -50, -50, -50, -50, -50, -50 );
      
      plans.dayPlanFor( calendar.period().get( 4 ) ).plan().set( null );
      systemUnderTest.recalculate( item1 );
      assertStockForItem( item1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 );
   }//End Method
   
   private void assertStockForItem( FoodItem item, double... stock ) {
      assertThat( stock.length, is( calendar.period().size() ) );
      
      for ( int i = 0; i < stock.length; i++ ) {
         assertStockForFoodOnDay( i, item, stock[ i ] );
      }
   }//End Method
   
   private void assertStockForFoodOnDay( int dateIndex, FoodItem item, double stock ) {
      DayPlan plan = calendar.dayPlans().dayPlanFor( calendar.period().get( dateIndex ) );
      assertThat( plan.stockFor( item ).get(), is( stock ) );
   }//End Method

}//End Class
