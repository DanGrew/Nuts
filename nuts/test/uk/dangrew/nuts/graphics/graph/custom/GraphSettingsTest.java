package uk.dangrew.nuts.graphics.graph.custom;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.TestCommon;
import uk.dangrew.kode.launch.TestApplication;

public class GraphSettingsTest {
   
   @Captor private ArgumentCaptor< LocalDateTime > timestampCaptor;
   
   @Mock private GraphController controller;
   private GraphSettings systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new GraphSettings( controller );
   }//End Method

   @Test public void shouldInitialiseGraphBounds() {
      verify( controller ).setRecordingLowerBound( GraphSettings.DEFAULT_LOWER_BOUND );
      verify( controller ).setRecordingUpperBound( GraphSettings.DEFAULT_UPPER_BOUND );
   }//End Method
   
   @Test public void shouldFocusOnTimestamp(){
      LocalDateTime now = LocalDateTime.now();
      verify( controller ).focusHorizontalAxisOn( timestampCaptor.capture(), eq( GraphSettings.DEFAULT_OUTLOOK ) );
      TestCommon.assertThatInputIsInRangeOf( timestampCaptor.getValue(), now, true );
   }//End Method

}//End Class
