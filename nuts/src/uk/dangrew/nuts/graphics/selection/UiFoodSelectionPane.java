package uk.dangrew.nuts.graphics.selection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;

public class UiFoodSelectionPane extends BorderPane {

   private static final int COLUMN_NUMBER = 5; 
   
   private final ScrollPane scrollPane;
   private final GridPane grid;
   
   private final Map< Food, UiFoodTile > tiles;
   private final UiFoodSelectionController controller;
   
   public UiFoodSelectionPane( UiFoodSelectionController controller ) {
      JavaFxStyle styling = new JavaFxStyle();
      
      this.controller = controller;
      this.tiles = new HashMap<>();
      
      this.grid = new GridPane();
      this.grid.setHgap( 5 );
      this.grid.setVgap( 5 );
      this.scrollPane = styling.scrollPaneToFitFor( grid ); 
      this.setCenter( scrollPane );
      
      styling.configureConstraintsForEvenColumns( grid, 5 );
      styling.configureConstraintsForEvenRows( grid, 10 );
   }//End Constructor
   
   public void layoutTiles( List< Food > foods ){
      grid.getChildren().clear();
      grid.getRowConstraints().clear();
      
      int column = 0;
      int row = 0;
      
      for ( Food food : foods ) {
         UiFoodTile tile = tiles.get( food );
         if ( tile == null ) {
            tile = new UiFoodTile( new FoodPortion( food, 100 ), controller );
            tiles.put( food, tile );
         }
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
