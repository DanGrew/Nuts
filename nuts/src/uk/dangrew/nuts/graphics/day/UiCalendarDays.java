/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.day;

import java.time.DayOfWeek;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public class UiCalendarDays extends GridPane {

   private static final int DAYS_IN_WEEK = 7;
   
   public UiCalendarDays() {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenColumns( this, DAYS_IN_WEEK );
      styling.configureConstraintsForEvenRows( this, 1 );
      
      for ( int i = 0; i < DAYS_IN_WEEK; i++ ) {
         add( styling.createBoldLabel( DayOfWeek.of( i + 1 ).name(), 20, Color.WHITE ), i, 0 );
      }
   }//End Class
   
}//End Class
