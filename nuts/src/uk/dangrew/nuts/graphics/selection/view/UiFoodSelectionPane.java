package uk.dangrew.nuts.graphics.selection.view;

import java.util.List;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public class UiFoodSelectionPane extends BorderPane implements SelectionPane {

   private final int columns;
   private final ScrollPane scrollPane;
   private final GridPane grid;
   
   public UiFoodSelectionPane() {
      this( 5, 10 );
   }//End Constructor
      
   public UiFoodSelectionPane( int columns, int rows ) {
      this.columns = columns;
      JavaFxStyle styling = new JavaFxStyle();
      
      this.grid = new GridPane();
      this.grid.setHgap( 5 );
      this.grid.setVgap( 5 );
      this.scrollPane = styling.scrollPaneToFitFor( grid ); 
      this.setCenter( scrollPane );
      
      styling.configureConstraintsForEvenColumns( grid, columns );
      styling.configureConstraintsForEvenRows( grid, rows );
   }//End Constructor
   
   @Override public void layoutTiles( List< UiFoodSelectionTile > tiles ){
      grid.getChildren().clear();
      grid.getRowConstraints().clear();
      
      int column = 0;
      int row = 0;
      
      for ( UiFoodSelectionTile tile : tiles ) {
         //there is a memory leak here by not clearing the map, but it will be minimal
         grid.add( tile, column, row );
         
         column++;
         if ( column == columns ) {
            row++;
            column = 0;
         }
      }
   }//End Method

   GridPane grid(){
      return grid;
   }//End Method
   
}//End Class
