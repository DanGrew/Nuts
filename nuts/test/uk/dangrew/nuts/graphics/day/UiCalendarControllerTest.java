package uk.dangrew.nuts.graphics.day;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class UiCalendarControllerTest {

   private UiCalendarController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new UiCalendarController();
   }//End Method

   @Test public void shouldProvideSelector() {
      assertThat( systemUnderTest.selector(), is( notNullValue() ) );
   }//End Method

}//End Class
