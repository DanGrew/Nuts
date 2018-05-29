package uk.dangrew.nuts.graphics.tutorial.architecture;

import java.util.Optional;

import org.controlsfx.control.PopOver.ArrowLocation;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class TutorMessageBuilder {
   
   private Node message;
   private Node component;
   private ArrowLocation arrowLocation;
   private Node borderHighlightSubject;
   private Color highlightColour;
   private Runnable callback;
   private boolean withConfirmation;
   
   public TutorMessageBuilder() {
      this.arrowLocation = ArrowLocation.LEFT_CENTER;
      this.highlightColour = Color.RED;
   }//End Constructor
   
   public TutorMessageBuilder withMessage( Node message ) {
      this.message = message;
      return this;
   }//End Method
   
   public Node getMessage(){
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
   
   public TutorMessageBuilder withConfirmation() {
      this.withConfirmation = true;
      return this;
   }//End Method
   
   public boolean shouldHaveConfirmation(){
      return withConfirmation;
   }//End Method
   
   public TutorMessageBuilder highlighting( Node node ) {
      this.borderHighlightSubject = node;
      return this;
   }//End Method
   
   public Node getHighlightSubject(){
      return borderHighlightSubject;
   }//End Method
   
   public TutorMessageBuilder withHighlight( Color colour ) {
      this.highlightColour = colour;
      return this;
   }//End Method
   
   public Color getHighlightColour(){
      return highlightColour;
   }//End Method
   
   public TutorMessageBuilder callingBackTo( Runnable runnable ) {
      this.callback = runnable;
      return this;
   }//End Method
   
   public Optional< Runnable > callback(){
      return Optional.ofNullable( callback );
   }//End Method
   
   public TutorMessageBuilder focussingOn( Node focus ) {
      return withRespectTo( focus ).highlighting( focus );
   }//End Method

}//End Class
