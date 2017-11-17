package uk.dangrew.nuts.graphics.day;

import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import uk.dangrew.nuts.graphics.table.FoodOptions;
import uk.dangrew.nuts.template.Template;

public class UiTemplateSelectionDialog extends ChoiceDialog< Template > {

   private final FoodOptions< Template > options;
   
   public UiTemplateSelectionDialog( FoodOptions< Template > options ) {
      super( options.first(), options.options() );
      this.options = options;
      setTitle( "Template Selection" );
      setContentText( "Choose your Template:" );
   }//End Constructor
   
   public Optional< Template > friendly_showAndWait(){
      getItems().clear();
      getItems().addAll( options.options() );
      return showAndWait();
   }//End Method
   
   public void friendly_setHeaderText( String headerText ) {
      setHeaderText( headerText );
   }//End Method
   
}//End Class
