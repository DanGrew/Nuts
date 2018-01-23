package uk.dangrew.nuts.graphics.day.balance;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;

public class UiBalanceRowTest {

   private DayPlan plan;
   private UiBalanceRow systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      plan = new DayPlan( LocalDate.now() );
      systemUnderTest = new UiBalanceRow();
      
      systemUnderTest.setDayPlan( plan );
   }//End Method

   @Test public void shouldSyncConsumedCalories() {
      assertThat( systemUnderTest.consumedCaloriesField().getText(), is( "0.0" ) );
      plan.consumedCalories().set( 101.2 );
      assertThat( systemUnderTest.consumedCaloriesField().getText(), is( "101.2" ) );
      
      systemUnderTest.consumedCaloriesField().setText( "34.5" );
      assertThat( plan.consumedCalories().get(), is( 34.5 ) );
      
      systemUnderTest.consumedCaloriesField().setText( "abc" );
      assertThat( plan.consumedCalories().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldSyncAllowedCalories() {
      assertThat( systemUnderTest.allowedCaloriesField().getText(), is( "0.0" ) );
      plan.allowedCalories().set( 101.2 );
      assertThat( systemUnderTest.allowedCaloriesField().getText(), is( "101.2" ) );
      
      systemUnderTest.allowedCaloriesField().setText( "34.5" );
      assertThat( plan.allowedCalories().get(), is( 34.5 ) );
      
      systemUnderTest.allowedCaloriesField().setText( "abc" );
      assertThat( plan.allowedCalories().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldSyncBalanceCalories() {
      assertThat( systemUnderTest.calorieBalanceField().getText(), is( "0.0" ) );
      plan.calorieBalance().set( 101.2 );
      assertThat( systemUnderTest.calorieBalanceField().getText(), is( "101.2" ) );
      
      systemUnderTest.calorieBalanceField().setText( "34.5" );
      assertThat( plan.calorieBalance().get(), is( 34.5 ) );
      
      systemUnderTest.calorieBalanceField().setText( "abc" );
      assertThat( plan.calorieBalance().get(), is( 0.0 ) );
   }//End Method
   
   @Test public void shouldProvideWritePermissions() {
      assertThat( systemUnderTest.consumedCaloriesField().isEditable(), is( true ) );
      assertThat( systemUnderTest.allowedCaloriesField().isEditable(), is( true ) );
      assertThat( systemUnderTest.calorieBalanceField().isEditable(), is( false ) );
   }//End Method

   @Test public void shouldNotSyncPreviousDayPlan() {
      DayPlan alternate = new DayPlan( LocalDate.now().plusDays( 2 ) );
      systemUnderTest.setDayPlan( alternate );
      
      plan.consumedCalories().set( 101.2 );
      assertThat( systemUnderTest.consumedCaloriesField().getText(), is( "0.0" ) );
      systemUnderTest.consumedCaloriesField().setText( "34.5" );
      assertThat( plan.consumedCalories().get(), is( 101.2 ) );
      
      plan.allowedCalories().set( 101.2 );
      assertThat( systemUnderTest.allowedCaloriesField().getText(), is( "0.0" ) );
      systemUnderTest.allowedCaloriesField().setText( "34.5" );
      assertThat( plan.allowedCalories().get(), is( 101.2 ) );
      
      plan.calorieBalance().set( 101.2 );
      assertThat( systemUnderTest.calorieBalanceField().getText(), is( "0.0" ) );
      systemUnderTest.calorieBalanceField().setText( "34.5" );
      assertThat( plan.calorieBalance().get(), is( 101.2 ) );
      
      alternate.allowedCalories().set( 65.0 );
      assertThat( systemUnderTest.allowedCaloriesField().getText(), is( "65" ) );
   }//End Method
   
}//End Class
