package uk.dangrew.nuts.graphics.graph.custom;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import javafx.collections.FXCollections;
import javafx.scene.chart.NumberAxis;
import uk.dangrew.kode.TestCommon;
import uk.dangrew.kode.launch.TestApplication;

public class GraphSettingsTest {
   
   @Captor private ArgumentCaptor< LocalDateTime > timestampCaptor;
   
   private GraphController controller;
   private GraphSettings systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      controller = spy( new GraphController( FXCollections.observableArrayList(), new NumberAxis(), new NumberAxis() ) );
      systemUnderTest = new GraphSettings( controller );
   }//End Method

   @Test public void shouldInitialiseGraphBounds() {
      assertThat( controller.yAxisLowerBoundProperty().get(), is( GraphSettings.DEFAULT_LOWER_BOUND ) );
      assertThat( controller.yAxisUpperBoundProperty().get(), is( GraphSettings.DEFAULT_UPPER_BOUND ) );
   }//End Method
   
   @Test public void shouldFocusOnTimestamp(){
      LocalDateTime now = LocalDateTime.now();
      verify( controller ).focusHorizontalAxisOn( timestampCaptor.capture(), eq( GraphSettings.DEFAULT_OUTLOOK ) );
      TestCommon.assertThatInputIsInRangeOf( timestampCaptor.getValue(), now, true );
   }//End Method

}//End Class
