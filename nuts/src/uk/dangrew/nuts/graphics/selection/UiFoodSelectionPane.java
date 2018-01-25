package uk.dangrew.nuts.graphics.selection;

import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.observable.FunctionListChangeListenerImpl;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.food.FoodPortion;

public class UiFoodSelectionPane extends BorderPane {

   private static final int COLUMN_NUMBER = 5; 
   
   private final ScrollPane scrollPane;
   private final GridPane grid;
   
   private final ObservableList< Food > foods;
   private final UiFoodSelectionController controller;
   
   public UiFoodSelectionPane( ObservableList< Food > foods, UiFoodSelectionController controller ) {
      JavaFxStyle styling = new JavaFxStyle();
      
      this.foods = foods;
      this.controller = controller;
      
      this.grid = new GridPane();
      this.grid.setHgap( 5 );
      this.grid.setVgap( 5 );
      this.scrollPane = styling.scrollPaneToFitFor( grid ); 
      this.setCenter( scrollPane );
      
      styling.configureConstraintsForEvenColumns( grid, 5 );
      styling.configureConstraintsForEvenRows( grid, 10 );
      
      layoutTiles();
      
      foods.addListener( new FunctionListChangeListenerImpl<>( 
               a -> layoutTiles(), r -> layoutTiles()  
      ) );
   }//End Constructor
   
   private void layoutTiles(){
      grid.getChildren().forEach( n -> ( ( UiFoodTile )n ).detach() );
      grid.getChildren().clear();
      grid.getRowConstraints().clear();
      
      int column = 0;
      int row = 0;
      
      for ( Food food : foods ) {
         UiFoodTile tile = new UiFoodTile( new FoodPortion( food, 100 ), controller );
         grid.add( tile, column, row );
         
         if ( column++ > COLUMN_NUMBER ) {
            row++;
            column = 0;
         }
      }
   }//End Method
   
   GridPane grid(){
      return grid;
   }//End Method
   
}//End Class
