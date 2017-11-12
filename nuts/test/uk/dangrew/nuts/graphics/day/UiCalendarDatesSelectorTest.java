package uk.dangrew.nuts.graphics.day;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.utility.mouse.TestMouseEvent;
import uk.dangrew.nuts.day.DayPlan;

public class UiCalendarDatesSelectorTest {

   private DayPlan day1;
   private DayPlan day2;
   private UiDay uiDay1;
   private UiDay uiDay2;
   private UiCalendarDatesSelector systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      uiDay1 = spy( new UiDay( day1 = new DayPlan( LocalDate.now() ) ) );
      uiDay2 = spy( new UiDay( day2 = new DayPlan( LocalDate.now().plusDays( 2 ) ) ) );
      systemUnderTest = new UiCalendarDatesSelector();
      
      systemUnderTest.monitor( uiDay1 );
      systemUnderTest.monitor( uiDay2 );
   }//End Method

   @Test public void shouldSelectSingleDay() {
      uiDay1.getOnMouseClicked().handle( new TestMouseEvent() );
      verify( uiDay1 ).select();
      assertThat( systemUnderTest.selection().get(), is( day1 ) );
      
      uiDay2.getOnMouseClicked().handle( new TestMouseEvent() );
      verify( uiDay1 ).deselect();
      verify( uiDay2 ).select();
      assertThat( systemUnderTest.selection().get(), is( day2 ) );
   }//End Method
   
   @Test public void shouldDeselectWhenRemoved() {
      uiDay1.getOnMouseClicked().handle( new TestMouseEvent() );
      verify( uiDay1 ).select();
      
      systemUnderTest.remove( uiDay1 );
      verify( uiDay1 ).deselect();
      assertThat( systemUnderTest.selection().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldIgnoreWhenRemoved() {
      systemUnderTest.remove( uiDay1 );
      assertThat( uiDay1.getOnMouseClicked(), is( nullValue() ) );
      assertThat( systemUnderTest.selection().get(), is( nullValue() ) );
   }//End Method

}//End Class
