/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.day;

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
      this.operations = new DayPlanOperations();
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

}//End Class
