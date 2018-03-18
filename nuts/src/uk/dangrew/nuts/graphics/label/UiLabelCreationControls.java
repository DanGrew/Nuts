package uk.dangrew.nuts.graphics.label;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.style.JavaFxStyle;

public class UiLabelCreationControls extends GridPane {
   
   private final TextField nameField;
   private final Button changeNameButton;
   private final Button createLabelButton;
   private final Button deleteLabelButton;
   
   public UiLabelCreationControls( UiLabelController controller ) {
      JavaFxStyle styling = new JavaFxStyle();
      styling.configureConstraintsForEvenColumns( this, 5 );
      styling.configureConstraintsForEvenRows( this, 1 );
      
      this.nameField = new TextField();
      this.add( nameField, 0, 0 );
      controller.selectedLabel().addListener( ( s, o, n ) -> {
         if ( n == null ) {
            nameField.setText( "" );
         } else {
            nameField.setText( n.properties().nameProperty().get() );
         }
      } );
      
      this.changeNameButton = new Button( "Change Name" );
      this.changeNameButton.setMaxWidth( Double.MAX_VALUE );
      this.changeNameButton.setOnAction( e -> controller.changeName( nameField.getText() ) );
      this.add( changeNameButton, 1, 0 );
      
      this.createLabelButton = new Button( "Create Label" );
      this.createLabelButton.setMaxWidth( Double.MAX_VALUE );
      this.createLabelButton.setOnAction( e -> controller.createLabel( nameField.getText() ) );
      this.add( createLabelButton, 2, 0 );
      
      this.deleteLabelButton = new Button( "Delete Label" );
      this.deleteLabelButton.setMaxWidth( Double.MAX_VALUE );
      this.deleteLabelButton.setOnAction( e -> controller.deleteLabel() );
      this.add( deleteLabelButton, 3, 0 );
   }//End Constructor
   
   TextField nameField(){
      return nameField;
   }//End Method
   
   Button changeNameButton(){
      return changeNameButton;
   }//End Method
   
   Button createLabelButton(){
      return createLabelButton;
   }//End Method
   
   Button deleteLabelButton(){
      return deleteLabelButton;
   }//End Method
   
}//End Class
