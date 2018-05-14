package uk.dangrew.nuts.graphics.progress.custom;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public class ProgressEntryTextPane extends TitledPane {

   private final GridPane wrapper;
   private final TextField headerField;
   private final TextArea notesArea;
   
   public ProgressEntryTextPane( ProgressSeriesDataController controller ) {
      this.setText( "Entry Text" );
      this.setExpanded( true );
      this.setCollapsible( false );
      this.setContent( wrapper = new GridPane() );
      controller.associate( this );
      
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForRowPercentages( wrapper, 10, 90 );
      styling.configureConstraintsForEvenColumns( wrapper, 1 );
      
      wrapper.add( headerField = new TextField(), 0, 0 );
      wrapper.add( notesArea = new TextArea(), 0, 1 );
      
      headerField.textProperty().addListener( ( s, o, n ) -> controller.setHeaderForSelected( n ) );
      notesArea.textProperty().addListener( ( s, o, n ) -> controller.setNotesForSelected( n ) );
      
      notesArea.setWrapText( true );
      selectionRemoved();
   }//End Constructor

   public void selectionRemoved(){
      headerField.setDisable( true );
      notesArea.setDisable( true );
      
      headerField.setText( null );
      notesArea.setText( null );
   }//End Method
   
   public void update( String header, String notes ) {
      headerField.setDisable( false );
      notesArea.setDisable( false );
      
      headerField.setText( header );
      notesArea.setText( notes );
   }//End Method

   TextField headerField() {
      return headerField;
   }//End Method
   
   TextArea notesArea() {
      return notesArea;
   }//End Method
   
}//End Class
