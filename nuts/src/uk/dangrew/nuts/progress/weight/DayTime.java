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
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * {@link DayTime} provides the times of day of the {@link WeightRecording}.
 */
public enum DayTime {
   
   Morning( d -> d + ": Morning" ),
   Evening( d -> d + ": Evening" ),
   Period( d -> "Average for w/c " + d );
   
   static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("EEE, MMM d yyyy");
   private final Function< String, String > displayInformationProvider;
   
   /**
    * Constructs a new {@link DayTime}.
    * @param displayInformationProvider the {@link Function} to provide information for the {@link DayTime}
    * in relation to the given {@link String} {@link LocalDate}.
    */
   private DayTime( Function< String, String > displayInformationProvider ) {
      this.displayInformationProvider = displayInformationProvider;
   }//End Constructor
   
   /**
    * Method to provide the display information for the {@link DayTime} given the {@link LocalDate}.
    * @param date the {@link LocalDate} to provide information for.
    * @return the display information.
    */
   public String disaplyInformationFor( LocalDate date ) {
      return displayInformationProvider.apply( FORMAT.format( date ) );
   }//End Method

}//End Enum
