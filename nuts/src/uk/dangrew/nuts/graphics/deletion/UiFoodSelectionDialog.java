package uk.dangrew.nuts.graphics.deletion;

import java.util.Arrays;
import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import uk.dangrew.nuts.food.Food;
import uk.dangrew.nuts.graphics.food.FoodStringConverter;
import uk.dangrew.kode.javafx.table.options.ConceptOptions;
import uk.dangrew.kode.javafx.table.options.ConceptOptionsImpl;
import uk.dangrew.nuts.store.Database;

public class UiFoodSelectionDialog extends ChoiceDialog< Food > {

   private final ConceptOptions< Food > options;
   
   public UiFoodSelectionDialog( Database database ) {
      this( new ConceptOptionsImpl<>( Arrays.asList( 
            database.foodItems(),
            database.meals(),
            database.templates(),
            database.dayPlans(),
            database.shoppingLists(),
            database.stockLists()
      ) ) );
   }//End Constructor
   
   public UiFoodSelectionDialog( ConceptOptions< Food > options ) {
      super( null, options.options() );
      this.options = options;
      setTitle( "Food Replacement Selection" );
      setContentText( "Choose your Food:" );
   }//End Constructor
   
   public Optional< Food > friendly_showAndWait(){
      try {
         Node node = ( ( GridPane )getDialogPane().getChildren().get( 3 ) ).getChildren().get( 1 );
         ( ( ComboBox< Food > )node ).setConverter( new FoodStringConverter( options.options() ) );
      } catch ( Exception exception ) {
         //exceptionally hacky, but the controls fx devs want to hide the combobox
         //do nothing, its fine, go with the default
      }
      getItems().clear();
      getItems().addAll( options.options() );
      return showAndWait();
   }//End Method
   
   public void friendly_setHeaderText( String headerText ) {
      setHeaderText( headerText );
   }//End Method
   
}//End Class
