package uk.dangrew.nuts.graphics.day;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import javafx.event.ActionEvent;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiDayPlanContextMenuTest {

   private Template selection;
   private UiCalendarController controller;
   @Mock private UiTemplateSelectionPopup templateSelection;
   private UiDayPlanContextMenu systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      selection = new Template( "Template" );
      
      PlatformImpl.runAndWait( () -> {
         controller = spy( new UiCalendarController( new Database() ) );
         systemUnderTest = new UiDayPlanContextMenu( controller, templateSelection );  
      } );
   }//End Method

   @Test public void shouldChooseTemplateToApplyAndSendToController() {
      when( templateSelection.friendly_showAndWait() ).thenReturn( Optional.of( selection ) );
      systemUnderTest.applyTemplateMenu().getOnAction().handle( new ActionEvent() );
      verify( controller ).applyTemplate( selection );
   }//End Method
   
   @Test public void shouldCancelChooseTemplateToApplyAndNotSendToController() {
      when( templateSelection.friendly_showAndWait() ).thenReturn( Optional.empty() );
      systemUnderTest.applyTemplateMenu().getOnAction().handle( new ActionEvent() );
      verify( controller, never() ).applyTemplate( Mockito.any() );
   }//End Method
   
   @Test public void shouldSaveAsTemplateAndSendToController() {
      //not implemented yet
   }//End Method
   
   @Test public void shouldCancelSaveAsTemplateAndNotSendToController() {
      //not implemented yet
   }//End Method
   
   @Test public void shouldClearDay(){
      //not implemented yet
   }//End Method
   
   @Test public void shouldNotClearDay(){
      //not implemented yet
   }//End Method

}//End Class
