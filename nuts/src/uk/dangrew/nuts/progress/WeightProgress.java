/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.progress;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * {@link WeightProgress} provides the overall progress of {@link WeightRecording}s.
 */
public class WeightProgress {

   private final SystemDateRange dateRange;
   private final List< WeightRecording > records;
   
   /**
    * Constructs a new {@link WeightProgress}.
    */
   public WeightProgress() {
      this.records = new ArrayList<>();
      this.dateRange = new SystemDateRange();
      
      Queue< WeightRecording > runningAverageWeighIns = new LinkedList<>();
      
      for ( LocalDate date : dateRange.get() ) {
         WeightRecording recording = addRecord( date, runningAverageWeighIns );
         records.add( recording );
         
         if ( runningAverageWeighIns.size() == 6 ) {
            runningAverageWeighIns.remove();
         }
         runningAverageWeighIns.add( recording );
      }
   }//End Constructor
   
   /**
    * Method to add a weeks worth of {@link WeightRecording}s.
    * @param date the {@link LocalDate} to start the week from.
    * @param previousWeighIns the previous {@link WeightRecording}s to average over.
    * @return the {@link WeightRecording} constructed.
    */
   private WeightRecording addRecord( LocalDate date, Collection< WeightRecording > previousWeighIns ) {
      return new WeightRecording( date, previousWeighIns );
   }//End Method
   
   /**
    * Access to the records.
    * @return the {@link WeightRecording}s.
    */
   public List< WeightRecording > records() {
      return records;
   }//End Method

}//End Class
