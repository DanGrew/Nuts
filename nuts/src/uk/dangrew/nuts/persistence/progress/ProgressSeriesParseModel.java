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

import uk.dangrew.kode.datetime.TimestampFormat;
import uk.dangrew.nuts.progress.custom.ProgressEntry;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.progress.custom.ProgressSeriesStore;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesParseModel {
   
   private final TimestampFormat formats;
   private final ProgressSeriesStore store;
   
   private String id;
   private String name;
   private final List< ProgressEntry > entries;
   
   private String timestamp;
   private Double value;
   private String header;
   private String notes;
   
   ProgressSeriesParseModel( Database database ) {
      this.formats = new TimestampFormat();
      this.store = database.progressSeries();
      this.entries = new ArrayList<>();
   }//End Constructor
   
   void startSeries() {
      this.id = null;
      this.name = null;
      this.entries.clear();
      this.timestamp = null;
      this.value = null;
      this.header = null;
      this.notes = null;
   }//End Method
   
   void finishSeries() {
      ProgressSeries series = store.get( id );
      if ( series == null ) {
         series = store.createConcept( id, name );
      }
      series.clear();
      for ( ProgressEntry entry : entries ) {
         series.values().record( entry.timestamp(), entry.value() );
         series.headers().record( entry.timestamp(), entry.header() );
         series.notes().record( entry.timestamp(), entry.notes() );
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
      this.value = null;
      this.header = null;
      this.notes = null;
   }//End Method
   
   void finishEntry(){
      LocalDateTime timestampLdt = formats.parseTimestamp( timestamp );
      if ( timestampLdt == null ) {
         return;
      }

      ProgressEntry entry = new ProgressEntry( timestampLdt );
      entry.setValue( value );
      entry.setHeader( header );
      entry.setNotes( notes );
      entries.add( entry );
   }//End Method
   
   void setTimestamp( String value ) {
      this.timestamp = value;
   }//End Method
   
   void setValue( Double value ) {
      this.value = value;
   }//End Method
   
   void setHeader( String value ) {
      if ( value != null && value.trim().isEmpty() ) {
         value = null;
      }
      this.header = value;
   }//End Method
   
   void setNotes( String value ) {
      if ( value != null && value.trim().isEmpty() ) {
         value = null;
      }
      this.notes = value;
   }//End Method
   
}//End Class
