package uk.dangrew.nuts.graphics.system;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.javafx.application.PlatformImpl;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageLoaderService {

   private ExecutorService service;
   
   public ImageLoaderService() {
      this.service = Executors.newSingleThreadExecutor();
   }//End Constructor
   
   public void loadImage( ImageView view, String imageUrl ) {
      service.submit( () -> {
         PlatformImpl.runAndWait( () -> view.setImage( new Image( imageUrl ) ) );
         Thread.sleep( 1000 );
         return null;
      } );
   }//End Method
   
   public void cancelInProgress(){
      service.shutdownNow();
      service = Executors.newSingleThreadExecutor();
   }//End Method
   
}//End Class
