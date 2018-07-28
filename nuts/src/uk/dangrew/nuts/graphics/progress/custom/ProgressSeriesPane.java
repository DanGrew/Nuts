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
import uk.dangrew.nuts.graphics.graph.custom.GraphSeriesVisibility;
import uk.dangrew.nuts.graphics.graph.custom.GraphWithSettings;
import uk.dangrew.nuts.graphics.table.BasicConceptControls;
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.nuts.graphics.table.TableWithControls;
import uk.dangrew.nuts.graphics.table.controls.TableControls;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesPane extends GridPane {

   private final ConceptTableWithControls< ProgressSeries > seriesTable;
   private final ProgressSeriesTableController seriesController;
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
      
      GraphSeriesVisibility graphController = graph.graph().controller().seriesVisibility();
      add( seriesTable = new TableComponents< ProgressSeries >()
               .withDatabase( database )
               .withColumns( new ProgressSeriesTableColumns( graphController ) )
               .withCheckBoxController( new ProgressSeriesGraphVisibilityController( graphController ) )
               .withController( seriesController = new ProgressSeriesTableController( database.progressSeries(), graphController ) )
               .withControls( new TableControls( new BasicConceptControls( seriesController ) ) )
               .buildTableWithControls( "Series" ), 
      0, 0 );
      add( new TableWithControls<>( "Data Points", 
               dataTable, 
               dataController
      ), 1, 0 );
      GridPane.setColumnSpan( graph, 3 );
      
      dataController.associate( seriesTable.table() );
   }//End Constructor
   
}//End Class
