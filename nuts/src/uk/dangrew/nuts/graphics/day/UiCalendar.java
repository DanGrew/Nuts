/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.day;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.day.DayPlanStore;

public class UiCalendar extends BorderPane {

   private final JavaFxStyle styling;
   private final UiCalendarController controller;
   
   public UiCalendar( DayPlanStore dayPlans ) {
      this.styling = new JavaFxStyle();
      this.controller = new UiCalendarController();
      
      setBackground( styling.backgroundFor( Color.BLACK ) );
      
      setTop( new UiCalendarDays() );
      setCenter( new UiCalendarDates( dayPlans, controller ) );
//      setBottom( new UiCalendarControls() );
   }//End Constructor

   public UiCalendarController controller() {
      return controller;
   }//End Method
}//End Class
