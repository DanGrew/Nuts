package uk.dangrew.nuts.graphics.graph.custom;

import java.time.Duration;
import java.time.LocalDateTime;

public enum TimestampPeriod {

   TenSeconds( Duration.ofSeconds( 10 ) ),
   ThirtySeconds( Duration.ofSeconds( 20 ) ),
   OneMinute( Duration.ofMinutes( 1 ) ),
   TenMinutes( Duration.ofMinutes( 10 ) ),
   ThirtyMinutes( Duration.ofMinutes( 30 ) ),
   OneHour( Duration.ofHours( 1 ) ),
   SixHours( Duration.ofHours( 6 ) ),
   TwelveHours( Duration.ofHours( 12 ) ),
   OneDay( Duration.ofDays( 1 ) ),
   FiveDays( Duration.ofDays( 5 ) ),
   TenDays( Duration.ofDays( 10 ) ),
   OneMonth( Duration.ofDays( 30 ) ),
   ThreeMonths( Duration.ofDays( 90 ) ),
   SixMonths( Duration.ofDays( 180 ) ),
   OneYear( Duration.ofDays( 365 ) );
   
   private final Duration duration;
   
   private TimestampPeriod( Duration duration ) {
      this.duration = duration;
   }//End Constructor
   
   public LocalDateTime upperBound( LocalDateTime timestamp ) {
      return timestamp.plusSeconds( duration.getSeconds() );
   }//End Method
   
   public LocalDateTime lowerBound( LocalDateTime timestamp ) {
      return timestamp.minusSeconds( duration.getSeconds() );
   }//End Method
   
}//End Enum
