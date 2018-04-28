/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.progress.weight;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * {@link WeighIn} provides a manual weigh in recording for a particular {@link DayTime}.
 */
public class WeighIn implements WeightProperties {

   private final DayTime dayTime;
   private final ObjectProperty< Double > weight;
   private final ObjectProperty< Double > bodyFat;
   private final ObjectProperty< Double > leanMass;
   
   /**
    * Constructs a new {@link WeighIn}.
    * @param dayTime the {@link DayTime} of the {@link WeighIn}.
    */
   public WeighIn( DayTime dayTime ) {
      this( dayTime, null, null, null );
   }//End Constructor
   
   /**
    * Constructs a new {@link WeighIn}.
    * @param dayTime the {@link DayTime} of the {@link WeighIn}.
    * @param weight the initial weight in pounds.
    * @param bodyFat the initial body fat percentage.
    * @param leanMass the initial lean mass in pounds.
    */
   public WeighIn( DayTime dayTime, Double weight, Double bodyFat, Double leanMass ) {
      this.dayTime = dayTime;
      this.weight = new SimpleObjectProperty<>( weight );
      this.bodyFat = new SimpleObjectProperty<>( bodyFat );
      this.leanMass = new SimpleObjectProperty<>( leanMass );
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public DayTime dayTime() {
      return dayTime;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public ObjectProperty< Double > weight() {
      return weight;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public ObjectProperty< Double > bodyFat() {
      return bodyFat;
   }//End Method

   /**
    * {@inheritDoc}
    */
   @Override public ObjectProperty< Double > leanMass() {
      return leanMass;
   }//End Method
   
   /**
    * {@inheritDoc}
    */
   @Override public RecordingType recordingType() {
      return RecordingType.WeighIn;
   }//End Method

}//End Class
