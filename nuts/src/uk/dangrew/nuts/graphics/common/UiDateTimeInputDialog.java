package uk.dangrew.nuts.graphics.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.LabelBuilder;

public class UiDateTimeInputDialog extends Dialog< LocalDateTime >{

   static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
   static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern( "HH:mm:ss" );
   
   private final ButtonType confirm;
   private final ButtonType cancel;
   
   private final JavaFxStyle styling;
   private final TextField dateField;
   private final TextField timeField;
   
   public UiDateTimeInputDialog() {
      this.styling = new JavaFxStyle();
      this.setTitle( "Timestamp Input" );
      
      this.dateField = new TextField();
      this.dateField.setPromptText( "dd-mm-yyyy" );
      this.dateField.textProperty().addListener( ( s, o, n ) -> validateDate() );
      this.timeField = new TextField();
      this.timeField.setPromptText( "hh:mm:ss" );
      this.timeField.textProperty().addListener( ( s, o, n ) -> validateTime() );
      
      GridPane wrapper = new GridPane();
      this.styling.configureConstraintsForEvenRows( wrapper, 3 );
      this.styling.configureConstraintsForEvenColumns( wrapper, 1 );
      wrapper.add( 
            new LabelBuilder()
               .withText( "Please enter the date and time:" )
               .build(), 
      0, 0 );
      wrapper.add( dateField, 0, 1 );
      wrapper.add( timeField, 0, 2 );
      wrapper.setPadding( new Insets( 10 ) );
      this.getDialogPane().setContent( wrapper );
      
      this.getDialogPane().getButtonTypes().addAll( 
               confirm = ButtonType.OK, 
               cancel = ButtonType.CANCEL 
      );
      this.setResultConverter( this::resultFormatter );
      
      this.validateDate();
      this.validateTime();
      
      this.resetInputToNow();
   }//End Constructor
   
   public void resetInputToNow(){
      LocalDateTime now = LocalDateTime.now();
      dateField.setText( DATE_FORMATTER.format( now ) );
      timeField.setText( TIME_FORMATTER.format( now ) );
   }//End Method
   
   private LocalDateTime resultFormatter( ButtonType button ) {
      if ( button == confirm ) {
         LocalDate date = validateDate();
         LocalTime time = validateTime();
         return LocalDateTime.of( date, time );
      }
      return null;
   }//End Method
   
   private LocalDate validateDate(){
      try { 
         LocalDate parsed = LocalDate.parse( dateField.getText(), DATE_FORMATTER );
         showValidation( dateField, true );
         return parsed;
      } catch ( DateTimeParseException exception ) {
         showValidation( dateField, false );
         return null;
      }
   }//End Method
   
   private LocalTime validateTime(){
      try { 
         LocalTime parsed = LocalTime.parse( timeField.getText(), TIME_FORMATTER );
         showValidation( timeField, true );
         return parsed;
      } catch ( DateTimeParseException exception ) {
         showValidation( timeField, false );
         return null;
      }
   }//End Method
   
   private void showValidation( TextField field, boolean valid ) {
      if ( valid ) {
         field.setBorder( styling.borderFor( Color.GREEN ) );
         getDialogPane().lookupButton( confirm ).setDisable( false );
      } else {
         field.setBorder( styling.borderFor( Color.RED ) );
         getDialogPane().lookupButton( confirm ).setDisable( true );
      }
   }//End Method
   
   public Optional< LocalDateTime > friendly_showAndWait(){
      return showAndWait();
   }//End Method
   
   TextField dateField() {
      return dateField;
   }//End Method
   
   TextField timeField() {
      return timeField;
   }//End Method
   
}//End Class
