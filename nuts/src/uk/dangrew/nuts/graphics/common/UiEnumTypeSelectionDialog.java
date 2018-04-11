package uk.dangrew.nuts.graphics.common;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceDialog;

public class UiEnumTypeSelectionDialog< EnumTypeT extends Enum< ? > > extends ChoiceDialog< EnumTypeT > {

   public UiEnumTypeSelectionDialog( Class< EnumTypeT > enumClass, EnumTypeT defaultSelection ) {
      super( defaultSelection, FXCollections.observableArrayList( enumClass.getEnumConstants() ) );
      setTitle( "Selection" );
      setContentText( "Choose your Type:" );
   }//End Constructor
   
   public Optional< EnumTypeT > friendly_showAndWait(){
      return showAndWait();
   }//End Method
   
   public void friendly_setHeaderText( String headerText ) {
      setHeaderText( headerText );
   }//End Method
   
}//End Class
