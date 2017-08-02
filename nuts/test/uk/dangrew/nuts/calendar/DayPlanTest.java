package uk.dangrew.nuts.calendar;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.food.FoodItem;

public class DayPlanTest {

   private LocalDate planDate;
   private DayPlan systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      planDate = LocalDate.now();
      systemUnderTest = new DayPlan( planDate );
   }//End Method

   @Test public void shouldProvideDate() {
      assertThat( systemUnderTest.date(), is( planDate ) );
   }//End Method
   
   @Test public void shouldProvidePlan() {
      assertThat( systemUnderTest.plan(), is( notNullValue() ) );
      assertThat( systemUnderTest.plan().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideStock() {
      FoodItem food = new FoodItem( "anything" );
      assertThat( systemUnderTest.stockFor( food ), is( nullValue() ) );
      systemUnderTest.stock( food );
      assertThat( systemUnderTest.stockFor( food ), is( notNullValue() ) );
      assertThat( systemUnderTest.stockFor( food ).get(), is( 0.0 ) );
      systemUnderTest.remove( food );
      assertThat( systemUnderTest.stockFor( food ), is( nullValue() ) );
      
      systemUnderTest.stock( food );
      ObjectProperty< Double > property = systemUnderTest.stockFor( food );
      systemUnderTest.stock( food );
      assertThat( systemUnderTest.stockFor( food ), is( property ) );
   }//End Method

}//End Class
