/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.progress.custom;

import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import uk.dangrew.kode.javafx.table.base.ConceptTableRowImpl;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class ProgressSeriesDataRow extends ConceptTableRowImpl< ProgressSeries >{
   
   private final LocalDateTime timestamp;
   private final ObjectProperty< Double > valueProperty;
   
   public ProgressSeriesDataRow( ProgressSeries series, LocalDateTime timestamp ) {
      super( series );
      this.timestamp = timestamp;
      this.valueProperty = new SimpleObjectProperty<>( series.values().entryFor( timestamp ) );
      this.valueProperty.addListener( ( s, o, n ) -> series.values().record( timestamp, n ) );
   }//End Constructor
   
   public LocalDateTime timestamp(){
      return timestamp;
   }//End Method
   
   public ObjectProperty< Double > valueProperty(){
      return valueProperty;
   }//End Method

}//End Class
