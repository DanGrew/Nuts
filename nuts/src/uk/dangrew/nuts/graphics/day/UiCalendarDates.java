/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.day;

import java.time.LocalDate;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanStore;

public class UiCalendarDates extends GridPane {

   private static final int DAYS_IN_WEEK = 7;
   private static final int ROWS_IN_CALENDAR = 5;
   
   private final DayPlanStore dayPlans;
   private final UiCalendarController controller;
   
   public UiCalendarDates( DayPlanStore dayPlans, UiCalendarController controller ) {
      this.controller = controller;
      this.dayPlans = dayPlans;
      
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenColumns( this, DAYS_IN_WEEK );
      styling.configureConstraintsForEvenRows( this, ROWS_IN_CALENDAR );
      setBackground( new Background( new BackgroundFill( Color.BLACK, null, null ) ) );
      
      fillDays( LocalDate.now() );
   }//End Class

   private void fillDays( LocalDate centeredOnDate ){
      int day = centeredOnDate.getDayOfWeek().getValue();
      
      int dayOffset = 0;
      int row = 2;
      int column = day - 1;
      while ( row < ROWS_IN_CALENDAR && column < DAYS_IN_WEEK ) {
         LocalDate nextDate = centeredOnDate.plusDays( dayOffset );
         createUiDay( nextDate, row, column );
         
         column++;
         dayOffset++;
         if ( column == DAYS_IN_WEEK ) {
            row++;
            column = 0;
         }
      }
      
      dayOffset = 1;
      row = 2;
      column = day - 2;
      while ( row >= 0 && column >= 0 ) {
         LocalDate nextDate = centeredOnDate.minusDays( dayOffset );
         createUiDay( nextDate, row, column );
         
         column--;
         dayOffset++;
         if ( column < 0 ) {
            row--;
            column = 6;
         }
      }
   }//End Method
   
   private void createUiDay( LocalDate date, int row, int column ) {
      DayPlan dayPlan = dayPlans.get( date );
      if ( dayPlan == null ) {
         return;
      }
      UiDay uiDay = new UiDay( dayPlan );
      add( uiDay, column, row );
      controller.selector().monitor( uiDay );
   }//End Method
   
}//End Class
