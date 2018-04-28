package uk.dangrew.nuts.progress.custom;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.launch.TestApplication;
import uk.dangrew.nuts.ModelVerifier;

public class ProgressEntryTest {

   private ProgressEntry systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new ProgressEntry();
   }//End Method

   @Test public void shouldProvideProperties() {
      new ModelVerifier<>( systemUnderTest )
         .shouldProvideProperty( ProgressEntry::timestampProperty, LocalDateTime.now() )
         .shouldProvideDoubleProperty( ProgressEntry::valueProperty, 0.0 );
   }//End Method

}//End Class
