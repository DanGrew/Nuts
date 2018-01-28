package uk.dangrew.nuts.graphics.selection;

import javafx.collections.FXCollections;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.store.Database;

public class UiFoodSelectionView extends TitledPane {

   public UiFoodSelectionView( Database database, UiFoodSelectionController controller ) {
      UiFoodSelectionPane selectionPane = new UiFoodSelectionPane( controller );
      controller.controlSelection( selectionPane );
      GridPane wrapper = new GridPane();
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenColumns( wrapper, 1 );
      styling.configureConstraintsForRowPercentages( wrapper, 10, 90 );
      wrapper.add( new UiFoodSelectionControls( FXCollections.observableArrayList( database.foodItems().objectList() ), controller ), 0, 0 );
      wrapper.add( selectionPane, 0, 1 );
      
      this.setText( "Selection" );
      this.setCollapsible( false );
      this.setContent( wrapper );
   }//End Constructor
   
}//End Class
