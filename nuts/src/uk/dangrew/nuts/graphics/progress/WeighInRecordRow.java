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

public class WeighInRecordRow {
   
   private final WeightRecording recording;
   
   public WeighInRecordRow( WeightRecording recording ) {
      this.recording = recording;
   }//End Constructor
   
   public WeightRecording recording(){
      return recording;
   }//End Method

}//End Class
