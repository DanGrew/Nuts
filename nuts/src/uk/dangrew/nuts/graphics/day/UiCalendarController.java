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

import uk.dangrew.nuts.day.DayPlan;
import uk.dangrew.nuts.day.DayPlanOperations;
import uk.dangrew.nuts.store.Database;
import uk.dangrew.nuts.template.Template;

public class UiCalendarController {

   private final Database database;
   
   private final DayPlanOperations operations;
   private final UiCalendarDatesSelector selector;
   
   public UiCalendarController( Database database ) {
      this.database = database;
      this.operations = new DayPlanOperations( database.templates(), database.dayPlans() );
      this.selector = new UiCalendarDatesSelector( this );
   }//End Constructor
   
   UiCalendarController( Database database, DayPlanOperations operations, UiCalendarDatesSelector selector ) {
      this.database = database;
      this.operations = operations;
      this.selector = selector;
   }//End Constructor
   
   public Database database() {
      return database;
   }//End Method
   
   public UiCalendarDatesSelector selector() {
      return selector;
   }//End Method

   public void applyTemplate( Template template ) {
      DayPlan selection = selector.selection().get();
      if ( selection == null ) {
         return;
      }
      
      operations.applyTemplate( selection, template );
   }//End Method
   
   public void addFromTemplate( Template template ) {
      DayPlan selection = selector.selection().get();
      if ( selection == null ) {
         return;
      }
      
      operations.addFromTemplate( selection, template );
   }//End Method
   
   public void saveAsTemplate( String name ) {
      DayPlan selection = selector.selection().get();
      if ( selection == null ) {
         return;
      }
      
      operations.saveAsTemplate( name, selection );
   }//End Method
   
   public void clearSelection(){
      DayPlan selection = selector.selection().get();
      if ( selection == null ) {
         return;
      }
      
      operations.clearDayPlan( selection );
   }//End Method

   public void copyToDay( LocalDate toDate ) {
      DayPlan selection = selector.selection().get();
      if ( selection == null ) {
         return;
      }
      
      operations.copyToDay( selection, toDate );
   }//End Method

}//End Class
