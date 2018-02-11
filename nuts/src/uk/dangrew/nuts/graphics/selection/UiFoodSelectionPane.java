package uk.dangrew.nuts.graphics.selection;

import java.util.List;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public class UiFoodSelectionPane extends BorderPane implements SelectionPane {

   private static final int COLUMN_NUMBER = 5; 
   
   private final ScrollPane scrollPane;
   private final GridPane grid;
   
   public UiFoodSelectionPane() {
      JavaFxStyle styling = new JavaFxStyle();
      
      this.grid = new GridPane();
      this.grid.setHgap( 5 );
      this.grid.setVgap( 5 );
      this.scrollPane = styling.scrollPaneToFitFor( grid ); 
      this.setCenter( scrollPane );
      
      styling.configureConstraintsForEvenColumns( grid, 5 );
      styling.configureConstraintsForEvenRows( grid, 10 );
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
         if ( column == COLUMN_NUMBER ) {
            row++;
            column = 0;
         }
      }
   }//End Method

   GridPane grid(){
      return grid;
   }//End Method
   
}//End Class
