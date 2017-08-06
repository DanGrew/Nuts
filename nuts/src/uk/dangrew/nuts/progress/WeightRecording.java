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

import javafx.beans.property.ObjectProperty;

/**
 * {@link WeightRecording} provides the interface for a recording of weight related properties.
 */
public interface WeightRecording {
   
   /**
    * Access to the {@link LocalDate}.
    * @return the {@link LocalDate}.
    */
   public LocalDate date();
   
   /**
    * Access to the {@link DayTime}.
    * @return the {@link DayTime}.
    */
   public DayTime dayTime();
   
   /**
    * Access to the weight in pounds.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > weight();
   
   /**
    * Access to the body fat as a percentage.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > bodyFat();
   
   /**
    * Access to the lean mass in pounds.
    * @return the {@link ObjectProperty}.
    */
   public ObjectProperty< Double > leanMass();
   
   /**
    * Access to the {@link RecordingType}.
    * @return the {@link RecordingType}.
    */
   public RecordingType recordingType();
   
}//End Interface

