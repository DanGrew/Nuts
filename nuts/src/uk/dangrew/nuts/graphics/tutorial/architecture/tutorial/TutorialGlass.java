package uk.dangrew.nuts.graphics.tutorial.architecture.tutorial;

import java.util.function.Function;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorActionBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorHighlight;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorMessageBuilder;
import uk.dangrew.nuts.graphics.tutorial.architecture.tutor.TutorPopOver;

public class TutorialGlass extends StackPane {

   private final Pane glass;
   
   private final Function< Runnable, Thread > threadSupplier; 
   private final TutorPopOver popOver;
   private final TutorHighlight highlight;
   
   public TutorialGlass() {
      this( new TutorPopOver(), new TutorHighlight() );
   }//End Constructor
   
   private TutorialGlass(
            TutorPopOver popover, 
            TutorHighlight highlight 
   ) {
      this( new Pane(), Thread::new, popover, highlight );
   }//End Constructor
   
   TutorialGlass( 
            Pane glass,
            Function< Runnable, Thread > threadSupplier,
            TutorPopOver popover, 
            TutorHighlight highlight
   ) {
      this.glass = glass;
      this.threadSupplier = threadSupplier;
      this.popOver = popover;
      this.highlight = highlight;
   }//End Constructor
   
   public void replaceUnderlyingContent( Node underlyingContent ) {
      JavaFxThreading.runAndWait( () -> {
         this.getChildren().clear();
         this.getChildren().addAll( underlyingContent, glass );
         this.glass.getChildren().clear();
         this.glass.getChildren().add( highlight );
      } );
   }//End Method
   
   public void tutorUser( TutorMessageBuilder messageBuilder ) {
      if ( messageBuilder.getComponent() != null ) {
         JavaFxThreading.runLater( () -> popOver.show( messageBuilder ) );
      }
      
      if ( messageBuilder.getHighlightSubject() != null ) {
         JavaFxThreading.runLater( () -> highlight.focus( messageBuilder.getHighlightSubject(), messageBuilder.getHighlightColour() ) );
      }
   }//End Method
   
   public void tutorAction( TutorActionBuilder builder ) {
      threadSupplier.apply( () -> {
         builder.actions().forEach( Runnable::run );
         builder.callback().ifPresent( Runnable::run );
      } ).start();
   }//End Method
   
   public void removeTutorMessage() {
      popOver.friendly_hide();
   }//End Method
   
   public void removeTutorHighlight() {
      highlight.hide();
   }//End Method
   
   public void clearMessageAndHighlight(){
      removeTutorMessage();
      removeTutorHighlight();
   }//End Method
   
}//End Method
