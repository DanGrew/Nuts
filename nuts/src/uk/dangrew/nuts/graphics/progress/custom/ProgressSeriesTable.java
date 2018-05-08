/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.progress.custom;

import uk.dangrew.nuts.graphics.graph.custom.GraphSeriesVisibility;
import uk.dangrew.nuts.graphics.table.ConceptTable;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesTable extends ConceptTable< ProgressSeries > {

   public ProgressSeriesTable( Database database, GraphSeriesVisibility graphController ) {
      super( 
               new ProgressSeriesTableColumns( graphController ), 
               new ProgressSeriesTableController( database.progressSeries(), graphController ) 
      );
   }//End Constructor
   
}//End Class
