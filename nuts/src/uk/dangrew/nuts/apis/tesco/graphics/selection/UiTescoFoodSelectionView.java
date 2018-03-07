package uk.dangrew.nuts.apis.tesco.graphics.selection;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.selection.UiFoodSelectionController;

public class UiTescoFoodSelectionView extends GridPane {

   private final UiTescoFoodSelectionControls controls;
   private final UiTescoFoodPortionOptions options;
   
   public UiTescoFoodSelectionView( UiFoodSelectionController selectionController ) {
      this.options = new UiTescoFoodPortionOptions( selectionController );
      
      UiTescoPortionOptionsController controller = new UiTescoPortionOptionsController( options );
      TescoFoodSelectionPaneManager paneManager = new TescoFoodSelectionPaneManager( controller );
      controller.controlSelection( paneManager );
      
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForRowPercentages( this, 10, 90 );
      styling.configureConstraintsForColumnPercentages( this, 80, 20 );
      
      add( controls = new UiTescoFoodSelectionControls( controller ), 0, 0 );
      GridPane.setColumnSpan( controls, 2 );
      
      add( paneManager.selectionPane(), 0, 1 );
      add( options, 1, 1 );
   }//End Constructor
   
}//End Class
