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
import uk.dangrew.nuts.graphics.table.ConceptTableColumnsPopulator;
import uk.dangrew.nuts.graphics.table.TableConfiguration;
import uk.dangrew.nuts.graphics.table.configuration.TableViewColumnConfigurer;
import uk.dangrew.nuts.progress.custom.ProgressSeries;

public class ProgressSeriesTableColumns implements ConceptTableColumnsPopulator< ProgressSeries >{

   static final String COLUMN_TITLE_SERIES = "Series";
   
   private final ProgressSeriesGraphVisibilityController controller;
   private final TableConfiguration configuration;

   public ProgressSeriesTableColumns( GraphSeriesVisibility graphController ) {
      this.configuration = new TableConfiguration();
      this.controller = new ProgressSeriesGraphVisibilityController( graphController );
   }//End Constructor
   
   @Override public void populateColumns( ConceptTable< ProgressSeries > table ) {
      table.setEditable( true );
      configuration.configureCheckBoxController( 
               new TableViewColumnConfigurer<>( table ), 
               controller, 
               0.2 
      );
      configuration.initialiseStringColumn( 
               new TableViewColumnConfigurer<>( table ), 
               COLUMN_TITLE_SERIES, 
               0.8, 
               p -> p.properties().nameProperty(), 
               true 
      );
   }//End Method
   
}//End Class
