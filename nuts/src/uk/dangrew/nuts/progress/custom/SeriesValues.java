package uk.dangrew.nuts.progress.custom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NavigableMap;

public class SeriesValues extends EntryData< LocalDateTime, Double >{

   public NavigableMap< LocalDateTime, Double > range( LocalDate t1 ) {
      return range( LocalDateTime.of( t1, LocalTime.MIN ), LocalDateTime.of( t1, LocalTime.MAX ) );
   }//End Method
   
}//End Class
