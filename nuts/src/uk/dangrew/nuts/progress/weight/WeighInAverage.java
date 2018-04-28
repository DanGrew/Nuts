/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.progress.weight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;

/**
 * {@link WeighInAverage} provides a calculated average of {@link WeighIn}s.
 */
public class WeighInAverage extends WeighIn implements WeightProperties {
   
   private final ChangeListener< Object > weightCalculator;
   private final ChangeListener< Object > bodyFatCalculator;
   private final ChangeListener< Object > leanMassCalculator;
   private final Collection< WeightRecording > recordings;
   
   /**
    * Constructs a new {@link WeighInAverage}.
    * @param weighIns the {@link WeightRecording}s to consider.
    */
   public WeighInAverage( WeightRecording... weighIns ) {
      this( Arrays.asList( weighIns ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link WeighInAverage}.
    * @param weighIns the {@link WeightRecording}s to consider.
    */
   public WeighInAverage( Collection< WeightRecording > weighIns ) {
      super( DayTime.Period );
      this.recordings = new ArrayList<>( weighIns );
      
      this.weightCalculator = ( s, o, n ) -> calculateAverage( WeighIn::weight );
      this.bodyFatCalculator = ( s, o, n ) -> calculateAverage( WeighIn::bodyFat );
      this.leanMassCalculator = ( s, o, n ) -> calculateAverage( WeighIn::leanMass );
      
      this.recordings.forEach( w -> {
         w.morningWeighIn().weight().addListener( weightCalculator );
         w.morningWeighIn().bodyFat().addListener( bodyFatCalculator );
         w.morningWeighIn().leanMass().addListener( leanMassCalculator );
         w.eveningWeighIn().weight().addListener( weightCalculator );
         w.eveningWeighIn().bodyFat().addListener( bodyFatCalculator );
         w.eveningWeighIn().leanMass().addListener( leanMassCalculator );
      } );
      calculateAverages();
   }//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public RecordingType recordingType() {
      return RecordingType.CalculatedAverage;
   }//End Method
   
   /**
    * Method to calculate the averages for each property.
    */
   private void calculateAverages(){
      calculateAverage( WeighIn::weight );
      calculateAverage( WeighIn::bodyFat );
      calculateAverage( WeighIn::leanMass );
   }//End Method
   
   /**
    * Method to calculate the average for the associated property.
    * @param valueRetriever the {@link Function} to retrieve the property from the {@link WeighIn}.
    */
   private void calculateAverage( Function< WeighIn, ObjectProperty< Double > > valueRetriever ) {
      double total = 0;
      int count = 0;
      
      for ( WeightRecording recording : recordings ) {
         Double value = valueRetriever.apply( recording.morningWeighIn() ).get();
         if ( value != null ) {
            count++;
            total += value;
         }
         
         value = valueRetriever.apply( recording.eveningWeighIn() ).get();
         if ( value != null ) {
            count++;
            total += value;
         }
      }
      
      double average = count == 0 ? 0.0 : total / count;
      valueRetriever.apply( this ).set( average );
   }//End Method

   /**
    * Access to the {@link WeightRecording}s.
    * @return the {@link Collection}.
    */
   Collection< WeightRecording > weighIns() {
      return recordings;
   }//End Method
   
}//End Class
