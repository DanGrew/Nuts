package uk.dangrew.nuts.progress.custom;

import java.time.LocalDateTime;

public class ProgressEntry {

   private final LocalDateTime timestamp;
   private Double value;
   private String heading;
   private String notes;
   
   public ProgressEntry( LocalDateTime timestamp ) {
      this( timestamp, 0.0 );
   }//End Constructor
   
   public ProgressEntry( LocalDateTime timstamp, double value ) {
      this.timestamp = timstamp;
      this.value = value;
   }//End Constructor

   public LocalDateTime timestamp() {
      return timestamp;
   }//End Method
   
   public Double value() {
      return value;
   }//End Method

   public void setValue( Double value ) {
      this.value = value;
   }//End Method

   public String header() {
      return heading;
   }//End Method

   public void setHeader( String heading ) {
      this.heading = heading;
   }//End Method

   public String notes() {
      return notes;
   }//End Method

   public void setNotes( String notes ) {
      this.notes = notes;
   }//End Method
   
}//End Class
