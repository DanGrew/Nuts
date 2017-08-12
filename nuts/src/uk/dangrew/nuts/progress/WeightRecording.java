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
import java.util.List;

/**
 * {@link WeightRecording} provides an individual recording of weight properties for a day.
 */
public class WeightRecording {

   private final LocalDate date;
   private final WeighIn morning;
   private final WeighIn evening;
   private final WeighInAverage runningAverage;
   
   /**
    * Constructs a new {@link WeightRecording}.
    * @param date the {@link LocalDate} of the recording.
    * @param previousWeighIns the {@link WeightRecording}s before this.
    */
   public WeightRecording( LocalDate date, Collection< WeightRecording > previousWeighIns ) {
      this( date, new WeighIn( DayTime.Morning ), new WeighIn( DayTime.Evening ), previousWeighIns );
   }//End Constructor
   
   /**
    * Constructs a new {@link WeightRecording}.
    * @param date the {@link LocalDate} of the recording.
    * @param morning the {@link WeighIn} for the morning.
    * @param evening the {@link WeighIn} for the evening.
    * @param previousWeighIns the {@link WeightRecording}s before this.
    */
   WeightRecording( LocalDate date, WeighIn morning, WeighIn evening, Collection< WeightRecording > previousWeighIns ) {
      this.date = date;
      this.morning = morning;
      this.evening = evening;
      
      List< WeightRecording > averageWeighIns = new ArrayList<>();
      averageWeighIns.addAll( previousWeighIns );
      averageWeighIns.add( this );
      this.runningAverage = new WeighInAverage( averageWeighIns );
   }//End Class
   
   /**
    * Access to the date.
    * @return the {@link LocalDate}.
    */
   public LocalDate date(){
      return date;
   }//End Method

   /**
    * Access to the morning {@link WeighIn}.
    * @return the {@link WeighIn}.
    */
   public WeighIn morningWeighIn() {
      return morning;
   }//End Method

   /**
    * Access to the evening {@link WeighIn}.
    * @return the {@link WeighIn}.
    */
   public WeighIn eveningWeighIn() {
      return evening;
   }//End Method

   /**
    * Access to the {@link WeighInAverage}.
    * @return the {@link WeighInAverage}.
    */
   public WeighInAverage runningAverage() {
      return runningAverage;
   }//End Method

}//End Class
