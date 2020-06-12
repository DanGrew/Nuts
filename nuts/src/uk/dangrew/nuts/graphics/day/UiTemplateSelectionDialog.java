package uk.dangrew.nuts.graphics.day;

import java.util.Optional;

import javafx.scene.control.ChoiceDialog;
import uk.dangrew.kode.javafx.table.options.ConceptOptions;
import uk.dangrew.nuts.template.Template;

public class UiTemplateSelectionDialog extends ChoiceDialog< Template > {

   private final ConceptOptions< Template > options;
   
   public UiTemplateSelectionDialog( ConceptOptions< Template > options ) {
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
