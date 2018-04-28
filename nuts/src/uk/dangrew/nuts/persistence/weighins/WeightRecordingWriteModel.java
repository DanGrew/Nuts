/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.weighins;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import uk.dangrew.nuts.progress.weight.WeightRecording;
import uk.dangrew.nuts.store.Database;

/**
 * {@link WeightRecordingWriteModel} is responsible for handling the hooks for the {@link uk.dangrew.jupa.json.parse.JsonParser}
 * when writing {@link WeightRecording}s.
 */
class WeightRecordingWriteModel {
   
   private final Database database;
   private final List< WeightRecording > buffer;
   private WeightRecording current; 
   
   /**
    * Constructs a new {@link WeightRecordingWriteModel}.
    * @param database the {@link Database}.
    */
   WeightRecordingWriteModel( Database database ) {
      this.database = database;
      this.buffer = new ArrayList<>();
   }//End Constructor
   
   /**
    * Provides the number of {@link WeightRecording}s to write.
    * @param key the parsed key.
    * @return the number of items.
    */
   Integer getNumberOfWeighIns( String key ){
      return database.weightProgress().records().size();
   }//End Method
   
   /**
    * Triggered when the writing of the {@link WeightRecording}s begins, initialising the items to write.
    * @param key the parsed key.
    */
   void startWritingWeighIns( String key ) {
      buffer.clear();
      buffer.addAll( database.weightProgress().records() );
   }//End Method
   
   /**
    * Triggered when starting a new {@link WeightRecording}.
    * @param key the parsed key.
    */
   void startWritingWeighIn( String key ) {
      if ( buffer.isEmpty() ) {
         return;
      }
      this.current = buffer.remove( 0 );
   }//End Method

   /**
    * Provides the next {@link WeightRecording#date()}.
    * @param key the parsed key.
    * @return the value.
    */
   String getDate( String key ) {
      return DateTimeFormatter.ISO_LOCAL_DATE.format( current.date() );
   }//End Method

   /**
    * Provides the next {@link WeightRecording#morningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#weight()}.
    * @param key the parsed key.
    * @return the value.
    */
   Double getMorningWeight( String key ) {
      return makeWriteableValue( current.morningWeighIn().weight() );
   }//End Method
   
   /**
    * Provides the next {@link WeightRecording#morningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#bodyFat()}.
    * @param key the parsed key.
    * @return the value.
    */
   Double getMorningBodyFat( String key ) {
      return makeWriteableValue( current.morningWeighIn().bodyFat() );
   }//End Method
   
   /**
    * Provides the next {@link WeightRecording#morningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#leanMass()}.
    * @param key the parsed key.
    * @return the value.
    */
   Double getMorningLeanMass( String key ) {
      return makeWriteableValue( current.morningWeighIn().leanMass() );
   }//End Method
   
   /**
    * Provides the next {@link WeightRecording#eveningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#weight()}.
    * @param key the parsed key.
    * @return the value.
    */
   Double getEveningWeight( String key ) {
      return makeWriteableValue( current.eveningWeighIn().weight() );
   }//End Method
   
   /**
    * Provides the next {@link WeightRecording#eveningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#bodyFat()}.
    * @param key the parsed key.
    * @return the value.
    */
   Double getEveningBodyFat( String key ) {
      return makeWriteableValue( current.eveningWeighIn().bodyFat() );
   }//End Method
   
   /**
    * Provides the next {@link WeightRecording#eveningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#leanMass()}.
    * @param key the parsed key.
    * @return the value.
    */
   Double getEveningLeanMass( String key ) {
      return makeWriteableValue( current.eveningWeighIn().leanMass() );
   }//End Method
   
   /**
    * Method to make the writeable value, converting null to 0.0.
    * @param property the {@link ObjectProperty} to create the writeable value for.
    * @return the value.
    */
   private double makeWriteableValue( ObjectProperty< Double > property ) {
      if ( property.get() == null ) {
         return 0.0;
      } else {
         return property.get();
      }
   }//End Method
   
}//End Class
