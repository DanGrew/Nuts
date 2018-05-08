package uk.dangrew.nuts.graphics.graph.custom;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.kode.datetime.DateTimeFormats;
import uk.dangrew.kode.launch.TestApplication;

public class GraphSettingsTest {

   private DateTimeFormats formats;
   
   @Mock private GraphController controller;
   private GraphSettings systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      formats = new DateTimeFormats();
      systemUnderTest = new GraphSettings( controller );
   }//End Method

   @Test public void shouldInitialiseGraphBounds() {
      verify( controller ).setDateLowerBound( formats.toDayBeginningEpochSeconds( GraphSettings.DEFAULT_DATE_LOWER_BOUND ) );
      verify( controller ).setDateUpperBound( formats.toDayBeginningEpochSeconds( GraphSettings.DEFAULT_DATE_UPPER_BOUND ) );
      verify( controller ).setRecordingLowerBound( GraphSettings.DEFAULT_LOWER_BOUND );
      verify( controller ).setRecordingUpperBound( GraphSettings.DEFAULT_UPPER_BOUND );
   }//End Method

}//End Class
