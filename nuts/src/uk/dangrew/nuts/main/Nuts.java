/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.dangrew.jupa.javafx.platform.PlatformLifecycle;
import uk.dangrew.nuts.graphics.goal.CoreInterface;

/**
 * Entry point to the system for launching.
 */
public class Nuts extends Application {
   
   static final String TITLE = "Nutrient Usage Tracking System";
   
   /**
    * Constructs a new {@link Nuts}.
    */
   public Nuts() {}//End Constructor
   
   /**
    * {@inheritDoc}
    */
   @Override public void start(Stage stage) throws Exception {
      stage.setTitle( TITLE );
      stage.setOnCloseRequest( event -> PlatformLifecycle.shutdown() );
      stage.setScene( new Scene( new CoreInterface() ) );
      stage.setMaximized( true );
      stage.show();
   }//End Method
   
   public static void main( String[] args ) {
      launch();
   }//End Method
   
}//End Class
