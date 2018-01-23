package uk.dangrew.nuts.graphics.day.balance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanStore;

public class UiBalanceSummary extends BorderPane {

   static final int NUMBER_OF_DAYS = 20;
   static final int FOCUS_ROW = 10;
   
   private final DayPlanStore dayPlans;
   
   private final GridPane content;
   private final ScrollPane scroller;
   private final UiBalanceController controller;
   
   private final List< UiBalanceRow > rows;
   
   public UiBalanceSummary( DayPlanStore dayPlans ) {
      this.content = new GridPane();
      this.scroller = new ScrollPane( content );
      this.scroller.setFitToHeight( true );
      this.scroller.setFitToWidth( true );
      this.setCenter( scroller );
      
      this.controller = new UiBalanceController();
      this.dayPlans = dayPlans;
      this.rows = new ArrayList<>();

      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenColumns( content, 1 );
      styling.configureConstraintsForEvenRows( content, NUMBER_OF_DAYS + 1 );
      
      content.add( new UiBalanceHeader(), 0, 0 );
      for ( int i = 0; i < NUMBER_OF_DAYS; i++ ) {
         UiBalanceRow row = new UiBalanceRow( controller );
         rows.add( row );
         content.add( row, 0, i + 1 );   
      }
      updateRows();
      
      content.setBackground( styling.backgroundFor( Color.WHITE ) );
   }//End Constructor
   
   private void updateRows(){
      Map< LocalDate, DayPlan > mappedPlans = dayPlans.objectList().stream()
               .collect( Collectors.toMap( DayPlan::date, Function.identity() ) );
      
      for ( int i = 0; i < NUMBER_OF_DAYS; i++ ) {
         LocalDate date = LocalDate.now().plusDays( i - FOCUS_ROW );
         DayPlan plan = mappedPlans.get( date );
         
         UiBalanceRow row = rows.get( i );
         row.setDayPlan( plan );
      }
   }//End Method

}//End Class
