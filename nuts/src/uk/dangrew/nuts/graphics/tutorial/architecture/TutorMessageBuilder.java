package uk.dangrew.nuts.graphics.tutorial.architecture;

import org.controlsfx.control.PopOver.ArrowLocation;

import javafx.scene.Node;
import javafx.scene.text.TextFlow;

public class TutorMessageBuilder {
   
   private TextFlow message;
   private Node component;
   private ArrowLocation arrowLocation;
   
   private Node borderHighlightSubject;
   
   public TutorMessageBuilder() {
      this.arrowLocation = ArrowLocation.LEFT_CENTER;
   }//End Constructor
   
   public TutorMessageBuilder withMessage( TextFlow message ) {
      this.message = message;
      return this;
   }//End Method
   
   public TextFlow getMessage(){
      return message;
   }//End Method
   
   public TutorMessageBuilder withRespectTo( Node component ) {
      this.component = component;
      return this;
   }//End Method
   
   public Node getComponent(){
      return component;
   }//End Method
   
   public TutorMessageBuilder pointing( ArrowLocation location ) {
      this.arrowLocation = location;
      return this;
   }//End Method
   
   public ArrowLocation getArrowDirection(){
      return arrowLocation;
   }//End Method
   
   public TutorMessageBuilder highlighting( Node node ) {
      this.borderHighlightSubject = node;
      return this;
   }//End Method
   
   public Node getHighlightSubject(){
      return borderHighlightSubject;
   }//End Method

}//End Class
