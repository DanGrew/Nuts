package uk.dangrew.nuts.graphics.tutorial.architecture;

import java.util.function.Function;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class TutorialGlass extends StackPane {

   private final Pane glass;
   
   private final Function< Runnable, Thread > threadSupplier; 
   private final TutorPopOver popOver;
   private final TutorHighlight highlight;
   
   public TutorialGlass( Node underlyingContent ) {
      this( new TutorPopOver(), new TutorHighlight(), underlyingContent );
   }//End Constructor
   
   private TutorialGlass(
            TutorPopOver popover, 
            TutorHighlight highlight, 
            Node underlyingContent
   ) {
      this( Thread::new, popover, highlight, underlyingContent );
   }//End Constructor
   
   TutorialGlass( 
            Function< Runnable, Thread > threadSupplier,
            TutorPopOver popover, 
            TutorHighlight highlight, 
            Node underlyingContent 
   ) {
      this.glass = new Pane();
      this.getChildren().addAll( underlyingContent, glass );
      
      this.threadSupplier = threadSupplier;
      this.popOver = popover;
      this.highlight = highlight;
      this.glass.getChildren().add( highlight );
   }//End Constructor
   
   public void tutorUser( TutorMessageBuilder messageBuilder ) {
      PlatformImpl.runLater( () -> popOver.show( messageBuilder ) );
      
      if ( messageBuilder.getHighlightSubject() == null ) {
         return;
      }
      PlatformImpl.runLater( () -> highlight.focus( messageBuilder.getHighlightSubject() ) );
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
   
}//End Method
