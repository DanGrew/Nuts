package uk.dangrew.nuts.calendar;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;

public class CalendarPeriodTest {

   private CalendarPeriod systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      systemUnderTest = new CalendarPeriod();
   }//End Method

   @Test public void shouldProvideDaysInPeriod() {
      assertThat( systemUnderTest.modifiableDays(), is( instanceOf( ObservableList.class ) ) );
      assertThat( systemUnderTest.days(), is( instanceOf( PrivatelyModifiableObservableListImpl.class ) ) );
   }//End Method

}//End Class
