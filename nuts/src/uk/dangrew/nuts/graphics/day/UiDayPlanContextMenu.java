/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.day;

import java.util.Optional;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import uk.dangrew.kode.javafx.contextmenu.ContextMenuWithCancel;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.template.Template;

public class UiDayPlanContextMenu extends ContextMenuWithCancel {

   static final String APPLY_TEMPLATE_TEXT = "Apply Template";
   static final String ADD_FROM_TEMPLATE_TEXT = "Add From Template";
   static final String SAVE_AS_TEMPLATE_TEXT = "Save As Template";
   static final String CLEAR_TEXT = "Clear";
   
   static final String APPLY_TEMPLATE_DESCRIPTION = 
            "You can apply a template to the selected day plan. Doing this will \n"
            + "clear the current contents of the day and add all food from the template \n"
            + "to the day. If you'd rather append to the day, choose 'Add From Template'.";
   static final String ADD_FROM_TEMPLATE_DESCRIPTION = 
            "You can add food from a template to the selected day plan. Doing this will \n"
            + "not change the current contents of the day and add all food from the template \n"
            + "to the day. If you'd rather replace the contents of day, choose 'Apply Template'.";
   static final String SAVE_AS_TEMPLATE_DESCRIPTION = 
            "You can save the food in the selected day as a template for use with other days. \n"
            + "To do this, enter a name for the template and it will be saved with the others.";
   static final String CLEAR_DESCRIPTION =
            "Clearing the day will remove all food from the plan. ";
   
   private final UiCalendarController controller;
   private final UiTemplateSelectionDialog uiTemplateSelectionDialog;
   private final UiTemplateNameInputDialog uiTemplateNameInputDialog;
   private final UiConfirmAlert confirmAlert;
   
   private final MenuItem applyTemplateMenu;
   private final MenuItem addFromTemplateMenu;
   private final MenuItem saveAsTemplateMenu;
   private final MenuItem clearMenu;
   
   public UiDayPlanContextMenu( UiCalendarController controller ) {
      this( 
               controller, 
               new UiTemplateSelectionDialog( 
                        new ConceptOptionsImpl<>( controller.database().templates() ) 
               ),
               new UiTemplateNameInputDialog(),
               new UiConfirmAlert()
      );
   }//End Constructor
   
   public UiDayPlanContextMenu( 
            UiCalendarController controller, 
            UiTemplateSelectionDialog uiTemplateSelectionDialog,
            UiTemplateNameInputDialog uiTemplateNameInputDialog,
            UiConfirmAlert confirmAlert
   ) {
      this.controller = controller;
      this.uiTemplateSelectionDialog = uiTemplateSelectionDialog;
      this.uiTemplateNameInputDialog = uiTemplateNameInputDialog;
      this.confirmAlert = confirmAlert;
      
      this.applyTemplateMenu = new MenuItem( APPLY_TEMPLATE_TEXT );
      this.addFromTemplateMenu = new MenuItem( ADD_FROM_TEMPLATE_TEXT );
      this.saveAsTemplateMenu = new MenuItem( SAVE_AS_TEMPLATE_TEXT );
      this.clearMenu = new MenuItem( CLEAR_TEXT );
      
      super.initialise();
   }//End Constructor
   
   @Override protected void applyControls() {
      applyTemplateMenu.setOnAction( event -> applyTemplate() );
      addFromTemplateMenu.setOnAction( event -> addFromTemplate() );
      saveAsTemplateMenu.setOnAction( event -> saveAsTemplate() );
      clearMenu.setOnAction( event -> clear() );
      
      getItems().addAll( 
               new MenuItem( "Templates" ),
               new SeparatorMenuItem(),
               applyTemplateMenu,
               addFromTemplateMenu,
               saveAsTemplateMenu, 
               clearMenu
      );
   }//End Method
   
   private void applyTemplate(){
      PlatformImpl.runAndWait( () -> {
         uiTemplateSelectionDialog.friendly_setHeaderText( APPLY_TEMPLATE_DESCRIPTION );
         Optional< Template > result = uiTemplateSelectionDialog.friendly_showAndWait();
         result.ifPresent( controller::applyTemplate );
      } );
   }//End Method
   
   private void addFromTemplate(){
      PlatformImpl.runAndWait( () -> {
         uiTemplateSelectionDialog.friendly_setHeaderText( ADD_FROM_TEMPLATE_DESCRIPTION );
         Optional< Template > result = uiTemplateSelectionDialog.friendly_showAndWait();
         result.ifPresent( controller::addFromTemplate );
      } );
   }//End Method
   
   private void saveAsTemplate(){
      PlatformImpl.runAndWait( () -> {
         uiTemplateNameInputDialog.friendly_setHeaderText( SAVE_AS_TEMPLATE_DESCRIPTION );
         Optional< String > result = uiTemplateNameInputDialog.friendly_showAndWait();
         result.ifPresent( controller::saveAsTemplate );
      } );
   }//End Method
   
   private void clear(){
      PlatformImpl.runAndWait( () -> {
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

   MenuItem saveAsTemplateMenu(){
      return saveAsTemplateMenu;
   }//End Method
   
   MenuItem clearMenu(){
      return clearMenu;
   }//End Method
}//End Method
