/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.day;

public class UiCalendarController {

   private final UiCalendarDatesSelector selector;
   
   public UiCalendarController() {
      this.selector = new UiCalendarDatesSelector();
   }//End Constructor
   
   public UiCalendarDatesSelector selector() {
      return selector;
   }//End Method

}//End Class
