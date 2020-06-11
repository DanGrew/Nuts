package uk.dangrew.nuts.graphics.system;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uk.dangrew.kode.javafx.platform.JavaFxThreading;

public class ImageLoaderService {

   private final Map< String, Image > cachedImages;
   private final Supplier< ExecutorService > executorSupplier;
   
   private ExecutorService service;
   
   public ImageLoaderService() {
      this( Executors::newSingleThreadExecutor );
   }//End Constructor
   
   ImageLoaderService( Supplier< ExecutorService > executorSupplier ) {
      this.executorSupplier = executorSupplier;
      this.service = executorSupplier.get();
      this.cachedImages = new HashMap<>();
   }//End Constructor
   
   public void loadImage( ImageView view, String imageUrl ) {
      service.submit( () -> {
         boolean newLoad = !cachedImages.containsKey( imageUrl );
         JavaFxThreading.runAndWait( () -> applyImage( view, imageUrl ) );
         if ( newLoad ) {
            Thread.sleep( 1000 );
         }
         return null;
      } );
   }//End Method
   
   public void cancelInProgress(){
      service.shutdownNow();
      service = executorSupplier.get();
   }//End Method
   
   private void applyImage( ImageView view, String imageUrl ) {
      Image cachedImage = imageFor( imageUrl );
      if ( cachedImage == null ) {
         cachedImages.put( imageUrl, cachedImage = new Image( imageUrl ) );
      }
      view.setImage( cachedImage );
   }//End Method

   Image imageFor( String imageUrl ) {
      return cachedImages.get( imageUrl );
   }//End Method
   
}//End Class
