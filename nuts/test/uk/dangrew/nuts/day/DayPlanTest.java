package uk.dangrew.nuts.day;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.food.FoodItem;
import uk.dangrew.nuts.food.FoodPortion;
import uk.dangrew.nuts.meal.Meal;
import uk.dangrew.nuts.template.Template;

public class DayPlanTest {

   private FoodItem food1;
   private FoodItem food2;
   private FoodPortion portion1;
   private FoodPortion portion2;
   
   private LocalDate date;
   private Template structure;
   private DayPlan systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      food1 = new FoodItem( "Food1" );
      food2 = new FoodItem( "Food2" );
      portion1 = new FoodPortion();
      portion2 = new FoodPortion();
      
      date = LocalDate.now();
//      systemUnderTest = new DayPlan( structure = new Template( "Plan" ) );
      systemUnderTest = new DayPlan( "Plan" );
   }//End Method
   
//   @Test public void shouldProvideProperties(){
//      assertThat( systemUnderTest.properties(), is( structure.properties() ) );
//      assertThat( systemUnderTest.portions(), is( structure.portions() ) );
//      assertThat( systemUnderTest.nutrition(), is( structure.nutrition() ) );
//      assertThat( systemUnderTest.foodAnalytics(), is( structure.foodAnalytics() ) );
//      assertThat( systemUnderTest.goalAnalytics(), is( structure.goalAnalytics() ) );
//   }//End Method

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
      assertThat( systemUnderTest.duplicate(), is( systemUnderTest ) );
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
   
//   @Test public void shouldNotExtendTemplateAndShouldNotBeCompatibleWithTemplateOperations(){
//      fail();
//   }//End Method
   
   @Test public void shouldRemoveFromPlan(){
      Meal meal1 = new Meal( "Meal1" );
      Meal meal2 = new Meal( "Meal2" );
      Meal meal3 = new Meal( "Meal3" );
      Meal meal4 = new Meal( "Meal4" );
      
      FoodItem item1 = new FoodItem( "Item1" );
      FoodItem item2 = new FoodItem( "Item2" );
      
      meal3.portions().add( new FoodPortion( meal4, 100.0 ) );
      meal2.portions().add( new FoodPortion( meal3, 100.0 ) );
      meal1.portions().add( new FoodPortion( meal2, 100.0 ) );
      
      meal3.portions().add( new FoodPortion( item1, 100.0 ) );
      meal1.portions().add( new FoodPortion( item2, 100.0 ) );
      
      systemUnderTest.portions().add( new FoodPortion( meal1, 0 ) );
      
      systemUnderTest.remove( meal3.portions().get( 0 ) );
      assertThat( meal3.portions(), hasSize( 1 ) );
      assertThat( meal3.portions().get( 0 ).food().get(), is( item1 ) );
      
      systemUnderTest.remove( meal3.portions().get( 0 ) );
      assertThat( meal3.portions(), hasSize( 0 ) );
      
      systemUnderTest.remove( systemUnderTest.portions().get( 0 ) );
      assertThat( systemUnderTest.portions(), hasSize( 0 ) );
   }//End Method
   
}//End Class
