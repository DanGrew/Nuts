/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.progress.custom;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.graph.custom.GraphBuilder;
import uk.dangrew.nuts.graphics.graph.custom.GraphDateStringConverter;
import uk.dangrew.nuts.graphics.graph.custom.GraphWithSettings;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.TableWithControls;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesPane extends GridPane {

   private final ProgressSeriesTable seriesTable;
   private final ProgressSeriesDataTable dataTable;
   private final ProgressEntryTextPane textPane;
   private final ProgressSeriesDataController dataController;
   private final GraphWithSettings graph;
   
   public ProgressSeriesPane( Database database ) {
      new JavaFxStyle().configureConstraintsForColumnPercentages( this, 20, 50, 30 );
      new JavaFxStyle().configureConstraintsForRowPercentages( this, 40, 50 );
      
      this.dataTable = new ProgressSeriesDataTable(); 
      this.dataController = dataTable.controller();
      this.graph = new GraphWithSettings( 
               new GraphBuilder()
                  .withXAxisTickFormatter( new GraphDateStringConverter() )
                  .withXAxisTickInterval( 86400 )
      );
      this.textPane = new ProgressEntryTextPane( dataController );
      
      add( graph, 0, 1 );
      add( textPane, 2, 0 );
      
      add( new ConceptTableWithControls<>( "Series", 
               seriesTable = new ProgressSeriesTable( database, graph.graph().controller().seriesVisibility() ) 
      ), 0, 0 );
      add( new TableWithControls<>( "Data Points", 
               dataTable, 
               dataController
      ), 1, 0 );
      GridPane.setColumnSpan( graph, 3 );
      
      dataController.associate( seriesTable );
   }//End Constructor
   
}//End Class
