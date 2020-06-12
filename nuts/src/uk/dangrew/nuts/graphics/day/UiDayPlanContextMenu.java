/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.day;

import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import uk.dangrew.kode.javafx.contextmenu.ContextMenuWithCancel;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.kode.javafx.table.options.ConceptOptionsImpl;
import uk.dangrew.nuts.progress.weight.SystemDateRange;
import uk.dangrew.nuts.template.Template;

import java.time.LocalDate;
import java.util.Optional;

public class UiDayPlanContextMenu extends ContextMenuWithCancel {

   static final String APPLY_TEMPLATE_TEXT = "Apply Template";
   static final String ADD_FROM_TEMPLATE_TEXT = "Add From Template";
   static final String SAVE_AS_TEMPLATE_TEXT = "Save As Template";
   static final String COPY_TO_DATE_TEXT = "Copy To Date";
   static final String CLEAR_TEXT = "Clear";
   
   static final String APPLY_TEMPLATE_DESCRIPTION = 
            "You can apply a template to the selected day plan. Doing this will \n"
            + "clear the current contents of the day and add all food from the template \n"
            + "to the day. If you'd rather append to the day, choose 'Add From Template'.";
   static final String ADD_FROM_TEMPLATE_DESCRIPTION = 
            "You can add food from a template to the selected day plan. Doing this will \n"
            + "not change the current contents of the day and add all food from the template \n"
            + "to the day. If you'd rather replace the contents of day, choose 'Apply Template'.";
   static final String COPY_TO_DATE_DESCRIPTION = 
            "You can copy the food in the selected day to another day without saving as a template. \n"
            + "To do this, select the date to copy to and the food items will be added to that date.";
   static final String CLEAR_DESCRIPTION =
            "Clearing the day will remove all food from the plan. ";
   
   private final UiCalendarController controller;
   private final UiTemplateSelectionDialog uiTemplateSelectionDialog;
   private final UiDateSelectionDialog uiDateSelectionDialog;
   private final UiConfirmAlert confirmAlert;
   
   private final MenuItem applyTemplateMenu;
   private final MenuItem addFromTemplateMenu;
   private final MenuItem copyToDateMenu;
   private final MenuItem clearMenu;
   
   public UiDayPlanContextMenu( UiCalendarController controller ) {
      this( 
               controller, 
               new UiTemplateSelectionDialog( 
                        new ConceptOptionsImpl<>( controller.database().templates() )
               ),
               new UiDateSelectionDialog( new SystemDateRange().get() ),
               new UiConfirmAlert()
      );
   }//End Constructor
   
   public UiDayPlanContextMenu( 
            UiCalendarController controller, 
            UiTemplateSelectionDialog uiTemplateSelectionDialog,
            UiDateSelectionDialog uiDateSelectionDialog,
            UiConfirmAlert confirmAlert
   ) {
      this.controller = controller;
      this.uiTemplateSelectionDialog = uiTemplateSelectionDialog;
      this.uiDateSelectionDialog = uiDateSelectionDialog;
      this.confirmAlert = confirmAlert;
      
      this.applyTemplateMenu = new MenuItem( APPLY_TEMPLATE_TEXT );
      this.addFromTemplateMenu = new MenuItem( ADD_FROM_TEMPLATE_TEXT );
      this.copyToDateMenu = new MenuItem( COPY_TO_DATE_TEXT );
      this.clearMenu = new MenuItem( CLEAR_TEXT );
      
      super.initialise();
   }//End Constructor
   
   @Override protected void applyControls() {
      applyTemplateMenu.setOnAction( event -> applyTemplate() );
      addFromTemplateMenu.setOnAction( event -> addFromTemplate() );
      copyToDateMenu.setOnAction( event -> copyToDate() );
      clearMenu.setOnAction( event -> clear() );
      
      getItems().addAll( 
               new MenuItem( "Templates" ),
               new SeparatorMenuItem(),
               applyTemplateMenu,
               addFromTemplateMenu,
               copyToDateMenu,
               clearMenu
      );
   }//End Method
   
   private void applyTemplate(){
      JavaFxThreading.runAndWait( () -> {
         uiTemplateSelectionDialog.friendly_setHeaderText( APPLY_TEMPLATE_DESCRIPTION );
         Optional< Template > result = uiTemplateSelectionDialog.friendly_showAndWait();
         result.ifPresent( controller::applyTemplate );
      } );
   }//End Method
   
   private void addFromTemplate(){
      JavaFxThreading.runAndWait( () -> {
         uiTemplateSelectionDialog.friendly_setHeaderText( ADD_FROM_TEMPLATE_DESCRIPTION );
         Optional< Template > result = uiTemplateSelectionDialog.friendly_showAndWait();
         result.ifPresent( controller::addFromTemplate );
      } );
   }//End Method
   
   private void copyToDate(){
      JavaFxThreading.runAndWait( () -> {
         uiDateSelectionDialog.friendly_setHeaderText( COPY_TO_DATE_DESCRIPTION );
         Optional< LocalDate > result = uiDateSelectionDialog.friendly_showAndWait();
         result.ifPresent( controller::copyToDay );
      } );
   }//End Method
   
   private void clear(){
      JavaFxThreading.runAndWait( () -> {
         confirmAlert.friendly_setHeaderText( CLEAR_DESCRIPTION );
         Optional< ButtonType > result = confirmAlert.friendly_showAndWait();
         result.filter( t -> t == ButtonType.OK ).ifPresent( t -> controller.clearSelection() );
      } );
   }//End Method
   
   MenuItem applyTemplateMenu(){
      return applyTemplateMenu;
   }//End Method
   
   MenuItem addFromTemplateMenu(){
      return addFromTemplateMenu;
   }//End Method

   MenuItem clearMenu(){
      return clearMenu;
   }//End Method

   MenuItem copyToDateMenu() {
      return copyToDateMenu;
   }//End Method
}//End Method
