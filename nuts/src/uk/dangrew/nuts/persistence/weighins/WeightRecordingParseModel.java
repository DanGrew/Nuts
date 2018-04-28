/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.persistence.weighins;

import uk.dangrew.nuts.progress.weight.WeightRecording;
import uk.dangrew.nuts.store.Database;

/**
 * {@link WeightRecordingParseModel} provides the handles for the {@link uk.dangrew.jupa.json.parse.JsonParser} when
 * parsing {@link WeightRecording}s.
 */
class WeightRecordingParseModel {
   
   private final Database database;
   
   private String date;
   private Double morningWeight;
   private Double morningBodyFat;
   private Double morningLeanMass;
   private Double eveningWeight;
   private Double eveningBodyFat;
   private Double eveningLeanMass;
   
   /**
    * Constructs a new {@link WeightRecordingParseModel}.
    * @param database the {@link Database}.
    */
   WeightRecordingParseModel( Database database ) {
      this.database = database;
   }//End Constructor
   
   /**
    * Triggered when starting a new {@link WeightRecording}.
    * @param key the parsed key.
    */
   void startWeighIn( String key ) {
      this.date = null;
      this.morningWeight = null;
      this.morningBodyFat = null;
      this.morningLeanMass = null;
      this.eveningWeight = null;
      this.eveningBodyFat = null;
      this.eveningLeanMass = null;
   }//End Method
   
   /**
    * Triggered when all values of a {@link WeightRecording} have been parsed.
    * @param key the parsed key.
    */
   void finishWeighIn( String key ) {
      WeightRecording recording = database.weightProgress().records().stream()
               .filter( r -> r.date().toString().equalsIgnoreCase( date ) )
               .findFirst()
               .orElse( null );
      if ( recording == null ) {
         System.out.println( "Cannot find recording for: " + date );
         return;
      }
      
      recording.morningWeighIn().weight().set( morningWeight );
      recording.morningWeighIn().bodyFat().set( morningBodyFat );
      recording.morningWeighIn().leanMass().set( morningLeanMass );
      recording.eveningWeighIn().weight().set( eveningWeight );
      recording.eveningWeighIn().bodyFat().set( eveningBodyFat );
      recording.eveningWeighIn().leanMass().set( eveningLeanMass );
   }//End Method
   
   /**
    * Sets the {@link WeightRecording#date()} for the current {@link WeightRecording}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setDate( String key, String value ) {
      this.date = value;
   }//End Method
   
   /**
    * Sets the {@link WeightRecording#morningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#weight()} for the current {@link WeightRecording}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setMorningWeight( String key, Double value ) {
      this.morningWeight = extractWrittenValue( value );
   }//End Method

   /**
    * Sets the {@link WeightRecording#morningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#bodyFat()} for the current {@link WeightRecording}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setMorningBodyFat( String key, Double value ) {
      this.morningBodyFat = extractWrittenValue( value );
   }//End Method
   
   /**
    * Sets the {@link WeightRecording#morningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#leanMass()} for the current {@link WeightRecording}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setMorningLeanMass( String key, Double value ) {
      this.morningLeanMass = extractWrittenValue( value );
   }//End Method
   
   /**
    * Sets the {@link WeightRecording#eveningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#weight()} for the current {@link WeightRecording}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setEveningWeight( String key, Double value ) {
      this.eveningWeight = extractWrittenValue( value );
   }//End Method

   /**
    * Sets the {@link WeightRecording#eveningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#bodyFat()} for the current {@link WeightRecording}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setEveningBodyFat( String key, Double value ) {
      this.eveningBodyFat = extractWrittenValue( value );
   }//End Method
   
   /**
    * Sets the {@link WeightRecording#eveningWeighIn()} {@link uk.dangrew.nuts.progress.WeighIn#leanMass()} for the current {@link WeightRecording}.
    * @param key the parsed key.
    * @param value the parsed value.
    */
   void setEveningLeanMass( String key, Double value ) {
      this.eveningLeanMass = extractWrittenValue( value );
   }//End Method   
   
   private Double extractWrittenValue( Double value ) {
      if ( value == 0.0 ) {
         return null;
      } else {
         return value;
      }
   }//End Method
}//End Class
