package uk.dangrew.nuts.graphics.day;

import java.time.LocalDate;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceDialog;

public class UiDateSelectionDialog extends ChoiceDialog< LocalDate > {

   private final ObservableList< LocalDate > options;
   
   public UiDateSelectionDialog( ObservableList< LocalDate > options ) {
      super( LocalDate.now(), options );
      this.options = options;
      setTitle( "Date Selection" );
      setContentText( "Choose your Date:" );
   }//End Constructor
   
   public Optional< LocalDate > friendly_showAndWait(){
      getItems().clear();
      getItems().addAll( options );
      setSelectedItem( LocalDate.now().plusDays( 1 ) );
      return showAndWait();
   }//End Method
   
   public void friendly_setHeaderText( String headerText ) {
      setHeaderText( headerText );
   }//End Method
   
}//End Class
