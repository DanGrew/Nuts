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
import uk.dangrew.nuts.graphics.graph.custom.GraphSeriesVisibility;
import uk.dangrew.nuts.graphics.graph.weight.DoubleVerticalGraph;
import uk.dangrew.kode.javafx.table.controls.BasicConceptControls;
import uk.dangrew.kode.javafx.table.controls.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.TableComponents;
import uk.dangrew.kode.javafx.table.controls.TableWithControls;
import uk.dangrew.kode.javafx.table.controls.TableControls;
import uk.dangrew.nuts.progress.custom.ProgressSeries;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesPane extends GridPane {

   private final ConceptTableWithControls< ProgressSeries > group1Table;
   private final ConceptTableWithControls< ProgressSeries > group2Table;
   private final ProgressSeriesTableController group1Controller;
   private final ProgressSeriesTableController group2Controller;
   private final ProgressSeriesDataTable dataTable;
   private final ProgressEntryTextPane textPane;
   private final ProgressSeriesDataController dataController;
   private final DoubleVerticalGraph graphArea;
   
   public ProgressSeriesPane( Database database ) {
      new JavaFxStyle().configureConstraintsForColumnPercentages( this, 20, 20, 30, 30 );
      new JavaFxStyle().configureConstraintsForRowPercentages( this, 30, 50 );
      
      this.dataTable = new ProgressSeriesDataTable(); 
      this.dataController = dataTable.controller();
      this.graphArea = new DoubleVerticalGraph(
               "Combined ProgressSeries Graph",
               "Date",
               "Group 1 Scale",
               "Group 2 Scale"
      );
      this.textPane = new ProgressEntryTextPane( dataController );
      
      add( graphArea, 0, 1 );
      add( textPane, 3, 0 );
      
      GraphSeriesVisibility graphController1 = graphArea.graph1().controller().seriesVisibility();
      add( group1Table = new TableComponents< ProgressSeries >()
               .withDatabase( database )
               .withColumns( new ProgressSeriesTableColumns( graphController1 ) )
               .withCheckBoxController( new ProgressSeriesGraphVisibilityController( graphController1 ) )
               .withController( group1Controller = new ProgressSeriesTableController( database.progressSeries(), graphController1 ) )
               .withControls( new TableControls( new BasicConceptControls( group1Controller ) ) )
               .buildTableWithControls( "Group 1" ), 
      0, 0 );
      GraphSeriesVisibility graphController2 = graphArea.graph2().controller().seriesVisibility();
      add( group2Table = new TableComponents< ProgressSeries >()
               .withDatabase( database )
               .withColumns( new ProgressSeriesTableColumns( graphController2 ) )
               .withCheckBoxController( new ProgressSeriesGraphVisibilityController( graphController2 ) )
               .withController( group2Controller = new ProgressSeriesTableController( database.progressSeries(), graphController2 ) )
               .withControls( new TableControls( new BasicConceptControls( group2Controller ) ) )
               .buildTableWithControls( "Group 2" ), 
      1, 0 );
      add( new TableWithControls<>( "Data Points", 
               dataTable, 
               dataController
      ), 2, 0 );
      GridPane.setColumnSpan( graphArea, 4 );
      
      new SelectionSynchronizer< ProgressSeries >( group1Table.table(), group2Table.table() );
      
      dataController.associate( group1Table.table() );
   }//End Constructor
   
}//End Class
