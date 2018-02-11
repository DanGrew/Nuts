package uk.dangrew.nuts.graphics.selection;

import java.util.Arrays;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.nuts.graphics.table.ConceptOptionsImpl;
import uk.dangrew.nuts.store.Database;

public class UiFoodSelectionView extends TitledPane {

   public UiFoodSelectionView( Database database, UiFoodSelectionController controller ) {
      FoodSelectionPaneManager paneManager = new FoodSelectionPaneManager( controller ); 
      controller.controlSelection( paneManager );
      
      GridPane wrapper = new GridPane();
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenColumns( wrapper, 1 );
      styling.configureConstraintsForRowPercentages( wrapper, 10, 90 );
      
      wrapper.add( new UiFoodSelectionControls( 
               new ConceptOptionsImpl<>( Arrays.asList( 
                        database.foodItems(), 
                        database.meals() 
               ) ).options(), controller 
      ), 0, 0 );
      wrapper.add( paneManager.selectionPane(), 0, 1 );
      
      this.setText( "Selection" );
      this.setCollapsible( false );
      this.setContent( wrapper );
   }//End Constructor
   
}//End Class
