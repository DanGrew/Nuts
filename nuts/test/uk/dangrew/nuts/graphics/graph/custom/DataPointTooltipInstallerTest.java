package uk.dangrew.nuts.graphics.graph.custom;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.scene.Node;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import uk.dangrew.kode.friendly.javafx.FriendlyTooltip;
import uk.dangrew.kode.launch.TestApplication;

public class DataPointTooltipInstallerTest {

   private Node dataNode;
   private Data< Number, Number > data;
   private Tooltip tooltip;
   
   @Mock private FriendlyTooltip tooltipKit;
   private DataPointTooltipInstaller systemUnderTest;

   @Before public void initialiseSystemUnderTest() {
      TestApplication.startPlatform();
      MockitoAnnotations.initMocks( this );
      dataNode = new BorderPane();
      data = new Data<>();
      tooltip = new Tooltip();
      systemUnderTest = new DataPointTooltipInstaller( tooltipKit, data, tooltip );
   }//End Method

   @Test public void shouldInstallTooltip() {
      data.setNode( dataNode );
      verify( tooltipKit ).friendly_install( dataNode, tooltip );
      
      data.setNode( new BorderPane() );
      verifyNoMoreInteractions( tooltipKit );
   }//End Method
   
}//End Class
