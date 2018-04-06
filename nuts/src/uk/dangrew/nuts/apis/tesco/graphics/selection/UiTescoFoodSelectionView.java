package uk.dangrew.nuts.apis.tesco.graphics.selection;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.selection.view.UiFoodSelectionController;

public class UiTescoFoodSelectionView extends GridPane {

   private final UiTescoFoodSelectionControls controls;
   private final UiTescoFoodDescriptionPane options;
   
   public UiTescoFoodSelectionView( UiFoodSelectionController selectionController ) {
      this.options = new UiTescoFoodDescriptionPane( selectionController );
      
      UiTescoDescriptionController controller = new UiTescoDescriptionController( options );
      TescoFoodDescriptionSelectionPaneManager paneManager = new TescoFoodDescriptionSelectionPaneManager( controller );
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
