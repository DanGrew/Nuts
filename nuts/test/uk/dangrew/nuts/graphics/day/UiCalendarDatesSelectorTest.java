package uk.dangrew.nuts.graphics.day;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.input.ContextMenuEvent;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.kode.utility.mouse.TestMouseEvent;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.store.Database;

public class UiCalendarDatesSelectorTest {

   private DayPlan day1;
   private DayPlan day2;
   private UiDay uiDay1;
   private UiDay uiDay2;
   
   @Mock private UiDayPlanContextMenu contextMenu;
   private UiCalendarDatesSelector systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      uiDay1 = spy( new UiDay( day1 = new DayPlan( LocalDate.now() ) ) );
      uiDay2 = spy( new UiDay( day2 = new DayPlan( LocalDate.now().plusDays( 2 ) ) ) );
      PlatformImpl.runAndWait( () -> {
         systemUnderTest = new UiCalendarDatesSelector( new UiCalendarController( new Database() ), contextMenu );
      } );
      
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
      assertThat( uiDay1.getOnContextMenuRequested(), is( nullValue() ) );
      assertThat( systemUnderTest.selection().get(), is( nullValue() ) );
   }//End Method
   
   @Test public void shouldAllowManualSelection(){
      assertThat( systemUnderTest.selection().get(), is( nullValue() ) );
      systemUnderTest.select( uiDay2 );
      assertThat( systemUnderTest.selection().get(), is( day2 ) );
   }//End Method

   @Test public void shouldUseFriendlyOpenerForContextMenu(){
      uiDay1.getOnContextMenuRequested().handle( new ContextMenuEvent( null, 0, 0, 0, 0, false, null ) );
      verify( contextMenu ).friendly_show( eq( uiDay1 ), anyDouble(), anyDouble() );
   }//End Method
}//End Class
