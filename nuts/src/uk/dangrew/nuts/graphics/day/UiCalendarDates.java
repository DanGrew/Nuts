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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
   
   private final Map< LocalDate, DayPlan > mappedPlans;
   private final UiCalendarController controller;
   
   public UiCalendarDates( DayPlanStore dayPlans, UiCalendarController controller ) {
      this.controller = controller;
      
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenColumns( this, DAYS_IN_WEEK );
      styling.configureConstraintsForEvenRows( this, ROWS_IN_CALENDAR );
      setBackground( new Background( new BackgroundFill( Color.BLACK, null, null ) ) );
      
      mappedPlans = dayPlans.objectList().stream()
         .collect( Collectors.toMap( DayPlan::date, Function.identity() ) );
      
      fillDays( LocalDate.now() );
   }//End Class

   private void fillDays( LocalDate centeredOnDate ){
      int day = centeredOnDate.getDayOfWeek().ordinal();
      LocalDate startDate = centeredOnDate.minusDays( day + 14 );
      
      int dayOffset = 0;
      int row = 0;
      int column = 0;
      while ( row < ROWS_IN_CALENDAR && column < DAYS_IN_WEEK ) {
         LocalDate nextDate = startDate.plusDays( dayOffset );
         createUiDay( nextDate, row, column );
         
         column++;
         dayOffset++;
         if ( column == DAYS_IN_WEEK ) {
            row++;
            column = 0;
         }
      }
   }//End Method
   
   private void createUiDay( LocalDate date, int row, int column ) {
      DayPlan dayPlan = mappedPlans.get( date );
      if ( dayPlan == null ) {
         return;
      }
      UiDay uiDay = new UiDay( dayPlan );
      add( uiDay, column, row );
      controller.selector().monitor( uiDay );
   }//End Method
   
}//End Class
