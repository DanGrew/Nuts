/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.progress.custom;

import uk.dangrew.nuts.graphics.table.TableConfiguration;

public class ProgressSeriesDataTableColumns {

   static final String COLUMN_TITLE_TIMESTAMP = "Timestamp";
   static final String COLUMN_TITLE_VALUE = "Value";
   
   private final TableConfiguration configuration;

   public ProgressSeriesDataTableColumns() {
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   public void populateColumns( ProgressSeriesDataTable table ) {
      configuration.initialiseStringColumn( table, COLUMN_TITLE_TIMESTAMP, 0.5, r -> r.timestamp().toString() );
      configuration.initialiseDoubleColumn( table, COLUMN_TITLE_VALUE, 0.5, ProgressSeriesDataRow::valueProperty, true );
   }//End Method
   
}//End Class
