package uk.dangrew.nuts.progress.custom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NavigableMap;

public class RunningAverageCalculator {

   public Double calculate( LocalDate timestamp, ProgressSeries series ) {
      double runningAverage = 0;
      for ( int i = -3; i < 4; i++ ) {
         Double dayAverage = computeAverageForDay( timestamp.plusDays( i ), series );
         if ( dayAverage == null ) {
            return null;
         }
         
         runningAverage += dayAverage;
      }
      
      return runningAverage / 7.0;
   }//End Method
   
   private Double computeAverageForDay( LocalDate day, ProgressSeries series ) {
      NavigableMap< LocalDateTime, Double > dayRecordings = series.range( day );
      if ( dayRecordings.isEmpty() ) {
         return null;
      }
      
      double runningAverage = 0;
      for ( Double value : dayRecordings.values() ) {
         runningAverage += value;
      }
      return runningAverage / dayRecordings.size();
   }//End Method

}//End Class
