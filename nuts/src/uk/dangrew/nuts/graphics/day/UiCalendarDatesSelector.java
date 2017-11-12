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
import uk.dangrew.nuts.day.DayPlan;

public class UiCalendarDatesSelector {

   private final ObjectProperty< UiDay > selectedUi;
   private final ObjectProperty< DayPlan > selectedModel;
   
   public UiCalendarDatesSelector() {
      this.selectedUi = new SimpleObjectProperty<>( null );
      this.selectedModel = new SimpleObjectProperty<>( null );
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
      day.setOnMouseClicked( e -> selectedUi.set( day ) );
   }//End Method

   public void remove( UiDay day ) {
      day.setOnMouseClicked( null );
      if ( selectedUi.get() == day ) {
         selectedUi.set( null );
      }
   }//End Method
   
   public ReadOnlyObjectProperty< DayPlan > selection(){
      return selectedModel;
   }//End Method

}//End Class
