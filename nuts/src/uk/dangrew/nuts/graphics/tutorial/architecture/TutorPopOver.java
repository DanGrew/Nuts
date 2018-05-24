package uk.dangrew.nuts.graphics.tutorial.architecture;

import org.controlsfx.control.PopOver;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class TutorPopOver extends PopOver {

   private final FriendlyMessageGenerator buttonTextGenerator;
   private final BorderPane content;
   private final Button confirmationButton;
   
   public TutorPopOver() {
      this.buttonTextGenerator = new FriendlyMessageGenerator();
      
      this.setContentNode( content = new BorderPane() );
      this.setAutoHide( false );
      
      this.confirmationButton = new Button();
      this.confirmationButton.setOnAction( e -> friendly_hide() );
      BorderPane.setAlignment( confirmationButton, Pos.CENTER_RIGHT );
      this.content.setBottom( confirmationButton );
      this.content.setPadding( new Insets( 5 ) );
   }//End Constructor
   
   public void show( TutorMessageBuilder builder ) {
      this.setArrowLocation( builder.getArrowDirection() );
      this.content.setCenter( builder.getMessage() );
      this.confirmationButton.setText( buttonTextGenerator.friendlyConfirmation() );
      this.friendly_show( builder.getComponent() );
   }//End Method
   
   public void friendly_hide(){
      hide();
   }//End Method
   
   public void friendly_show( Node owner ){
      show( owner );
   }//End Method
   
   BorderPane content(){
      return content;
   }//End Method
   
   Button confirmationButton(){
      return confirmationButton;
   }//End Method
   
}//End Class
