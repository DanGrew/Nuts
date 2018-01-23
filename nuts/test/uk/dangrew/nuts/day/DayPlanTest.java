package uk.dangrew.nuts.day;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;

public class DayPlanTest {

   private FoodItem food1;
   private FoodItem food2;
   private FoodPortion portion1;
   private FoodPortion portion2;
   
   private LocalDate date;
   private DayPlan systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food1 = new FoodItem( "Food1" );
      food2 = new FoodItem( "Food2" );
      portion1 = new FoodPortion();
      portion2 = new FoodPortion();
      
      date = LocalDate.now();
      systemUnderTest = new DayPlan( "Plan" );
   }//End Method

   @Test public void shouldProvideDate() {
      assertThat( systemUnderTest.date(), is( nullValue() ) );
      systemUnderTest.setDate( date );
      assertThat( systemUnderTest.date(), is( date ) );
   }//End Method
   
   @Test( expected = IllegalStateException.class ) public void shouldSetDateOnlyOnce(){
      systemUnderTest.setDate( date );
      systemUnderTest.setDate( date );
   }//End Method
   
   @Test public void shouldTrackConsumption(){
      FoodPortion portion1 = mock( FoodPortion.class );
      
      systemUnderTest.portions().add( portion1 );
      
      assertThat( systemUnderTest.consumed(), is( empty() ) );
      systemUnderTest.consumed().add( portion1 );
   }//End Method
   
   @Test public void shouldRemoveConsumptionWhenRemovedFromPortions(){
      FoodPortion portion1 = mock( FoodPortion.class );
      systemUnderTest.portions().add( portion1 );
      systemUnderTest.consumed().add( portion1 );
      
      systemUnderTest.portions().remove( portion1 );
      assertThat( systemUnderTest.consumed(), is( empty() ) );
   }//End Method
   
   @Test public void shouldPreserveConsumedWhenSwapped(){
      systemUnderTest.portions().add( portion1 );
      systemUnderTest.portions().add( portion2 );
      systemUnderTest.consumed().add( portion1 );
      systemUnderTest.consumed().add( portion2 );
      assertThat( systemUnderTest.portions(), contains( portion1, portion2 ) );
      assertThat( systemUnderTest.consumed(), contains( portion1, portion2 ) );
      
      systemUnderTest.swap( portion1, portion2 );
      assertThat( systemUnderTest.portions(), contains( portion2, portion1 ) );
      assertThat( systemUnderTest.consumed(), contains( portion1, portion2 ) );
   }//End Method
   
   @Test public void shouldNotDuplicate(){
      assertThat( systemUnderTest.duplicate( "anything" ), is( systemUnderTest ) );
   }//End Method
   
   @Test public void shouldProvideConsumedCalories(){
      assertThat( systemUnderTest.consumedCalories().get(), is( DayPlan.DEFAULT_CONSUMED_CALORIES ) );
   }//End Method
   
   @Test public void shouldProvideAllowedCalories(){
      assertThat( systemUnderTest.allowedCalories().get(), is( DayPlan.DEFAULT_ALLOWED_CALORIES ) );
   }//End Method
   
   @Test public void shouldProvideCalorieBalance(){
      assertThat( systemUnderTest.calorieBalance().get(), is( DayPlan.DEFAULT_CALORIE_BALANCE ) );
   }//End Method
   
   @Test public void shouldProvideBalanceResetProperty(){
      assertThat( systemUnderTest.isBalanceReset().get(), is( DayPlan.DEFAULT_BALANCE_IS_RESET ) );
   }//End Method
   
}//End Class
