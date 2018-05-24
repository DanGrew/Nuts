package uk.dangrew.nuts.graphics.tutorial.architecture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sun.javafx.application.PlatformImpl;

public class TutorActionBuilder {

   private final List< Runnable > actions;
   
   public TutorActionBuilder() {
      this.actions = new ArrayList<>();
   }//End Constructor
   
   public TutorActionBuilder nonGraphicalAction( Runnable action ) {
      this.actions.add( action );
      return this;
   }//End Method
   
   public TutorActionBuilder graphicalNonBlockingAction( Runnable action ) {
      this.actions.add( () -> PlatformImpl.runLater( action::run ) );
      return this;
   }//End Method
   
   public TutorActionBuilder graphicalBlockingAction( Runnable action ) {
      this.actions.add( () -> PlatformImpl.runAndWait( action::run ) );
      return this;
   }//End Method
   
   public TutorActionBuilder pauseFor( int seconds ) {
      this.actions.add( sleep( seconds ) );
      return this;
   }//End Method
   
   private Runnable sleep( int seconds ) {
      return () -> {
         try {
            TimeUnit.SECONDS.sleep( seconds );
         } catch ( InterruptedException e ) {
            e.printStackTrace();
         }
      };
   }//End Method
   
   public List< Runnable > actions(){
      return actions;
   }//End Method
   
}//End Class
