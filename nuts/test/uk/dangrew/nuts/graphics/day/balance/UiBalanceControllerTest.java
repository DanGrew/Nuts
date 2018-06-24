package uk.dangrew.nuts.graphics.day.balance;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.nutrients.NutritionalUnit;

public class UiBalanceControllerTest {

   private DayPlan plan;
   private UiBalanceController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      
      plan = new DayPlan( LocalDate.now() );
      systemUnderTest = new UiBalanceController();
   }//End Method
   
   @Test public void shouldSyncDayPlanCalories(){
      plan.nutrition().of( NutritionalUnit.Calories ).set( 567.9 );
      assertThat( plan.consumedCalories().get(), is( 0.0 ) );
      
      systemUnderTest.syncCalories( plan );
      assertThat( plan.consumedCalories().get(), is( 567.9 ) );
   }//End Method
   
   @Test public void shouldResetDayPlanBalance(){
      assertThat( plan.isBalanceReset().get(), is( false ) );
      
      systemUnderTest.resetBalance( plan );
      assertThat( plan.isBalanceReset().get(), is( true ) );
      
      systemUnderTest.resetBalance( plan );
      assertThat( plan.isBalanceReset().get(), is( false ) );
   }//End Method

}//End Class
