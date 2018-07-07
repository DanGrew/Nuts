package uk.dangrew.nuts.graphics.day;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.application.PlatformImpl;

import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiDayPlanContextMenuTest {

   private Template selection;
   private LocalDate dateSelection;
   private UiCalendarController controller;
   @Mock private UiTemplateSelectionDialog templateSelection;
   @Mock private UiDateSelectionDialog dateSelectionDialog;
   @Mock private UiConfirmAlert confirmAlert;
   private UiDayPlanContextMenu systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      selection = new Template( "Template" );
      dateSelection = LocalDate.now().plusDays( 100 );
      
      PlatformImpl.runAndWait( () -> {
         controller = spy( new UiCalendarController( new Database() ) );
         systemUnderTest = new UiDayPlanContextMenu( controller, templateSelection, dateSelectionDialog, confirmAlert );  
      } );
   }//End Method

   @Test public void shouldChooseTemplateToApplyAndSendToController() {
      when( templateSelection.friendly_showAndWait() ).thenReturn( Optional.of( selection ) );
      systemUnderTest.applyTemplateMenu().getOnAction().handle( new ActionEvent() );
      verify( controller ).applyTemplate( selection );
      verify( templateSelection ).friendly_setHeaderText( UiDayPlanContextMenu.APPLY_TEMPLATE_DESCRIPTION );
   }//End Method
   
   @Test public void shouldCancelChooseTemplateToApplyAndNotSendToController() {
      when( templateSelection.friendly_showAndWait() ).thenReturn( Optional.empty() );
      systemUnderTest.applyTemplateMenu().getOnAction().handle( new ActionEvent() );
      verify( controller, never() ).applyTemplate( Mockito.any() );
   }//End Method
   
   @Test public void shouldAddFromTemplate(){
      when( templateSelection.friendly_showAndWait() ).thenReturn( Optional.of( selection ) );
      systemUnderTest.addFromTemplateMenu().getOnAction().handle( new ActionEvent() );
      verify( controller ).addFromTemplate( selection );
      verify( templateSelection ).friendly_setHeaderText( UiDayPlanContextMenu.ADD_FROM_TEMPLATE_DESCRIPTION );
   }//End Method
   
   @Test public void shouldNotAddFromTemplate(){
      when( templateSelection.friendly_showAndWait() ).thenReturn( Optional.empty() );
      systemUnderTest.addFromTemplateMenu().getOnAction().handle( new ActionEvent() );
      verify( controller, never() ).addFromTemplate( Mockito.any() );
   }//End Method
   
   @Test public void shouldClearDay(){
      when( confirmAlert.friendly_showAndWait() ).thenReturn( Optional.of( ButtonType.OK ) );
      systemUnderTest.clearMenu().getOnAction().handle( new ActionEvent() );
      verify( controller ).clearSelection();
      verify( confirmAlert ).friendly_setHeaderText( UiDayPlanContextMenu.CLEAR_DESCRIPTION );
   }//End Method
   
   @Test public void shouldNotClearDay(){
      when( confirmAlert.friendly_showAndWait() ).thenReturn( Optional.empty() ).thenReturn( Optional.of( ButtonType.CANCEL ) );
      systemUnderTest.clearMenu().getOnAction().handle( new ActionEvent() );
      systemUnderTest.clearMenu().getOnAction().handle( new ActionEvent() );
      verify( controller, never() ).clearSelection();
   }//End Method
   
   @Test public void shouldChooseDateToCopyAndSendToController() {
      when( dateSelectionDialog.friendly_showAndWait() ).thenReturn( Optional.of( dateSelection ) );
      systemUnderTest.copyToDateMenu().getOnAction().handle( new ActionEvent() );
      verify( controller ).copyToDay( dateSelection );
      verify( dateSelectionDialog ).friendly_setHeaderText( UiDayPlanContextMenu.COPY_TO_DATE_DESCRIPTION );
   }//End Method
   
   @Test public void shouldCancelChooseDateToCopyAndNotSendToController() {
      when( dateSelectionDialog.friendly_showAndWait() ).thenReturn( Optional.empty() );
      systemUnderTest.copyToDateMenu().getOnAction().handle( new ActionEvent() );
      verify( controller, never() ).copyToDay( Mockito.any() );
   }//End Method

}//End Class
