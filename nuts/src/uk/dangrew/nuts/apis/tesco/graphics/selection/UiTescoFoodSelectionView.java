package uk.dangrew.nuts.apis.tesco.graphics.selection;

import javafx.scene.layout.BorderPane;

public class UiTescoFoodSelectionView extends BorderPane {

   private final UiTescoFoodPortionOptions options;
   
   public UiTescoFoodSelectionView() {
      UiTescoFoodSelectionController selectionController = new UiTescoFoodSelectionController();
      options = new UiTescoFoodPortionOptions( selectionController );
      
      UiTescoPortionOptionsController controller = new UiTescoPortionOptionsController( options );
      TescoFoodSelectionPaneManager paneManager = new TescoFoodSelectionPaneManager( controller );
      controller.controlSelection( paneManager );
      
      setTop( new UiTescoFoodSelectionControls() );
      setCenter( paneManager.selectionPane() );
      setRight( options );
   }//End Constructor
   
}//End Class
