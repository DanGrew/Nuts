package uk.dangrew.nuts.day;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.nuts.progress.SystemDateRange;

public class DayPlanStoreTest {

   private DayPlan plan;
   private DayPlanStore systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      plan = new DayPlan( LocalDate.now().plusDays( 1000 ) );
      systemUnderTest = new DayPlanStore();
   }//End Method

   @Test public void shouldStoreByDate() {
      assertThat( systemUnderTest.get( plan.date() ), is( nullValue() ) );
      systemUnderTest.store( plan );
      assertThat( systemUnderTest.get( plan.date() ), is( plan ) );
   }//End Method
   
   @Test public void shouldNotSupportIdFunctions(){
      try {
         systemUnderTest.createFood( "anything" );
         fail( "Should exception" );
      } catch ( UnsupportedOperationException e ) {
         //continue;
      }
      
      try {
         systemUnderTest.createFood( "anything", "else" );
         fail( "Should exception" );
      } catch ( UnsupportedOperationException e ) {
         //continue;
      }
      
      try {
         systemUnderTest.get( "anything" );
         fail( "Should exception" );
      } catch ( UnsupportedOperationException e ) {
         //continue;
      }
   }//End Method
   
   @Test public void shouldRemoveExisting() {
      systemUnderTest.store( plan );
      systemUnderTest.removeFood( plan );
      assertThat( systemUnderTest.get( plan.date() ), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldInitialisePlansForDateRange(){
      for ( LocalDate date : new SystemDateRange().get() ) {
         assertThat( systemUnderTest.get( date ), is( notNullValue() ) );
         assertThat( systemUnderTest.get( date ).date(), is( date ) );
      }
   }//End Method
   
}//End Class
