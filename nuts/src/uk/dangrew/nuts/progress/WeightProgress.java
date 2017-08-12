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

   static final LocalDate START_DATE = LocalDate.of( 2017, 4, 24 );
   
   private final List< WeightRecording > records;
   
   /**
    * Constructs a new {@link WeightProgress}.
    */
   public WeightProgress() {
      this.records = new ArrayList<>();
      
      Queue< WeightRecording > runningAverageWeighIns = new LinkedList<>();
      int currentOffset = 0;
      while( !START_DATE.plusDays( currentOffset ).isAfter( LocalDate.now() ) ) {
         WeightRecording recording = addRecord( START_DATE.plusDays( currentOffset ), runningAverageWeighIns );
         records.add( recording );
         
         if ( runningAverageWeighIns.size() == 6 ) {
            runningAverageWeighIns.remove();
         }
         runningAverageWeighIns.add( recording );
         
         currentOffset++;
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
