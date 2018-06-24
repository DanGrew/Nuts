package uk.dangrew.nuts.day;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class CalorieBalanceTest {

   private DayPlan plan1;
   private DayPlan plan2;
   
   private DayPlanStore store;
   private CalorieBalance systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      store = new DayPlanStore();
      systemUnderTest = new CalorieBalance( store );
      
      store.store( plan1 = new DayPlan( LocalDate.now() ) );
      plan1.nutrition().of( NutritionalUnit.Calories ).set( 0.0 );
      store.store( plan2 = new DayPlan( LocalDate.now().minusDays( 1 ) ) );
   }//End Method
   
   @Test public void shouldReactToConsumedChange() {
      plan2.consumedCalories().set( 2000.0 );
      
      assertThat( plan2.calorieBalance().get(), is( 2000.0 ) );
      assertThat( plan1.calorieBalance().get(), is( 2000.0 ) );
      
      plan1.consumedCalories().set( -100.0 );
      
      assertThat( plan2.calorieBalance().get(), is( 2000.0 ) );
      assertThat( plan1.calorieBalance().get(), is( 1900.0 ) );
      
      plan2.consumedCalories().set( 400.0 );
      
      assertThat( plan2.calorieBalance().get(), is( 400.0 ) );
      assertThat( plan1.calorieBalance().get(), is( 300.0 ) );
   }//End Method
   
   @Test public void shouldReactToAllowedChange() {
      plan2.allowedCalories().set( 2000.0 );
      
      assertThat( plan2.calorieBalance().get(), is( -2000.0 ) );
      assertThat( plan1.calorieBalance().get(), is( -2000.0 ) );
      
      plan1.allowedCalories().set( 1000.0 );
      
      assertThat( plan2.calorieBalance().get(), is( -2000.0 ) );
      assertThat( plan1.calorieBalance().get(), is( -3000.0 ) );
      
      plan2.allowedCalories().set( 500.0 );
      
      assertThat( plan2.calorieBalance().get(), is( -500.0 ) );
      assertThat( plan1.calorieBalance().get(), is( -1500.0 ) );
   }//End Method

   @Test public void shouldRecalculateBalanceWhenPlanRemoved(){
      plan2.allowedCalories().set( 2000.0 );
      plan1.allowedCalories().set( 1000.0 );
      
      assertThat( plan2.calorieBalance().get(), is( -2000.0 ) );
      assertThat( plan1.calorieBalance().get(), is( -3000.0 ) );
      
      store.removeConcept( plan2 );
      assertThat( plan2.calorieBalance().get(), is( -2000.0 ) );
      assertThat( plan1.calorieBalance().get(), is( -1000.0 ) );
   }//End Method
   
   @Test public void shouldResetBalanceOnResetDays(){
      plan2.allowedCalories().set( 2000.0 );
      plan1.allowedCalories().set( 1000.0 );
      
      assertThat( plan2.calorieBalance().get(), is( -2000.0 ) );
      assertThat( plan1.calorieBalance().get(), is( -3000.0 ) );
      
      plan2.isBalanceReset().set( true );
      assertThat( plan2.calorieBalance().get(), is( 0.0 ) );
      assertThat( plan1.calorieBalance().get(), is( -1000.0 ) );
   }//End Method
   
}//End Class
