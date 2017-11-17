package uk.dangrew.nuts.graphics.day;

import java.util.Optional;

import javafx.scene.control.TextInputDialog;

public class UiTemplateNameInputDialog extends TextInputDialog {

   public UiTemplateNameInputDialog() {
      setTitle( "Template Creation" );
      setContentText( "Name your template:" );
   }//End Constructor
   
   public Optional< String > friendly_showAndWait(){
      return showAndWait();
   }//End Method
   
   public void friendly_setHeaderText( String headerText ) {
      setHeaderText( headerText );
   }//End Method
   
}//End Class
