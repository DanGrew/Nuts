package uk.dangrew.nuts.progress.custom;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.function.BiConsumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;

public class ProgressChangedListenerTest {

   private static final LocalDateTime EXAMPLE_TIMESTAMP = LocalDateTime.now();
   private static final Double EXAMPLE_VALUE = 102.3;
   
   @Mock private BiConsumer< LocalDateTime, Double > whenAdded;
   @Mock private BiConsumer< LocalDateTime, Double > whenUpdated;
   @Mock private BiConsumer< LocalDateTime, Double > whenRemoved;
   
   @Mock private BiConsumer< LocalDateTime, Double > whenAdded2;
   @Mock private BiConsumer< LocalDateTime, Double > whenUpdated2;
   @Mock private BiConsumer< LocalDateTime, Double > whenRemoved2;
   
   private ProgressChangedListener< LocalDateTime > systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProgressChangedListener<>();
   }//End Method

   @Test public void shouldForwardAddedCallsOnToDelegated() {
      systemUnderTest.progressAdded( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenAdded, never() ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      
      systemUnderTest.whenProgressAdded( whenAdded );
      systemUnderTest.progressAdded( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenAdded ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      
      systemUnderTest.whenProgressAdded( whenAdded2 );
      systemUnderTest.progressAdded( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenAdded, times( 2 ) ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenAdded2 ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      
      systemUnderTest.removeWhenProgressAdded( whenAdded );
      systemUnderTest.progressAdded( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenAdded, times( 2 ) ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenAdded2, times( 2 ) ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
   }//End Method
   
   @Test public void shouldForwardUpdatedCallsOnToDelegated() {
      systemUnderTest.progressUpdated( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenUpdated, never() ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      
      systemUnderTest.whenProgressUpdated( whenUpdated );
      systemUnderTest.progressUpdated( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenUpdated ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      
      systemUnderTest.whenProgressUpdated( whenUpdated2 );
      systemUnderTest.progressUpdated( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenUpdated, times( 2 ) ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenUpdated2 ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      
      systemUnderTest.removeWhenProgressUpdated( whenUpdated );
      systemUnderTest.progressUpdated( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenUpdated, times( 2 ) ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenUpdated2, times( 2 ) ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
   }//End Method
   
   @Test public void shouldForwardRemovedCallsOnToDelegated() {
      systemUnderTest.progressRemoved( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenRemoved, never() ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      
      systemUnderTest.whenProgressRemoved( whenRemoved );
      systemUnderTest.progressRemoved( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenRemoved ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      
      systemUnderTest.whenProgressRemoved( whenRemoved2 );
      systemUnderTest.progressRemoved( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenRemoved, times( 2 ) ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenRemoved2 ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      
      systemUnderTest.removeWhenProgressRemoved( whenRemoved );
      systemUnderTest.progressRemoved( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenRemoved, times( 2 ) ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
      verify( whenRemoved2, times( 2 ) ).accept( EXAMPLE_TIMESTAMP, EXAMPLE_VALUE );
   }//End Method
   
}//End Class
