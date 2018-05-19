package uk.dangrew.nuts.graphics.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.LabelBuilder;

public class UiDateTimeInputDialog extends Dialog< LocalDateTime >{

   static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
   static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern( "HH:mm:ss" );
   
   private final ButtonType confirm;
   private final ButtonType cancel;
   
   private final JavaFxStyle styling;
   private final DateTimeTextBox timestampTextBox;
   
   public UiDateTimeInputDialog() {
      this.styling = new JavaFxStyle();
      this.setTitle( "Timestamp Input" );
      
      this.timestampTextBox = new DateTimeTextBox();
      
      GridPane wrapper = new GridPane();
      this.styling.configureConstraintsForEvenRows( wrapper, 2 );
      this.styling.configureConstraintsForEvenColumns( wrapper, 1 );
      wrapper.add( 
            new LabelBuilder()
               .withText( "Please enter the date and time:" )
               .build(), 
      0, 0 );
      wrapper.add( timestampTextBox, 0, 1 );
      wrapper.setPadding( new Insets( 10 ) );
      this.getDialogPane().setContent( wrapper );
      
      this.getDialogPane().getButtonTypes().addAll( 
               confirm = ButtonType.OK, 
               cancel = ButtonType.CANCEL 
      );
      this.setResultConverter( this::resultFormatter );
      this.resetInputToNow();
   }//End Constructor
   
   public void resetInputToNow(){
      timestampTextBox.resetInputToNow();
   }//End Method
   
   private LocalDateTime resultFormatter( ButtonType button ) {
      if ( button == confirm ) {
         return timestampTextBox.getTextValue();
      }
      return null;
   }//End Method
   
   public Optional< LocalDateTime > friendly_showAndWait(){
      return showAndWait();
   }//End Method
   
   DateTimeTextBox timestampBox() {
      return timestampTextBox;
   }//End Method
   
}//End Class
