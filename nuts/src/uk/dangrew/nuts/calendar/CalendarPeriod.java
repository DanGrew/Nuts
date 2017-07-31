/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.calendar;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;

/**
 * {@link CalendarPeriod} provides a range of {@link LocalDate}s for the days in the calendar in the system.
 */
public class CalendarPeriod {

   private final ObservableList< LocalDate > modifiableDays;
   private final PrivatelyModifiableObservableListImpl< LocalDate > publicDays;
   
   /**
    * Constructs a new {@link CalendarPeriod}.
    */
   public CalendarPeriod() {
      this.modifiableDays = FXCollections.observableArrayList();
      this.publicDays = new PrivatelyModifiableObservableListImpl<>( modifiableDays );
   }//End Constructor
   
   /**
    * Limited access to modifications, providing the actual underlying structure.
    * @return the {@link ObservableList}.
    */
   ObservableList< LocalDate > modifiableDays() {
      return modifiableDays;
   }//End Method
   
   /**
    * Publicaly accessible version of the period in days, no modifications allowed.
    * @return the {@link ObservableList}.
    */
   public ObservableList< LocalDate > days() {
      return publicDays;
   }//End Method

}//End Class
