package uk.dangrew.nuts.calendar;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class StockCalculatorTest {

   private CalendarPeriod period;
   private StockCalculator systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      
      systemUnderTest = new StockCalculator();
   }//End Method
   
   @Test public void shouldNotAllowMultipleAssociations(){
      
   }//End Method


}//End Class
