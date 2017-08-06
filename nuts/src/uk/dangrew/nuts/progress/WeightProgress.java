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
import java.util.List;

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
      
      int currentOffset = 0;
      while( START_DATE.plusDays( currentOffset ).isBefore( LocalDate.now() ) ) {
         addWeekOfRecords( START_DATE.plusDays( currentOffset ) );
         currentOffset += 7;
      }
   }//End Constructor
   
   /**
    * Method to add a weeks worth of {@link WeightRecording}s.
    * @param start the {@link LocalDate} to start the week from.
    */
   private void addWeekOfRecords( LocalDate start ) {
      List< WeighIn > weighInsForAverage = new ArrayList<>();
      for ( int i = 0; i < 7; i++ ) {
         WeighIn morning = new WeighIn( start.plusDays( i ), DayTime.Morning );
         WeighIn evening = new WeighIn( start.plusDays( i ), DayTime.Evening );
         
         records.add( morning );
         records.add( evening );
         
         weighInsForAverage.add( morning );
         weighInsForAverage.add( evening );
      }
      records.add( new WeighInAverage( start, weighInsForAverage ) );
   }//End Method
   
   /**
    * Access to the records.
    * @return the {@link WeightRecording}s.
    */
   public List< WeightRecording > records() {
      return records;
   }//End Method

}//End Class
