package uk.dangrew.nuts.graphics.common;

import java.time.LocalDateTime;
import java.util.Optional;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import uk.dangrew.kode.datetime.TimestampFormat;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public class DateTimeTextBox extends TextField {

   static final Color INVALID_BORDER_COLOUR = Color.RED;
   static final Color VALID_BORDER_COLOUR = Color.GREEN;
   
   private final JavaFxStyle styling;
   private final TimestampFormat format;
   
   public DateTimeTextBox() {
      this( new CaretPositionTextSpinner() );
   }//End Constructor
   
   DateTimeTextBox( CaretPositionTextSpinner spinner ) {
      this.styling = new JavaFxStyle();
      this.format = new TimestampFormat();
      this.setPromptText( format.pattern() );
      this.textProperty().addListener( ( s, o, n ) -> validate() );
      spinner.register( this );
      
      this.validate();
      this.resetInputToNow();
   }//End Constructor
   
   public void resetInputToNow(){
      LocalDateTime now = LocalDateTime.now();
      setText( format.toTimestampString( now ) );
   }//End Method
   
   private void validate(){
      showValidation( isValid() );
   }//End Method
   
   boolean isValid(){
      return Optional.ofNullable( parseText() ).isPresent();
   }//End Method
   
   private LocalDateTime parseText() {
      return format.parseTimestamp( getText() );
   }//End Method
   
   private void showValidation( boolean valid ) {
      if ( valid ) {
         setBorder( styling.borderFor( VALID_BORDER_COLOUR ) );
      } else {
         setBorder( styling.borderFor( INVALID_BORDER_COLOUR ) );
      }
   }//End Method
   
   public LocalDateTime getTextValue(){
      return parseText();
   }//End Method
   
}//End Class
