package uk.dangrew.nuts.progress.custom;

import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ProgressEntry {

   private final ObjectProperty< LocalDateTime > timestampProperty;
   private final ObjectProperty< Double > valueProperty;
   
   public ProgressEntry() {
      this( null, 0.0 );
   }//End Constructor
   
   public ProgressEntry( LocalDateTime timstamp, double value ) {
      this.timestampProperty = new SimpleObjectProperty<>( timstamp );
      this.valueProperty = new SimpleObjectProperty<>( value );
   }//End Constructor
   
   public ObjectProperty< LocalDateTime > timestampProperty(){
      return timestampProperty;
   }//End Method
   
   public ObjectProperty< Double > valueProperty(){
      return valueProperty;
   }//End Method
   
}//End Class
