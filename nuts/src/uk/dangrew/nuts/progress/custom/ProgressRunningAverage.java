package uk.dangrew.nuts.progress.custom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ProgressRunningAverage {

   private final RunningAverageCalculator calculator;
   private final ProgressChangedListener< LocalDate, Double > averageChangedListener;
   private final ProgressSeries series;
   private final Map< LocalDate, Double > averages;
   
   public ProgressRunningAverage( 
            ProgressSeries series 
   ) {
      this( 
               new RunningAverageCalculator(), 
               new ProgressChangedListener<>(),
               series
      );
   }//End Class
   
   ProgressRunningAverage( 
            RunningAverageCalculator calculator, 
            ProgressChangedListener< LocalDate, Double > averageChangedListener,
            ProgressSeries series 
   ) {
      this.calculator = calculator;
      this.averageChangedListener = averageChangedListener;
      this.series = series;
      this.averages = new HashMap<>();
      
      this.series.values().progressChangedListener().whenProgressAdded( this::recalculate );
      this.series.values().progressChangedListener().whenProgressUpdated( this::recalculate );
      this.series.values().progressChangedListener().whenProgressRemoved( this::recalculate );
   }//End Class

   public Double averageFor( LocalDate day ) {
      return averages.get( day );
   }//End Method
   
   public ProgressChangedListener< LocalDate, Double > progressChangedListener(){
      return averageChangedListener;
   }//End Method
   
   private void recalculate( LocalDateTime timestamp, Double value ) {
      for ( int i = -3; i < 4; i++ ) {
         LocalDate date = timestamp.toLocalDate().plusDays( i );
         Double average = calculator.calculate( date, series );
         record( date, average );
      }
   }//End Method
   
   private void record( LocalDate timestamp, Double value ) {
      if ( value == null ) {
         remove( timestamp );
      } else {
         update( timestamp, value );
      }
   }//End Method
   
   private void update( LocalDate day, Double value ){
      Double existingValue = averageFor( day );
      if ( existingValue == null ) {
         internal_put( day, value );
         progressChangedListener().progressAdded( day, value );
      } else if ( existingValue.equals( value ) ){
         //do nothing
      } else {
         internal_put( day, value );
         progressChangedListener().progressUpdated( day, value );
      }
   }//End Method
   
   private void remove( LocalDate timestamp ){
      Double existingValue = averageFor( timestamp );
      if ( existingValue != null ) {
         internal_remove( timestamp );
         progressChangedListener().progressRemoved( timestamp, existingValue );
      }
   }//End Method
   
   private void internal_put( LocalDate timestamp, Double value ) {
      averages.put( timestamp, value );
   }//End Method
   
   private void internal_remove( LocalDate timestamp ) {
      averages.remove( timestamp );
   }//End Method

}//End Class
