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
   private final ProgressSeriesDataController dataController;
   private final GraphWithSettings graph;
   
   public ProgressSeriesPane( Database database ) {
      new JavaFxStyle().configureConstraintsForColumnPercentages( this, 20, 80 );
      new JavaFxStyle().configureConstraintsForRowPercentages( this, 40, 60 );
      
      add( graph = new GraphWithSettings( 
               new GraphBuilder()
                  .withXAxisTickFormatter( new GraphDateStringConverter() )
                  .withXAxisTickInterval( 86400 )
      ), 0, 1 );
      
      add( new ConceptTableWithControls<>( "Series", 
               seriesTable = new ProgressSeriesTable( database, graph.graph().controller().seriesVisibility() ) 
      ), 0, 0 );
      add( new TableWithControls<>( "Data Points", 
               dataTable = new ProgressSeriesDataTable(), 
               dataController = dataTable.controller()
      ), 1, 0 );
      GridPane.setColumnSpan( graph, 2 );
      
      dataController.associate( seriesTable );
      dataController.associate( dataTable );
   }//End Constructor
   
}//End Class
