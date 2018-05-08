/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.progress;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import uk.dangrew.kode.datetime.DateTimeFormats;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.progress.custom.ProgressSeriesStore;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesParseModel {
   
   private final DateTimeFormats formats;
   private final ProgressSeriesStore store;
   
   private String id;
   private String name;
   private final List< Pair< LocalDateTime, Double > > entries;
   
   private String timestamp;
   private double value;
   
   ProgressSeriesParseModel( Database database ) {
      this.formats = new DateTimeFormats();
      this.store = database.progressSeries();
      this.entries = new ArrayList<>();
   }//End Constructor
   
   void startSeries() {
      this.id = null;
      this.name = null;
      this.entries.clear();
      this.timestamp = null;
      this.value = 0.0;
   }//End Method
   
   void finishSeries() {
      ProgressSeries series = store.get( id );
      if ( series == null ) {
         series = store.createConcept( id, name );
      }
      series.clear();
      for ( Pair< LocalDateTime, Double > entry : entries ) {
         series.record( entry.getKey(), entry.getValue() );
      }
   }//End Method
   
   void setId( String value ) {
      this.id = value;
   }//End Method
   
   void setName( String value ) {
      this.name = value;
   }//End Method
   
   void startEntry() {
      this.timestamp = null;
      this.value = 0.0;
   }//End Method
   
   void finishEntry(){
      LocalDateTime timestampLdt = formats.parseTimestamp( timestamp );
      if ( timestampLdt == null ) {
         return;
      }
      
      entries.add( new Pair<>( timestampLdt, value ) );
   }//End Method
   
   void setTimestamp( String value ) {
      this.timestamp = value;
   }//End Method
   
   void setValue( Double value ) {
      this.value = value;
   }//End Method
   
}//End Class
