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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;

/**
 * {@link WeighInAverage} provides a calculated average of {@link WeighIn}s.
 */
public class WeighInAverage extends WeighIn implements WeightRecording {
   
   private final ChangeListener< Object > weightCalculator;
   private final ChangeListener< Object > bodyFatCalculator;
   private final ChangeListener< Object > leanMassCalculator;
   private final Collection< WeighIn > weighIns;
   
   /**
    * Constructs a new {@link WeighInAverage}.
    * @param date the {@link LocalDate} of the start of the average period.
    * @param weighIns the {@link WeighIn}s to consider.
    */
   public WeighInAverage( LocalDate date, WeighIn... weighIns ) {
      this( date, Arrays.asList( weighIns ) );
   }//End Constructor
   
   /**
    * Constructs a new {@link WeighInAverage}.
    * @param date the {@link LocalDate} of the start of the average period.
    * @param weighIns the {@link WeighIn}s to consider.
    */
   public WeighInAverage( LocalDate date, List< WeighIn > weighIns ) {
      super( date, DayTime.Period );
      this.weighIns = new ArrayList<>( weighIns );
      
      this.weightCalculator = ( s, o, n ) -> calculateAverage( WeighIn::weight );
      this.bodyFatCalculator = ( s, o, n ) -> calculateAverage( WeighIn::bodyFat );
      this.leanMassCalculator = ( s, o, n ) -> calculateAverage( WeighIn::leanMass );
      
      this.weighIns.forEach( w -> {
         w.weight().addListener( weightCalculator );
         w.bodyFat().addListener( bodyFatCalculator );
         w.leanMass().addListener( leanMassCalculator );
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
      
      for ( WeighIn weighIn : weighIns ) {
         Double value = valueRetriever.apply( weighIn ).get();
         if ( value == null ) {
            continue;
         }
         
         count++;
         total += value;
      }
      
      double average = count == 0 ? 0.0 : total / count;
      valueRetriever.apply( this ).set( average );
   }//End Method

   /**
    * Access to the {@link WeighIn}s.
    * @return the {@link Collection}.
    */
   Collection< WeighIn > weighIns() {
      return weighIns;
   }//End Method
   
}//End Class
