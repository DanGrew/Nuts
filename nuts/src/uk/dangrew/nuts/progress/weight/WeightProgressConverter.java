package uk.dangrew.nuts.progress.weight;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class WeightProgressConverter {

   public List< ProgressSeries > convert( WeightProgress weightProgress ) {
      List< ProgressSeries > progressSeries = new ArrayList<>();
      progressSeries.add( new ProgressSeries( "Morning Weight" ) );
      progressSeries.add( new ProgressSeries( "Evening Weight" ) );
      progressSeries.add( new ProgressSeries( "Morning Body Fat" ) );
      progressSeries.add( new ProgressSeries( "Evening Body Fat" ) );
      progressSeries.add( new ProgressSeries( "Morning Lean Mass" ) );
      progressSeries.add( new ProgressSeries( "Evening Lean Mass" ) );
      
      weightProgress.records().forEach( r -> {
         record( progressSeries.get( 0 ), LocalDateTime.of( r.date(), LocalTime.NOON ), r.morningWeighIn().weight().get() );
         record( progressSeries.get( 1 ), LocalDateTime.of( r.date(), LocalTime.MIDNIGHT ), r.eveningWeighIn().weight().get() );
         record( progressSeries.get( 2 ), LocalDateTime.of( r.date(), LocalTime.NOON ), r.morningWeighIn().bodyFat().get() );
         record( progressSeries.get( 3 ), LocalDateTime.of( r.date(), LocalTime.MIDNIGHT ), r.eveningWeighIn().bodyFat().get() );
         record( progressSeries.get( 4 ), LocalDateTime.of( r.date(), LocalTime.NOON ), r.morningWeighIn().leanMass().get() );
         record( progressSeries.get( 5 ), LocalDateTime.of( r.date(), LocalTime.MIDNIGHT ), r.eveningWeighIn().leanMass().get() );
      } );
      
      return progressSeries;
   }//End Method
   
   private void record( ProgressSeries series, LocalDateTime dateTime, Double value ){
      if ( value == null || value.doubleValue() == 0.0 ) {
         return;
      }
      series.values().record( dateTime, value );
   }//End Method
   
}//End Class