/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.day;

import java.time.LocalDate;
import uk.dangrew.nuts.template.Template;

/**
 * The {@link DayPlan} provides a specific {@link Template} for a day of the year.
 */
public class DayPlan extends Template {

   private LocalDate date;
   
   public DayPlan( LocalDate date ) {
      this( date.toString() );
      setDate( date );
   }//End Constructor
   
   public DayPlan( String name ) {
      super( name );
   }//End Constructor
   
   public DayPlan( String id, String name ) {
      super( id, name );
   }//End Constructor

   public void setDate( LocalDate date ) {
      if ( this.date != null ) {
         throw new IllegalStateException( "Date can only be set once." );
      }
      this.date = date;
   }//End Method
   
   public LocalDate date() {
      return date;
   }//End Method

}//End Class
