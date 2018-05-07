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
import uk.dangrew.nuts.graphics.table.ConceptTableWithControls;
import uk.dangrew.nuts.graphics.table.TableWithControls;
import uk.dangrew.nuts.store.Database;

public class ProgressSeriesPane extends GridPane {

   private final ProgressSeriesTable seriesTable;
   private final ProgressSeriesDataTable dataTable;
   private final ProgressSeriesDataController dataController;
   
   public ProgressSeriesPane( Database database ) {
      new JavaFxStyle().configureConstraintsForColumnPercentages( this, 20, 80 );
      new JavaFxStyle().configureConstraintsForEvenRows( this, 1 );
      
      add( new ConceptTableWithControls<>( "Series", 
               seriesTable = new ProgressSeriesTable( database ) 
      ), 0, 0 );
      add( new TableWithControls<>( "Data Points", 
               dataTable = new ProgressSeriesDataTable(), 
               dataController = dataTable.controller()
      ), 1, 0 );
      
      dataController.associate( seriesTable );
      dataController.associate( dataTable );
   }//End Constructor
   
}//End Class
