/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.progress.custom;

import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class ProgressSeriesTableColumns implements ConceptTableColumnsPopulator< ProgressSeries >{

   static final String COLUMN_TITLE_SERIES = "Series";
   
   private final TableConfiguration configuration;

   public ProgressSeriesTableColumns() {
      this.configuration = new TableConfiguration();
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< ProgressSeries > table ) {
      configuration.initialiseStringColumn( table, COLUMN_TITLE_SERIES, 0.8, p -> p.properties().nameProperty(), true );
   }//End Method
   
}//End Class
