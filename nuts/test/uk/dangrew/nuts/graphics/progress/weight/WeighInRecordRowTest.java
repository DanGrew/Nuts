package uk.dangrew.nuts.graphics.progress.weight;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.dangrew.nuts.graphics.progress.weight.WeightRecordingRow;
import uk.dangrew.nuts.progress.weight.WeightRecording;

public class WeighInRecordRowTest {

   @Mock private WeightRecording recording;
   private WeightRecordingRow systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      MockitoAnnotations.initMocks( this );
      systemUnderTest = new WeightRecordingRow( recording );
   }//End Method

   @Test public void shouldProvideRecording() {
      assertThat( systemUnderTest.recording(), is( recording ) );
   }//End Method

}//End Class
