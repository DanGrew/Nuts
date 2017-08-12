/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.progress;

import uk.dangrew.nuts.progress.WeightRecording;

/**
 * Provides a {@link WeightRecording} row in the {@link WeighInTable}.
 */
public class WeightRecordingRow {
   
   private final WeightRecording recording;
   
   /**
    * Constructs a new {@link WeightRecordingRow}.
    * @param recording the associated {@link WeightRecording}.
    */
   public WeightRecordingRow( WeightRecording recording ) {
      this.recording = recording;
   }//End Constructor
   
   public WeightRecording recording(){
      return recording;
   }//End Method

}//End Class
