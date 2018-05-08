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

import uk.dangrew.kode.datetime.DateTimeFormats;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.progress.custom.ProgressSeriesStore;

public class ProgressSeriesWriteModel {
   
   private final DateTimeFormats formats;
   private final ProgressSeriesStore store;
   private final List< ProgressSeries > seriesBuffer;
   private final List< LocalDateTime > timestampBuffer;
   private ProgressSeries currentSeries;
   private LocalDateTime currentTimestamp; 
   
   ProgressSeriesWriteModel( ProgressSeriesStore labels ) {
      this.formats = new DateTimeFormats();
      this.store = labels;
      this.seriesBuffer = new ArrayList<>();
      this.timestampBuffer = new ArrayList<>();
   }//End Constructor
   
   Integer getNumberOfSeries( String key ){
      seriesBuffer.clear();
      seriesBuffer.addAll( store.objectList() );
      return seriesBuffer.size();
   }//End Method
   
   Integer getNumberOfEntries( String key ){
      return seriesBuffer.remove( 0 ).entries().size();
   }//End Method
   
   void startWritingSeriesList() {
      seriesBuffer.clear();
      seriesBuffer.addAll( store.objectList() );
   }//End Method
   
   void startWritingSeries() {
      if ( seriesBuffer.isEmpty() ) {
         return;
      }
      this.currentSeries = seriesBuffer.remove( 0 );
      this.timestampBuffer.clear();
      this.timestampBuffer.addAll( currentSeries.entries() );
   }//End Method

   String getId() {
      return currentSeries.properties().id();
   }//End Method

   String getName() {
      return currentSeries.properties().nameProperty().get();
   }//End Method
   
   void startWritingEntries() {
      if ( timestampBuffer.isEmpty() ) {
         return;
      }
      this.currentTimestamp = timestampBuffer.remove( 0 );
   }//End Method
   
   String getTimestamp() {
      return formats.toTimestampString( currentTimestamp );
   }//End Method
   
   Double getValue() {
      return currentSeries.entryFor( currentTimestamp );
   }//End Method
   
}//End Class
