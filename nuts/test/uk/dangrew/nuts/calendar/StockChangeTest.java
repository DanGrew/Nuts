package uk.dangrew.nuts.calendar;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class StockChangeTest {

   private StockChange systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new StockChange();
   }//End Method

   @Test public void shouldProvideDateOfChange() {
      assertThat( systemUnderTest.date(), is( notNullValue() ) );
      assertThat( systemUnderTest.date().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideFoodItemStocked() {
      assertThat( systemUnderTest.foodItem(), is( notNullValue() ) );
      assertThat( systemUnderTest.foodItem().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldProvideStockChange() {
      assertThat( systemUnderTest.stockChange(), is( notNullValue() ) );
      assertThat( systemUnderTest.stockChange().get(), is( 0.0 ) );
   }//End Method

}//End Class
