package uk.dangrew.nuts.graphics.system;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uk.dangrew.kode.launch.TestApplication;

public class ImageLoaderServiceTest {
   
   private static final String EXAMPLE_IMAGE_URL = "https://img.tesco.com/Groceries/pi/807/5411188119807/IDShot_90x90.jpg";

   private ExecutorService executor;
   private ImageLoaderService systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      executor = Executors.newSingleThreadExecutor();
      systemUnderTest = new ImageLoaderService( () -> executor );
   }//End Method

   @Ignore //causing problems with slow internet and or tesco website - it does actually work
   @Test public void shouldCacheImage() {
      ImageView view = new ImageView();
      systemUnderTest.loadImage( view, EXAMPLE_IMAGE_URL );
      waitForExecutor();
      Image cachedImage = systemUnderTest.imageFor( EXAMPLE_IMAGE_URL );
      assertThat( cachedImage, is( notNullValue() ) );
      assertThat( view.getImage(), is( cachedImage ) );
      
      ImageView view2 = new ImageView();
      systemUnderTest.loadImage( view2, EXAMPLE_IMAGE_URL );
      waitForExecutor();
      assertThat( view2.getImage(), is( cachedImage ) );
   }//End Method
   
   @Test public void shouldCancelAndShutdownExecutor(){
      ExecutorService executor1 = mock( ExecutorService.class );
      ExecutorService executor2 = mock( ExecutorService.class );
      Supplier< ExecutorService > executorSupplier = mock( Supplier.class );
      when( executorSupplier.get() ).thenReturn( executor1 ).thenReturn( executor2 );
      
      systemUnderTest = new ImageLoaderService( executorSupplier );
      systemUnderTest.cancelInProgress();
      verify( executor1 ).shutdownNow();
      systemUnderTest.loadImage( new ImageView(), EXAMPLE_IMAGE_URL );
      verify( executor2 ).submit( Mockito.< Callable >any() );
   }//End Method
   
   private void waitForExecutor(){
      try {
         executor.awaitTermination( 5, TimeUnit.SECONDS );
      } catch ( InterruptedException e ) {
         fail();
      }
   }//End Method

}//End Class
