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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;

/**
 * {@link WeightProgressDateRange} is responsible for defining the range of {@link LocalDate}s
 * managed by the {@link WeightProgress}.
 */
public class WeightProgressDateRange {
   
   static final LocalDate START_DATE = LocalDate.of( 2017, 4, 24 );
   static final ObservableList< LocalDate > DATE_RANGE;
   
   static {
      ObservableList< LocalDate > range = FXCollections.observableArrayList();
      
      int currentOffset = 0;
      while( !START_DATE.plusDays( currentOffset ).isAfter( LocalDate.now() ) ) {
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
