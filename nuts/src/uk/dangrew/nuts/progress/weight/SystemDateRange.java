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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;

/**
 * {@link SystemDateRange} is responsible for defining the range of {@link LocalDate}s
 * managed by the system.
 */
public class SystemDateRange {
   
   static final LocalDate START_DATE = LocalDate.of( 2017, 4, 24 );
   static final LocalDate END_DATE = LocalDate.now().plusDays( 30 );
   static final ObservableList< LocalDate > DATE_RANGE;
   
   static {
      ObservableList< LocalDate > range = FXCollections.observableArrayList();
      
      int currentOffset = 0;
      while( !START_DATE.plusDays( currentOffset ).isEqual( END_DATE ) ) {
         range.add( START_DATE.plusDays( currentOffset ) );
         currentOffset++;
      }
      DATE_RANGE = new PrivatelyModifiableObservableListImpl<>( range );
   }
   
   /**
    * Access to the range. 
    * @return the {@link ObservableList}, not modifiable.
    */
   public ObservableList< LocalDate > get(){
      return DATE_RANGE;
   }//End Method

}//End Method
