package uk.dangrew.nuts.graphics.day;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanOperations;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiCalendarControllerTest {

   private static final int OTHER_DAY_OFFSET = 13;
   
   private UiDay uiDay;
   private DayPlan day;
   private UiCalendarDatesSelector selector;
   
   private Database database;
   
   @Mock private DayPlanOperations operations;
   private UiCalendarController systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      
      database = new Database();
      PlatformImpl.runAndWait( () -> selector = new UiCalendarDatesSelector( new UiCalendarController( database ) ) );
      uiDay = new UiDay( day = new DayPlan( LocalDate.now() ) );
      
      
      systemUnderTest = new UiCalendarController( database, operations, selector );
   }//End Method

   @Test public void shouldProvideSelector() {
      assertThat( systemUnderTest.selector(), is( notNullValue() ) );
   }//End Method
   
   @Test public void shouldProvideDatabase(){
      assertThat( systemUnderTest.database(), is( database ) );
   }//End Method
   
   @Test public void shouldApplyTemplateToSelection(){
      Template template = new Template( "Template" );
      
      selector.select( uiDay );
      assertThat( selector.selection().get(), is( day ) );
      systemUnderTest.applyTemplate( template );
      verify( operations ).applyTemplate( day, template );
   }//End Method
   
   @Test public void shouldIgnoreApplyTemplateWhenNoSelection(){
      Template template = new Template( "Template" );
      
      assertThat( selector.selection().get(), is( nullValue() ) );
      systemUnderTest.applyTemplate( template );
      verify( operations, never() ).applyTemplate( Mockito.any(), Mockito.any() );
   }//End Method
   
   @Test public void shouldAddFromTemplateToSelection(){
      Template template = new Template( "Template" );
      
      selector.select( uiDay );
      assertThat( selector.selection().get(), is( day ) );
      systemUnderTest.addFromTemplate( template );
      verify( operations ).addFromTemplate( day, template );
   }//End Method
   
   @Test public void shouldIgnoreAddFromTemplateWhenNoSelection(){
      Template template = new Template( "Template" );
      
      assertThat( selector.selection().get(), is( nullValue() ) );
      systemUnderTest.addFromTemplate( template );
      verify( operations, never() ).addFromTemplate( Mockito.any(), Mockito.any() );
   }//End Method
   
   @Test public void shouldClearTemplateSelection(){
      selector.select( uiDay );
      assertThat( selector.selection().get(), is( day ) );
      systemUnderTest.clearSelection();
      verify( operations ).clearDayPlan( day );
   }//End Method
   
   @Test public void shouldIgnoreClearTemplateWhenNoSelection(){
      assertThat( selector.selection().get(), is( nullValue() ) );
      systemUnderTest.clearSelection();
      verify( operations, never() ).clearDayPlan( Mockito.any() );
   }//End Method
   
   @Test public void shouldCopyTemplateToDay(){
      selector.select( uiDay );
      systemUnderTest.copyToDay( LocalDate.now().plusDays( OTHER_DAY_OFFSET ) );
      verify( operations ).copyToDay( day, LocalDate.now().plusDays( OTHER_DAY_OFFSET ) );
   }//End Method
   
   @Test public void shouldIgnoreCopyTemplateToDayWhenNoSelection(){
      assertThat( selector.selection().get(), is( nullValue() ) );
      systemUnderTest.copyToDay( LocalDate.now().plusDays( OTHER_DAY_OFFSET ) );
      verify( operations, never() ).copyToDay( Mockito.any(), Mockito.any() );
   }//End Method

}//End Class
