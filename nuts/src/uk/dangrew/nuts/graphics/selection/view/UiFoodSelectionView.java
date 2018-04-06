package uk.dangrew.nuts.graphics.selection.view;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.selection.model.FoodSelectionPaneManager;
import uk.dangrew.nuts.store.Database;

public class UiFoodSelectionView extends GridPane {

   public UiFoodSelectionView( Database database, UiFoodSelectionController controller ) {
      FoodSelectionPaneManager paneManager = new FoodSelectionPaneManager( controller ); 
      controller.controlSelection( paneManager );
      
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenColumns( this, 1 );
      styling.configureConstraintsForRowPercentages( this, 10, 90 );
      
      add( new UiFoodSelectionControls( 
               database.labels().objectList(), controller 
      ), 0, 0 );
      add( paneManager.selectionPane(), 0, 1 );
   }//End Constructor
   
}//End Class
