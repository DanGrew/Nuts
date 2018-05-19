package uk.dangrew.nuts.graphics.common;

import java.time.LocalDateTime;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uk.dangrew.kode.datetime.TimestampComponents;
import uk.dangrew.kode.datetime.TimestampFormat;

public class CaretPositionTextSpinner implements EventHandler< KeyEvent >{

   private final TimestampFormat format;
   
   private DateTimeTextBox subject;
   
   public CaretPositionTextSpinner() {
      this.format = new TimestampFormat();
   }//End Constructor
   
   public void register( DateTimeTextBox subject ) {
      this.subject = subject;
      this.subject.addEventFilter( KeyEvent.KEY_PRESSED, this );
   }//End Constructor
   
   public void deregister(){
      this.subject.removeEventFilter( KeyEvent.KEY_PRESSED, this );
   }//End Method
   
   @Override public void handle( KeyEvent event ) {
      if ( handleCaretMovement( event.getCode() ) ) {
         event.consume();
         return;
      }
      if ( subject.isValid() ) {
         spin( event.getCode(), subject.getCaretPosition() );
         event.consume();
      }
   }//End Method
   
   private boolean handleCaretMovement( KeyCode code ) {
      switch ( code ) {
         case RIGHT:
            subject.positionCaret( subject.getCaretPosition() + 1 );
            return true;
         case LEFT:
            subject.positionCaret( subject.getCaretPosition() - 1 );
            return true;
         default:
            break;
      }
      return false;
   }//End Method
   
   private void spin( KeyCode code, int caretPosition ) {
      TimestampComponents component = TimestampComponents.findComponent( caretPosition );
      
      LocalDateTime timestamp = subject.getTextValue();
      switch ( code ) {
         case UP:
            timestamp = component.increment( timestamp );
            break;
         case DOWN:
            timestamp = component.decrement( timestamp );
            break;
      }
      subject.setText( format.toTimestampString( timestamp ) );
      subject.positionCaret( caretPosition );
   }//End Method
   
}//End Class
