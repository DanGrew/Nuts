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

import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import uk.dangrew.kode.javafx.contextmenu.ContextMenuWithCancel;
import uk.dangrew.nuts.graphics.table.FoodOptions;
import uk.dangrew.nuts.template.Template;

public class UiDayPlanContextMenu extends ContextMenuWithCancel {

   static final String APPLY_TEMPLATE_TEXT = "Apply Template";
   static final String SAVE_AS_TEMPLATE_TEXT = "Save As Template";
   
   private final UiCalendarController controller;
   private final UiTemplateSelectionPopup uiTemplateSelectionPopup;
   
   private final MenuItem applyTemplateMenu;
   
   public UiDayPlanContextMenu( UiCalendarController controller ) {
      this( 
               controller, 
               new UiTemplateSelectionPopup( new FoodOptions<>( 
                        controller.database().templates() 
               ) ) 
      );
   }//End Constructor
   
   public UiDayPlanContextMenu( UiCalendarController controller, UiTemplateSelectionPopup uiTemplateSelectionPopup ) {
      this.controller = controller;
      this.uiTemplateSelectionPopup = uiTemplateSelectionPopup;
      this.applyTemplateMenu = new MenuItem( APPLY_TEMPLATE_TEXT );
      
      super.initialise();
   }//End Constructor
   
   @Override protected void applyControls() {
      menuForApplyTemplate();
   }//End Method
   
   private void menuForApplyTemplate(){
      applyTemplateMenu.setOnAction( event -> applyTemplate() );
      
      getItems().addAll( 
               new SeparatorMenuItem(),
               applyTemplateMenu
      );
   }//End Method
   
   private void applyTemplate(){
      PlatformImpl.runAndWait( () -> {
         Optional< Template > result = uiTemplateSelectionPopup.friendly_showAndWait();
         result.ifPresent( controller::applyTemplate );
      } );
   }//End Method
   
   MenuItem applyTemplateMenu(){
      return applyTemplateMenu;
   }//End Method

}//End Method
