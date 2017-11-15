package uk.dangrew.nuts.graphics.day;

import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import uk.dangrew.nuts.graphics.table.FoodOptions;
import uk.dangrew.nuts.template.Template;

public class UiTemplateSelectionPopup extends ChoiceDialog< Template > {

   public UiTemplateSelectionPopup( FoodOptions< Template > options ) {
      super( options.first(), options.options() );
      setTitle( "Template Selection" );
      setHeaderText( "Choose from Templates available in the system." );
      setContentText( "Choose your Template:" );
   }//End Constructor
   
   public Optional< Template > friendly_showAndWait(){
      return showAndWait();
   }//End Method
   
}//End Class
