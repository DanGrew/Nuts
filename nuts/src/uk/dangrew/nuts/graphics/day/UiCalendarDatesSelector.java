/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.day;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.kode.friendly.javafx.FriendlyMenuOpener;
import uk.dangrew.nuts.day.DayPlan;

public class UiCalendarDatesSelector {

   private final ObjectProperty< UiDay > selectedUi;
   private final ObjectProperty< DayPlan > selectedModel;
   private final UiDayPlanContextMenu contextMenu;
   
   public UiCalendarDatesSelector( UiCalendarController controller ) {
      this( controller, new UiDayPlanContextMenu( controller ) );
   }//End Constructor
   
   UiCalendarDatesSelector( UiCalendarController controller, UiDayPlanContextMenu contextMenu ) {
      this.selectedUi = new SimpleObjectProperty<>( null );
      this.selectedModel = new SimpleObjectProperty<>( null );
      this.contextMenu = contextMenu;
      
      this.selectedUi.addListener( ( s, o, n ) -> {
         if ( o != null ) {
            o.deselect();
         }
         if ( n != null ) {
            selectedModel.set( n.association() );
            n.select();
         } else {
            selectedModel.set( null );
         }
      } );
   }//End Constructor
   
   public void monitor( UiDay day ) {
      day.setOnMouseClicked( e -> select( day ) );
      day.setOnContextMenuRequested( new FriendlyMenuOpener( day, contextMenu ) );
   }//End Method

   public void remove( UiDay day ) {
      day.setOnMouseClicked( null );
      day.setOnContextMenuRequested( null );
      if ( selectedUi.get() == day ) {
         select( null );
      }
   }//End Method
   
   public ReadOnlyObjectProperty< DayPlan > selection(){
      return selectedModel;
   }//End Method

   public void select( UiDay uiDay ) {
      selectedUi.set( uiDay );
   }//End Method

}//End Class
